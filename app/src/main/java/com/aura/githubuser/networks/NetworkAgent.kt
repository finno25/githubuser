package com.aura.githubuser.networks

import com.aura.githubuser.BuildConfig
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkAgent {
    private fun buildService(serverBaseUrl: String): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(serverBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    private val okHttpClient: OkHttpClient
        private get() {
            val httpClient = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                httpClient.addInterceptor(createLogginInterceptor())
            }
            return httpClient.build()
        }

    private fun createLogginInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun getGithubUserFinder(keyword: String, page: Int, callback: Callback<UsersResponse>) {
        buildService("https://api.github.com/").create<ApiInterface>(ApiInterface::class.java).getFindUsers(keyword, page, PER_PAGE)?.enqueue(callback)
    }

    companion object {
        val PER_PAGE = 50

        var instance: NetworkAgent? = null
            get() {
                if (field == null) {
                    field = NetworkAgent()
                }
                return field
            }
    }
}