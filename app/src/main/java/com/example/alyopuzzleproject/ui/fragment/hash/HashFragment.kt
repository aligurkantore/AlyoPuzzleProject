package com.example.alyopuzzleproject.ui.fragment.hash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.alyopuzzleproject.R
import com.example.alyopuzzleproject.databinding.FragmentHashBinding
import com.example.alyopuzzleproject.util.navigateSafe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HashFragment : Fragment() {

    private var binding: FragmentHashBinding? = null
    private var viewModel: HashViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHashBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initObserver()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[HashViewModel::class.java]
    }

    private fun initObserver(){
        lifecycleScope.launchWhenStarted {
            viewModel?.response?.collectLatest { response ->
                response?.let {
                    Log.d("HTTP", "onCreate:$response ")
                    //The response is successful.
                    binding?.apply {
                        navigationResult.setOnClickListener {
                            navigateSafe(R.id.action_hashFragment_to_resultFragment)
                        }
                    }
                }
            }
        }
    }
}