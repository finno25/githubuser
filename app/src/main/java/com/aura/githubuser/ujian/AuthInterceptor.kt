package com.aura.githubuser.ujian

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(val tokenStorage: TokenStorage) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        val original = chain.request()
        val builder: Request.Builder = original.newBuilder()
        val token = tokenStorage.retrieveToken()
        if(!token.isNullOrEmpty()) {
            builder.header("Authorization", "Bearer ${token}")
        }
        val newRequest = builder.method(original.method(), original.body()).build()
        return chain.proceed(newRequest)
    }
}