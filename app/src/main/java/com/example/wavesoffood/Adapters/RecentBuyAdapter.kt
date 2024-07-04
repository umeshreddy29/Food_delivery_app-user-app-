package com.example.wavesoffood.Adapters




import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.RecentBuyItemBinding

class RecentBuyAdapter(
    private var context : Context,
    private val foodNameList: ArrayList<String>,
    private val foodPriceList: ArrayList<String>,
    private val foodImageList: ArrayList<String>,
    private val foodQuantityList: ArrayList<Int>

) : RecyclerView.Adapter<RecentBuyAdapter.RecentBuyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentBuyViewHolder {
        val binding =
            RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentBuyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentBuyViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return foodNameList.size
    }

    inner class RecentBuyViewHolder(private val binding: RecentBuyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.foodName.text = foodNameList[position]
            binding.foodPrice.text = foodPriceList[position]
            binding.foodQuantity.text = foodQuantityList[position].toString()
            val uriString = foodImageList[position]
            val uri = Uri.parse(uriString)
            Glide.with(context).load(uri).into(binding.foodImage)

        }
    }

}