package com.example.currency_converter

import android.app.DownloadManager
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.currency_converter.databinding.FragmentFirstBinding
import okhttp3.RequestBody.Companion.toRequestBody


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private fun countMe(view: View) {

        val kwota = view.findViewById<TextView>(R.id.podajKwote)

        val kwotaString = kwota.text.toString()

        var kwotaInt = kwotaString.toInt() * 3.40

        // Display the new value in the text view.
        view.findViewById<TextView>(R.id.kwotaPrzeliczona).text = kwotaInt.toString()
    }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        //binding.przeliczButton.setOnClickListener {
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

