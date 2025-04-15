package com.example.alyopuzzleproject.di

import com.example.alyopuzzleproject.data.remote.ApiService
import com.example.alyopuzzleproject.util.Constants.BASE_URL
import com.example.alyopuzzleproject.util.Constants.HEADER_REFERER_NAME
import com.example.alyopuzzleproject.util.Constants.HEADER_REFERER_VALUE
import com.example.alyopuzzleproject.util.Constants.HEADER_USER_AGENT_NAME
import com.example.alyopuzzleproject.util.Constants.HEADER_USER_AGENT_VALUE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        HEADER_USER_AGENT_NAME,
                        HEADER_USER_AGENT_VALUE
                    )
                    .addHeader(HEADER_REFERER_NAME, HEADER_REFERER_VALUE)
                    .build()
                chain.proceed(request)
            }.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
