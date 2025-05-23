package com.alekseilomain.medoffice.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.alekseilomain.data.database.model.ContactEntity
import com.alekseilomain.domain.model.Contact
import com.alekseilomain.domain.repository.ContactsRepository
import com.alekseilomain.domain.usecase.ClearContactsUseCase
import com.alekseilomain.domain.usecase.FetchContactsPageUseCase
import com.alekseilomain.domain.usecase.GetCityUseCase
import com.alekseilomain.domain.usecase.GetLocationUseCase
import com.alekseilomain.domain.usecase.GetSeedUseCase
import com.alekseilomain.domain.usecase.IsLoggedInUseCase
import com.alekseilomain.domain.usecase.LoginUseCase
import com.alekseilomain.domain.usecase.LogoutUseCase
import com.alekseilomain.domain.usecase.ObserveContactsUseCase
import com.alekseilomain.domain.usecase.UpsertContactUseCase
import com.alekseilomain.medoffice.BuildConfig.DADATA_API_TOKEN
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mapper.ContactDtoToEntityMapper
import mapper.DomainToEntityMapper
import mapper.EntityToDomainMapper
import mapper.Mapper
import mapper.UserDtoToEntityMapper
import okhttp3.OkHttpClient
import remote.location.DaDataApi
import remote.location.FusedLocationClientImpl
import remote.location.GetCityUseCaseImpl
import remote.location.GetLocationUseCaseImpl
import remote.location.LocationClient
import remote.randomuser.ContactDto
import remote.randomuser.ContactsRepositoryImpl
import remote.randomuser.RandomUserApi
import remote.randomuser.model.UserDto
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import usecase.ClearContactsUseCaseImpl
import usecase.FetchContactsPageUseCaseImpl
import usecase.GetSeedUseCaseImpl
import usecase.IsLoggedInUseCaseImpl
import usecase.LoginUseCaseImpl
import usecase.LogoutUseCaseImpl
import usecase.ObserveContactsUseCaseImpl
import usecase.UpsertContactUseCaseImpl
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    // region Binds

    @Binds
    abstract fun bindGetSeed(
        impl: GetSeedUseCaseImpl
    ): GetSeedUseCase

    @Binds
    abstract fun bindLocationClient(
        impl: FusedLocationClientImpl
    ): LocationClient

    @Binds
    abstract fun bindContactsRepository(
        impl: ContactsRepositoryImpl
    ): ContactsRepository

    @Binds
    abstract fun bindUserDtoToEntityMapper(
        impl: UserDtoToEntityMapper
    ): Mapper<UserDto, ContactEntity>


    @Binds
    abstract fun bindContactDtoToEntityMapper(
        impl: ContactDtoToEntityMapper
    ): Mapper<ContactDto, ContactEntity>

    @Binds
    abstract fun bindEntityToDomainMapper(
        impl: EntityToDomainMapper
    ): Mapper<ContactEntity, Contact>

    @Binds
    abstract fun bindDomainToEntityMapper(
        impl: DomainToEntityMapper
    ): Mapper<Contact, ContactEntity>

    @Binds
    abstract fun bindLoginUseCase(
        impl: LoginUseCaseImpl
    ): LoginUseCase

    @Binds
    abstract fun bindIsLoggedInUseCase(
        impl: IsLoggedInUseCaseImpl
    ): IsLoggedInUseCase

    @Binds
    abstract fun bindLogoutUseCase(
        impl: LogoutUseCaseImpl
    ): LogoutUseCase

    @Binds @Singleton
    abstract fun bindFetchContactsPageUseCase(
        impl: FetchContactsPageUseCaseImpl
    ): FetchContactsPageUseCase

    @Binds
    abstract fun bindObserveContactsUseCase(
        impl: ObserveContactsUseCaseImpl
    ): ObserveContactsUseCase

    @Binds
    abstract fun bindClearContactsUseCase(
        impl: ClearContactsUseCaseImpl
    ): ClearContactsUseCase

    @Binds @Singleton
    abstract fun bindUpsertContactUseCase(
        impl: UpsertContactUseCaseImpl
    ): UpsertContactUseCase

    @Binds
    abstract fun bindGetLocationUseCase(
        impl: GetLocationUseCaseImpl
    ): GetLocationUseCase

    @Binds
    abstract fun bindGetCityUseCase(
        impl: GetCityUseCaseImpl
    ): GetCityUseCase

    // endregion

    companion object {

        // region Network

        @Provides @Singleton
        fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        @Provides @Singleton
        fun provideMoshi(): Moshi =
            Moshi.Builder().build()

        @Provides @Singleton
        @Named("randomUserRetrofit")
        fun provideRandomUserRetrofit(
            client: OkHttpClient,
            moshi: Moshi
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        @Provides @Singleton
        @Named("daDataRetrofit")
        fun provideDaDataRetrofit(
            client: OkHttpClient,
            moshi: Moshi
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl("https://suggestions.dadata.ru/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        @Provides @Singleton
        fun provideRandomUserApi(
            @Named("randomUserRetrofit") retrofit: Retrofit
        ): RandomUserApi =
            retrofit.create(RandomUserApi::class.java)

        @Provides @Singleton
        fun provideDaDataApi(
            @Named("daDataRetrofit") retrofit: Retrofit
        ): DaDataApi =
            retrofit.create(DaDataApi::class.java)

        // endregion

        // region DataStore

        @Provides
        @Singleton
        @Named("DADATA_TOKEN")
        fun provideDaDataToken(): String = DADATA_API_TOKEN

        @Provides @Singleton
        fun providePreferencesDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> =
            PreferenceDataStoreFactory.create(
                produceFile = {
                    File(context.filesDir, "datastore/settings.preferences_pb")
                }
            )

        // endregion
    }

}