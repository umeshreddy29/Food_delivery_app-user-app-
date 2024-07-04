package com.example.wavesoffood.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wavesoffood.databinding.CartItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(

    private val context: Context,
    private val cartItems: MutableList<String>,
    private val cartItemPrices: MutableList<String>,
    private val cartDescriptions: MutableList<String>,
    private val cartImages: MutableList<String>,
    private val cartQuantity: MutableList<Int>,
    private val cartIngredient: MutableList<String>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    //initialize
    private val auth = FirebaseAuth.getInstance()

    init {
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid ?: ""
        val cartItemNumber = cartItems.size

        itemQualities = IntArray(cartItemNumber) { 1 }
        cartItemsReference = database.reference.child("user").child(userId).child("CartItems")
    }

    companion object {
        private var itemQualities: IntArray = intArrayOf()
        private lateinit var cartItemsReference: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQualities[position]
                cartFoodName.text = cartItems[position]
                cartItemPrice.text = cartItemPrices[position]

                //glide
                val uriString = cartImages[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartImage)
                //

                cartItemQuantity.text = quantity.toString()

                minusbutton.setOnClickListener {
                    deceaseQuantity(position)
                }
                plusbutton.setOnClickListener {
                    increaseQuantity(position)
                }
                deletebutton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteItem(itemPosition)
                    }
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQualities[position] < 10) {
                itemQualities[position]++
                cartQuantity[position] = itemQualities[position]
                binding.cartItemQuantity.text = itemQualities[position].toString()
            }
        }

        private fun deceaseQuantity(position: Int) {
            if (itemQualities[position] > 1) {
                itemQualities[position]--
                cartQuantity[position] = itemQualities[position]
                binding.cartItemQuantity.text = itemQualities[position].toString()
            }
        }

        private fun deleteItem(position: Int) {
            val positionRetrive = position
            getUniqueKeyAtPosition(positionRetrive){uniqueKey ->
                if(uniqueKey != null)
                {
                    removeItem(position,uniqueKey)
                }

            }

        }

        private fun removeItem(position: Int, uniqueKey: String) {
            if(uniqueKey != null)
            {
                cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    cartItems.removeAt(position)
                    cartImages.removeAt(position)
                    cartDescriptions.removeAt(position)
                    cartQuantity.removeAt(position)
                    cartItemPrices.removeAt(position)
                    cartIngredient.removeAt(position)
                    Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show()
                    // update itemquantities
                    itemQualities = itemQualities.filterIndexed{ index, i -> index!= position  }.toIntArray()
                        notifyItemRemoved(position)
                    notifyItemRangeChanged(position,cartItems.size)

                }.addOnSuccessListener {
                    Toast.makeText(context,"Failed to delete ",Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

    private fun getUniqueKeyAtPosition(positionRetrive: Int, onComplete:(String?) -> Unit ) {

        cartItemsReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var uniqueKey:String? = null

                snapshot.children.forEachIndexed { index ,dataSnapshot ->
                    if(index == positionRetrive)
                    {
                        uniqueKey= dataSnapshot.key
                        return@forEachIndexed
                    }
                }
                onComplete(uniqueKey)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getUpdatedItemQuantities(): MutableList<Int> {
        val itemQuantity  = mutableListOf<Int>()
        itemQuantity.addAll(cartQuantity)
        return itemQuantity

    }

}