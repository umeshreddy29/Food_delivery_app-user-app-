package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapters.RecentBuyAdapter
import com.example.wavesoffood.databinding.ActivityRecentOrderItemsBinding
import com.example.wavesoffood.models.OrderDetails

class recentOrderItems : AppCompatActivity() {

    private lateinit var allFoodNames: ArrayList<String>
    private lateinit var allFoodImages: ArrayList<String>
    private lateinit var allFoodPrices: ArrayList<String>
    private lateinit var allFoodQuantities: ArrayList<Int>

    private val binding: ActivityRecentOrderItemsBinding by lazy {
        ActivityRecentOrderItemsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        val recentOrderItems =
            intent.getSerializableExtra("RecentBuyOrderItem") as ArrayList<OrderDetails>
        recentOrderItems?.let { orderDetails ->
            if (orderDetails.isNotEmpty()) {

                val recentOrderItem = orderDetails[0]

                allFoodNames = recentOrderItem.foodNames as ArrayList<String>
                allFoodImages = recentOrderItem.foodImages as ArrayList<String>
                allFoodPrices = recentOrderItem.foodPrices as ArrayList<String>
                allFoodQuantities = recentOrderItem.foodQuantities as ArrayList<Int>

            }
        }
        setAdapter()
    }

    private fun setAdapter() {
        binding.recentBuyRecyclerview.layoutManager = LinearLayoutManager(this)
        val adapter =
            RecentBuyAdapter(this, allFoodNames, allFoodPrices, allFoodImages, allFoodQuantities)
        binding.recentBuyRecyclerview.adapter = adapter
    }

}