package remote.randomuser

import remote.randomuser.model.RandomUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("api/")
    suspend fun getUsers(
        @Query("seed")    seed:   String,
        @Query("page")    page:   Int,
        @Query("results") results: Int = 20
    ): Response<RandomUserResponse>
}