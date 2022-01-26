package com.example.currency_converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.currency_converter.databinding.FragmentFirstBinding
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class FirstFragment : Fragment() {
    var jsonObj : JSONObject = TODO()
    fun createUrl(old_amount: String,old_currency: String,new_currency: String): String {
        return "https://currency-converter-by-api-ninjas.p.rapidapi.com/v1/convertcurrency?have=$old_currency&want=$new_currency&amount=$old_amount"
    }

    fun createMsg(old_amount: String,new_amount: String, old_currency: String, new_currency: String ): String {
        return "$old_amount $old_currency to $new_amount $new_currency."
    }

    fun connectApi(old_amount: String, old_currency: String, new_currency: String): JSONObject {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(createUrl(old_amount,old_currency,new_currency))
            .get()
            .addHeader("x-rapidapi-host", "currency-converter-by-api-ninjas.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "9e7ed72733mshab0cd3c7461f069p17ef3bjsn9c88d40083eb")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {println("chujnia")}
            override fun onResponse(call: Call, response: Response ) {
                val answer = response.body?.string()
                println(answer)
                jsonObj = JSONObject(answer?.substring(answer.indexOf("{"), answer.lastIndexOf("}") + 1))
            }
        })
    return jsonObj
    }

    fun recalculate(view: View) {

        val old_amount = view.findViewById<TextView>(R.id.podajKwote).text.toString()
        val old_currency = "EUR"
        val new_currency = "USD"

        val jsonObj = connectApi(old_amount, old_currency, new_currency )
        val new_amount = jsonObj.getDouble("new_amount").toString()
        //val new_currency= jsonObj.getString("new_currency")
        //val old_currency= jsonObj.getString("old_currency")
        //val old_amount= jsonObj.getDouble("old_amount").toString()

        view.findViewById<TextView>(R.id.kwotaPrzeliczona).text = createMsg(old_amount, new_amount, old_currency, new_currency)

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
            recalculate(view)
        }
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


