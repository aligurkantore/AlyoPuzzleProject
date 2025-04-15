package com.example.alyopuzzleproject.util

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigateSafe(id: Int) {
    findNavController().navigate(id)
}