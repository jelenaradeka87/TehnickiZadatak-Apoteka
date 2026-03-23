package com.example.tehnickizadatak_apoteka.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tehnickizadatak_apoteka.data.model.ProductResponse
import com.example.tehnickizadatak_apoteka.data.repository.ProductRepository
import com.example.tehnickizadatak_apoteka.core.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<ProductResponse>>>(Resource.Loading())
    val uiState: StateFlow<Resource<List<ProductResponse>>> = _uiState

    private val pageSize = 20
    private var skip = 0
    private var isLoading = false
    private var endReached = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading || endReached) return
        viewModelScope.launch {
            try {
                isLoading = true
                if (skip == 0) _uiState.value = Resource.Loading()

                val page = repository.getProductsPage(pageSize, skip)
                val current = (uiState.value as? Resource.Success)?.data ?: emptyList()
                val merged = current + page
                _uiState.value = Resource.Success(merged)

                if (page.size < pageSize) {
                    endReached = true
                } else {
                    skip += pageSize
                }
            } catch (t: Throwable) {
                Log.e("AllProductsViewModel", "Error loading page", t)
                _uiState.value = if (skip == 0) {
                    Resource.Error(t.message ?: "Unknown error", t)
                } else {
                    (uiState.value as? Resource.Success<List<ProductResponse>>) ?: Resource.Error(t.message ?: "Unknown error", t)
                }
                endReached = true
            } finally {
                isLoading = false
            }
        }
    }

    fun addFavorite(product: ProductResponse) {
        viewModelScope.launch {
            try {
                repository.addToFavorites(product)
            } catch (t: Throwable) {
                Log.e("AllProductsViewModel", "Failed to add favorite", t)
            }
        }
    }
}
