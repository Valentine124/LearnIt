package com.valentine.learnit.internet.udemyapi


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.valentine.learnit.internet.CoursesResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.udemy.com/api-2.0/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder().apply {
    addInterceptor(UdemyInterceptor())
}.build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .baseUrl(BASE_URL).build()

interface UdemyService {

    @GET("courses/")
    suspend fun getCourses(
        @Query("category") category: String,
        @Query("page") page: Int
    ): CoursesResponse

    @GET("courses/")
    suspend fun getCoursesByCategory(
        @Query("category") category: String
    ): CoursesResponse

    @GET("courses/")
    suspend fun getSearchedCourses(
        @Query("search") query: String?,
        @Query("page") page: Int
    ): CoursesResponse

}

object UdemyApi {
    val udemyService: UdemyService by lazy {
        retrofit.create(UdemyService::class.java)
    }
}