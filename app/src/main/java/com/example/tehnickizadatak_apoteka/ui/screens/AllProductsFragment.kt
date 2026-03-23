package com.example.tehnickizadatak_apoteka.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tehnickizadatak_apoteka.R
import com.example.tehnickizadatak_apoteka.ui.adapter.AllProductsAdapter
import com.example.tehnickizadatak_apoteka.ui.viewModel.AllProductsViewModel
import com.example.tehnickizadatak_apoteka.core.Constants
import com.example.tehnickizadatak_apoteka.core.Resource
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllProductsFragment : Fragment() {

    private val viewModel: AllProductsViewModel by viewModels()

    private lateinit var productAdapter: AllProductsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var btnFavorite: ImageButton
    private lateinit var tvError: android.widget.TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvProducts)
        btnFavorite = view.findViewById<ImageButton>(R.id.btnFavorite)
        progressBar = view.findViewById(R.id.progressBar)
        tvError = view.findViewById(R.id.tvError)
        val shimmer = view.findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)


        productAdapter = AllProductsAdapter(
            onItemClick = { product ->
                Log.d("AllProductsFragment", "ITEM CLICK: ${product.title}")
                val args = Bundle().apply { putInt("productId", product.id) }
                findNavController().navigate(R.id.productDetailsFragment, args)
            },
            onFavoriteClick = { product ->
                Log.d("AllProductsFragment", "FAVORITE CLICK: ${product.title}")
                Toast.makeText(requireContext(),R.string.added_to_favorites, Toast.LENGTH_SHORT).show()
                viewModel.addFavorite(product)
            }
        )

        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager
        rv.adapter = productAdapter

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return
                val totalItemCount = layoutManager.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val threshold = Constants.PAGINATION_THRESHOLD
                if (totalItemCount - lastVisible <= threshold) {
                    viewModel.loadNextPage()
                }
            }
        })

        btnFavorite.setOnClickListener {
            findNavController().navigate(R.id.favoritesFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            tvError.visibility = View.GONE
                            rv.visibility = View.GONE
                            Log.d("AllProductsFragment", "Loading products...")
                            shimmer.visibility = View.VISIBLE
                            shimmer.startShimmer()
                        }
                        is Resource.Success -> {
                            shimmer.stopShimmer()
                            shimmer.visibility = View.GONE
                            tvError.visibility = View.GONE
                            val list = state.data
                            productAdapter.submitList(list)
                            rv.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            shimmer.stopShimmer()
                            shimmer.visibility = View.GONE
                            rv.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            tvError.text = state.message

                            Log.e("AllProductsFragment",
                                "Error loading products: ${state.message}",
                                state.throwable)
                        }
                    }
                }
            }
        }
    }
}