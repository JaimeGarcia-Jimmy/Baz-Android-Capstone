package com.example.criptomonedas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.criptomonedas.R
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.databinding.FragmentAvailableBooksBinding
import com.example.criptomonedas.ui.adapters.BookAdapter
import com.example.criptomonedas.ui.viewmodels.AvailableBooksViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AvailableBooksFragment: Fragment() {

    private var _binding: FragmentAvailableBooksBinding? = null
    private val binding: FragmentAvailableBooksBinding get() = _binding!!
    private val viewModel: AvailableBooksViewModel by viewModels<AvailableBooksViewModel>()
    private val adapter = BookAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvailableBooksBinding.inflate(inflater, container, false)
        binding.rvBooks.adapter = adapter
        lifecycleScope.launch {
            viewModel.booksList.collect() {
                binding.swipeBooks.isRefreshing = false

                when(it) {
                    is Resource.Error -> Toast.makeText(
                        activity, it.message, Toast.LENGTH_LONG
                    ).show()
                    is Resource.Loading -> binding.swipeBooks.isRefreshing = true
                    is Resource.Success -> {
                        adapter.booksList.clear()
                        adapter.booksList.addAll(it.data)
                        binding.rvBooks.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        binding.swipeBooks.setOnRefreshListener {
            viewModel.updateBooks()
        }

        return binding.root
    }

    private fun openBookDetail(bookId: String) {
        findNavController().navigate(
            R.id.action_availableBooksFragment_to_bookDetailFragment,
            Bundle().apply {
                putString("bookId", bookId)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}