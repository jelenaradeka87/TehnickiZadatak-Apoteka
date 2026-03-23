package com.example.tehnickizadatak_apoteka.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tehnickizadatak_apoteka.R
import com.example.tehnickizadatak_apoteka.ui.adapter.FavoriteProductAdapter
import com.example.tehnickizadatak_apoteka.ui.viewModel.FavoritesViewModel
import com.example.tehnickizadatak_apoteka.core.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var favoritesAdapter: FavoriteProductAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvFavorites)
        progressBar = view.findViewById(R.id.progressBar)

        favoritesAdapter = FavoriteProductAdapter(
            onItemClick = {}
        )

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = favoritesAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is Resource.Loading -> progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            progressBar.visibility = View.GONE
                            favoritesAdapter.submitList(state.data)
                        }
                        is Resource.Error -> {
                            progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                            Log.e("FavoritesFragment",
                                "Error loading favorite products: ${state.message}", state.throwable)
                        }
                    }
                }
            }
        }
    }
}