package com.example.currency_converter

import android.app.DownloadManager
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.currency_converter.databinding.FragmentFirstBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import kotlin.math.round
import java.math.BigDecimal
import java.math.RoundingMode


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    fun createUrl(a: String,b: String,c: String): String {
        return "https://currencyconverter.p.rapidapi.com/?from_amount=$a&from=$b&to=$c"
    }

    fun createMsg(a: String,b: String): String {
        return "$a EUR to $b USD."
    }

    private fun apiCurrency(view: View) {
        val kwota = view.findViewById<TextView>(R.id.podajKwote)
        val urlString = createUrl(kwota.text.toString(),"EUR","USD")

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(urlString.toString())
            .get()
            .addHeader("x-rapidapi-host", "currencyconverter.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "NOT-AUTHORIZED")
            .build()

        val response = client.newCall(request).execute()
    }

    private fun countMe(view: View) {

        val podanaKwota = view.findViewById<TextView>(R.id.podajKwote).text.toString()
        var nowaKwota =BigDecimal(podanaKwota.toDouble() * 1.13).setScale(2, RoundingMode.HALF_EVEN)
        view.findViewById<TextView>(R.id.kwotaPrzeliczona).text = createMsg(podanaKwota, nowaKwota.toString())

        // 30 sekund to trochę długi czas oczekiwania, ale gdyby miało być 30 to tutaj dalibyśmy wartość 30000 zamiast 5000
        Thread.sleep(5000);
        view.findViewById<TextView>(R.id.kwotaPrzeliczona).isVisible = true

    }

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.przeliczButton).setOnClickListener {
            countMe(view)

        }
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

