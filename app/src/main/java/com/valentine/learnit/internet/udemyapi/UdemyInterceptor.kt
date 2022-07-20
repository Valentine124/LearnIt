package com.valentine.learnit.internet.udemyapi


import okhttp3.Interceptor
import okhttp3.Response



class UdemyInterceptor: Interceptor {

    private val base64ClientIdSecrete = "bHVuVklUcWtHQ2JMMU10bDlUOThoQzdpWXZzS051VWhUbUdrdW9pMzpvRVEyb2tIcEFxRmdaMmU4WktEYlFSVVVxQ0NNclJvY3JQQjVpakRhd1hkeXZEczFMQUswVkJJWWp2RVFJYXJkSHFRZHN2emtXUFhoTnVIZ00wZEU4YWZnMUVKRHNFQkVKdzFQSlpZWXZaSTB1YWhKSmFrUWdRQkF1Vjdaa1ViRQ=="


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Accept", "application/json, text/plain, */*")
            .addHeader("Authorization", "Basic $base64ClientIdSecrete")
            .addHeader("Content-Type", "application/json;charset=utf-8").build()

        return chain.proceed(request)
    }
}