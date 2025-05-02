package com.alekseilomain.medoffice.di

import android.content.Context
import androidx.room.Room
import com.alekseilomain.data.database.AppDatabase
import com.alekseilomain.data.database.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "medoffice.db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Singleton
    @Provides
    fun provideContactDao(
        database: AppDatabase
    ): ContactDao {
        return database.contactDao()
    }
}
