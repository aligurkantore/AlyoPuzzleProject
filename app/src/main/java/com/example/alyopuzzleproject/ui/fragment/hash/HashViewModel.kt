package com.example.alyopuzzleproject.ui.fragment.hash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alyopuzzleproject.domain.repository.HashRepository
import com.example.alyopuzzleproject.domain.usacase.HashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HashViewModel @Inject constructor(private var useCase: HashUseCase) : ViewModel() {

    private val _response = MutableStateFlow<String?>(null)
    val response: StateFlow<String?> = _response

    init {
        fetchCandidateData()
    }

    private fun fetchCandidateData() {
        viewModelScope.launch {
            try {
                val result = useCase.invoke()
                _response.value = result
                Log.d("HTTP", "Success: $result")
            } catch (e: Exception) {
                Log.d("HTTP", "Error: ${e.message}")
            }
        }
    }
}