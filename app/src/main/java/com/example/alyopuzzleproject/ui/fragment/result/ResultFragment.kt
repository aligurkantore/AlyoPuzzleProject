package com.example.alyopuzzleproject.ui.fragment.result

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.alyopuzzleproject.databinding.FragmentResultBinding
import java.math.BigInteger
import java.security.MessageDigest

class ResultFragment : Fragment() {

    private var binding: FragmentResultBinding? = null
    private var viewModel: ResultViewModel? = null

    private val longHash = """
        f894e71e1551d1833a977df952d0cc9de44a1f9669fbf97d51309a2c6574d5eaa746cdeb9ee1a5dfc771d280d33e567204c2b7f12a3b18bf3470c7ca102a33b6e48a0b49e999dc7d88f3e7073040596e98687c4d1730f3ac2fb2fe4f3e2fba5607abfd6503ad39e8f3b7c63490b53eba9475e75c59875f8a5a87e6dc1db92cbfea240afcb4f9676b13e26802f796bda3090f08f58cab194d1300b17910a63ac4d656b1645be74319601bdc35f1ea4f51230adbe957928ea13b35cf1274968c5ec91e8022879e2e52cc779263f0d21cffd5acc468396b4556d357fdb2118f319e1605aac7e849f7cb2cd9a04322ebb39773345ff253b3aa09375da98f17812543ddbdb41fe4d2f1127fef95cc95337de5fdafe0324b2a6c7cfbd1375098b5499d
    """.trimIndent().replace("\n", "").replace(" ", "")

    private val allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_+.@"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        decodeEmail()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[ResultViewModel::class.java]
    }

    private fun decodeEmail() {
        val hashSegments = longHash.chunked(32)
        val result = StringBuilder()
        val candidates = mutableListOf<String>()

        for (a in allowedChars) {
            for (b in allowedChars) {
                candidates.add("$a$b")
            }
        }

        for ((index, targetHash) in hashSegments.withIndex()) {
            var found = false
            for (candidate in candidates) {
                val md5s = md5(candidate)
                val j = md5(md5s + candidate + md5s)
                if (j == targetHash) {
                    result.append(candidate)
                    Log.d("Match", "Segment $index: $candidate")
                    found = true
                    break
                }
            }
            if (!found) {
                Log.d("Error", "Segment $index didnt match!")
                break
            }
        }

        val decodedEmail = result.toString()
        Log.d("DecodedEmail", "Resolved email address: $decodedEmail")

        binding?.textViewShowResult?.text = decodedEmail
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray()))
            .toString(16).padStart(32, '0')
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
