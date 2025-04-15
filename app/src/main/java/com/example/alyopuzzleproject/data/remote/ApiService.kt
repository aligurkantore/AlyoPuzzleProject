package com.example.alyopuzzleproject.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("askme/candidates/2025_04/ali_g_rkan_t_re__6f0ba9b4")
    suspend fun getCandidateResponse(): String
}