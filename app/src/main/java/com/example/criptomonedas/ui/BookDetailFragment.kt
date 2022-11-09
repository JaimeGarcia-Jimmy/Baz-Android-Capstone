package com.example.criptomonedas.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.criptomonedas.data.Resource
import com.example.criptomonedas.databinding.FragmentBookDetailBinding
import com.example.criptomonedas.ui.adapters.AskAdapter
import com.example.criptomonedas.ui.adapters.BidAdapter
import com.example.criptomonedas.ui.viewmodels.BookDetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookDetailFragment: Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding: FragmentBookDetailBinding get() = _binding!!
    private val args: BookDetailFragmentArgs by navArgs()
    private val viewModel: BookDetailViewModel by viewModels()
    private val askAdapter = AskAdapter( mutableListOf() )
    private val bidAdapter = BidAdapter( mutableListOf() )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        var uri = "android.resource://com.example.criptomonedas/drawable/"+args.bookId?.substringBefore('_')
        Glide.with(requireActivity()).load(Uri.parse(uri)).into(binding.ivBookDetail)
        binding.tvBookId.text = args.bookId
        binding.rvAsks.adapter = askAdapter
        binding.rvBids.adapter = bidAdapter

        //Start viewmodel flows
        viewModel.getBookById(args.bookId!!)

        //Collect Book Details
        lifecycleScope.launch {
            viewModel.book.collect() {
                when(it) {
                    is Resource.Error -> Toast.makeText(
                        activity, it.message, Toast.LENGTH_LONG
                    ).show()
                    is Resource.Loading -> null
                    is Resource.Success -> {
                        binding.tvLastValue.text = "Last: ${it.data.last.toString()}"
                        binding.tvHighValue.text = "High: ${it.data.high.toString()}"
                        binding.tvLowValue.text = "Low: ${it.data.low.toString()}"
                    }
                }

            }
        }

        //Collect Book Asks
        lifecycleScope.launch {
            viewModel.asks.collect() {
                when(it) {
                    is Resource.Error -> Toast.makeText(
                        activity, it.message, Toast.LENGTH_LONG
                    ).show()
                    is Resource.Loading -> null
                    is Resource.Success -> {
                        askAdapter.asksList.clear()
                        askAdapter.asksList.addAll(it.data)
                        binding.rvAsks.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }

        //Collect Book Bids
        lifecycleScope.launch {
            viewModel.bids.collect() {
                when(it) {
                    is Resource.Error -> Toast.makeText(
                        activity, it.message, Toast.LENGTH_LONG
                    ).show()
                    is Resource.Loading -> null
                    is Resource.Success -> {
                        bidAdapter.bidsList.clear()
                        bidAdapter.bidsList.addAll(it.data)
                        binding.rvBids.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

}