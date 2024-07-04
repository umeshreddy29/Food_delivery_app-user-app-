package com.example.wavesoffood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapters.NotificationAdapter
import com.example.wavesoffood.databinding.FragmentCartBinding
import com.example.wavesoffood.databinding.FragmentNotificationBottomFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Notification_bottom_fragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomFragmentBinding.inflate(inflater, container, false)
        val notifications = listOf("Your Order has been cancelled Successfully", "Your Order has been taken by the driver ","congrats your order has been placed")
        val notificationImages = listOf(R.drawable.sademoji,R.drawable.truck,R.drawable.tick)
        val adapter = NotificationAdapter(ArrayList(notifications),ArrayList(notificationImages))

        binding.NotificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.NotificationRecyclerView.adapter = adapter

        return binding.root
    }

    companion object {

    }
}