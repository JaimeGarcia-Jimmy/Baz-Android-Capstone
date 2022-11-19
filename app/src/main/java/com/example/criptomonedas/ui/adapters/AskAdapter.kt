package com.example.criptomonedas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.criptomonedas.R
import com.example.criptomonedas.data.entities.Ask
import com.example.criptomonedas.databinding.ItemAskBinding

class AskAdapter() : ListAdapter<Ask, AskAdapter.ViewHolder>(AskDiffCallback) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemAskBinding.bind(itemView)

        fun bind(ask: Ask) {
            binding.tvAskPrice.text = "${ask.price}"
            binding.tvAskAmount.text = "${ask.amount}"
        }
    }

    object AskDiffCallback : DiffUtil.ItemCallback<Ask>() {
        override fun areItemsTheSame(oldItem: Ask, newItem: Ask): Boolean {
            return oldItem.price == newItem.price
        }

        override fun areContentsTheSame(oldItem: Ask, newItem: Ask): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ask, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
