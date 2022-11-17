package com.example.criptomonedas.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.criptomonedas.R
import com.example.criptomonedas.data.entities.Book
import com.example.criptomonedas.databinding.ItemBookBinding

class BookAdapter(
    val booksList: MutableList<Book>,
    val onClick: (bookId: String) -> Unit
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemBookBinding.bind(itemView)

        fun bind(book: Book) {
            val uri = "android.resource://com.example.criptomonedas/drawable/" + book.bookId.substringBefore('_')
            Glide.with(itemView.context).load(Uri.parse(uri)).into(binding.ivBook)
            binding.tvBook.text = book.bookId
            binding.root.setOnClickListener {
                onClick(book.bookId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(booksList[position])
    }

    override fun getItemCount(): Int {
        return booksList.size
    }
}
