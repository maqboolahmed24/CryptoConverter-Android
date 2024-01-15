package com.project.cryptoconverter

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.main_activity.*
import nl.yordanova.cryptoconverterapp.R
import java.math.BigDecimal

class MainActivity: AppCompatActivity() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var allCurrency: List<Data>
    var allCurrencySymbols: MutableList<String> = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val map = hashMapOf("Binance" to 0.1,
                            "Coinbase" to 1.49,
                            "Revolut" to 1.99,
                            "Paypal" to 2.3,
                            "Crypto.com" to 0.1,
                            "Robinhood" to 0,
                            "None, Just Total Amount" to 0)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        homeViewModel.fetchAllPosts()

        homeViewModel.postModelListLiveData?.observe(this, Observer {
            if (it!=null){
                allCurrency = it

                it.forEach {data ->
                    allCurrencySymbols.add(data.symbol.toString())
                }

                val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    allCurrencySymbols.toTypedArray()
                )

                filled_exposed_dropdown_from.setAdapter(adapter)
                filled_exposed_dropdown_to.setAdapter(adapter)

                val adapterForPercentage: ArrayAdapter<String?> = ArrayAdapter<String?>(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    map.keys.toTypedArray()
                )

                filled_exposed_dropdown_percentage.setAdapter(adapterForPercentage)


                Toast.makeText(this, "Data Recieved", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Data Error, Please Check your internet:)", Toast.LENGTH_LONG).show()
            }
        })

        convert.setOnClickListener(View.OnClickListener {

            if(filled_exposed_dropdown_percentage.text.isNotBlank()){

                var convertedAmount = allCurrency.find {
                    it.symbol == filled_exposed_dropdown_to.text.toString()
                }?.currentPrice?.times(totalAmount.text.toString().toDouble())


                var finalAmount = convertedAmount?.plus((map.get(filled_exposed_dropdown_percentage.text.toString())?.toDouble()!! / 100) * convertedAmount)

                final_amount.text = "The Total Amount is ${String.format("%.3f", finalAmount).toDouble()}GBP after ${map.get(filled_exposed_dropdown_percentage.text.toString())}% of Maker fees"
            }else{

                var convertedfrom = allCurrency.find {
                    it.symbol == filled_exposed_dropdown_from.text.toString()
                }

                var fromAmount : Double ?= allCurrency.find {
                    it.symbol == filled_exposed_dropdown_from.text.toString()
                }?.currentPrice?.times(totalAmount.text.toString().toDouble())

                var convertedToCurrency = allCurrency.find {
                    it.symbol == filled_exposed_dropdown_to.text.toString()
                }

                var convertedAmount = BigDecimal(fromAmount!!).div(BigDecimal(convertedToCurrency?.currentPrice!!))

                final_amount.text = "${totalAmount.text.toString()}${convertedfrom?.symbol?.toUpperCase()} = ${String.format("%.3f", convertedAmount).toDouble()}${convertedToCurrency?.symbol?.toUpperCase()}"
            }
        })
    }
}
