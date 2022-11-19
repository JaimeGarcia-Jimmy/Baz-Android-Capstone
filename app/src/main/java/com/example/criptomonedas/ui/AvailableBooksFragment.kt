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
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.databinding.FragmentAvailableBooksBinding
import com.example.criptomonedas.ui.adapters.BookAdapter
import com.example.criptomonedas.ui.viewmodels.AvailableBooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AvailableBooksFragment : Fragment() {

    private var _binding: FragmentAvailableBooksBinding? = null
    private val binding: FragmentAvailableBooksBinding get() = _binding!!
    private val viewModel: AvailableBooksViewModel by viewModels<AvailableBooksViewModel>()
    private val adapter = BookAdapter() { bookId -> openBookDetail(bookId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvailableBooksBinding.inflate(inflater, container, false)
        binding.rvBooks.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.booksList.collect() {
                binding.swipeBooks.isRefreshing = false

                when (it) {
                    is Resource.Error -> Toast.makeText(
                        activity,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    is Resource.Loading -> binding.swipeBooks.isRefreshing = true
                    is Resource.Success -> {
                        adapter.submitList(it.data)
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

        val action =
            AvailableBooksFragmentDirections
                .actionAvailableBooksFragmentToBookDetailFragment(bookId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
