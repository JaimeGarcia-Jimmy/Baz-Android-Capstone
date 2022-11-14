package com.example.criptomonedas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criptomonedas.R
import com.example.criptomonedas.data.entities.Ask
import com.example.criptomonedas.databinding.ItemAskBinding

class AskAdapter(
    val asksList: MutableList<Ask>
): RecyclerView.Adapter<AskAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val binding = ItemAskBinding.bind(itemView)

        fun bind(ask: Ask) {
            binding.tvAskPrice.text = "Price: ${ask.price}"
            binding.tvAskAmount.text = "Amount: ${ask.amount}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ask, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asksList[position])
    }

    override fun getItemCount(): Int {
        return asksList.size
    }
}