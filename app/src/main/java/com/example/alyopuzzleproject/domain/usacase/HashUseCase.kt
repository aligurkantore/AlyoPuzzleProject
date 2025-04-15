package com.example.alyopuzzleproject.domain.usacase

import com.example.alyopuzzleproject.domain.repository.HashRepository
import javax.inject.Inject

class HashUseCase @Inject constructor(
    private val hashRepository: HashRepository
) {
    suspend operator fun invoke() = hashRepository.hashRequest()
    //Since there is no data to be manipulated, only return operations were performed here
}