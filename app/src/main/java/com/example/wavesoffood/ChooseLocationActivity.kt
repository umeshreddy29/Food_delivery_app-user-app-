package com.example.wavesoffood

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.databinding.ActivityChooseLocationBinding
import com.example.wavesoffood.databinding.ActivityStartBinding

class ChooseLocationActivity : AppCompatActivity() {

    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList: Array<String> = arrayOf("Jaipur", "Odisha", "Bundi", "Sikar")
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView: AutoCompleteTextView = binding.listoflocation
        autoCompleteTextView.setAdapter(adapter)


    }
}