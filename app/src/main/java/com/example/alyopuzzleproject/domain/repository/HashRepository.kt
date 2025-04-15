package com.example.alyopuzzleproject.domain.repository

import com.example.alyopuzzleproject.data.remote.ApiService
import javax.inject.Inject

class HashRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun hashRequest() = apiService.getCandidateResponse()
}
