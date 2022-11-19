package com.example.criptomonedas.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.criptomonedas.Constants
import com.example.criptomonedas.R
import com.example.criptomonedas.data.entities.Book
import com.example.criptomonedas.databinding.ItemBookBinding

class BookAdapter(
    val onClick: (bookId: String) -> Unit
) : ListAdapter<Book, BookAdapter.ViewHolder>(BookDiffCallback) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemBookBinding.bind(itemView)

        fun bind(book: Book) {
            val uri = Constants.BOOK_ICONS_DIRECTORY + book.bookId.substringBefore('_')
            Glide.with(itemView.context).load(Uri.parse(uri)).into(binding.ivBook)
            binding.tvBook.text = book.bookId
            binding.root.setOnClickListener {
                onClick(book.bookId)
            }
        }
    }

    object BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.bookId == newItem.bookId
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
