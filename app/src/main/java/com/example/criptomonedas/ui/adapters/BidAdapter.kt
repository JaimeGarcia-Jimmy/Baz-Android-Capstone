package com.example.criptomonedas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criptomonedas.R
import com.example.criptomonedas.data.entities.Bid
import com.example.criptomonedas.databinding.ItemBidBinding

class BidAdapter(
    val bidsList: MutableList<Bid>
) : RecyclerView.Adapter<BidAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemBidBinding.bind(itemView)

        fun bind(bid: Bid) {
            binding.tvBidPrice.text = "Price: ${bid.price}"
            binding.tvBidAmount.text = "Amount: ${bid.amount}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_bid, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bidsList[position])
    }

    override fun getItemCount(): Int {
        return bidsList.size
    }
}
