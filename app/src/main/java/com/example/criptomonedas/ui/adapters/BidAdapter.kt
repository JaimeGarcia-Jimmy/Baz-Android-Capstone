package com.example.criptomonedas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.criptomonedas.R
import com.example.criptomonedas.data.entities.Bid
import com.example.criptomonedas.databinding.ItemBidBinding

class BidAdapter() : ListAdapter<Bid, BidAdapter.ViewHolder>(BidDiffCallback) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemBidBinding.bind(itemView)

        fun bind(bid: Bid) {
            binding.tvBidPrice.text = "${bid.price}"
            binding.tvBidAmount.text = "${bid.amount}"
        }
    }

    object BidDiffCallback : DiffUtil.ItemCallback<Bid>() {
        override fun areItemsTheSame(oldItem: Bid, newItem: Bid): Boolean {
            return oldItem.price == newItem.price
        }

        override fun areContentsTheSame(oldItem: Bid, newItem: Bid): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bid, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
