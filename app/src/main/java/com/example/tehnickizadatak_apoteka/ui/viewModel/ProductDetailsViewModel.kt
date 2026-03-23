package com.example.tehnickizadatak_apoteka.ui.viewModel

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
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<ProductResponse>>(Resource.Loading())
    val uiState: StateFlow<Resource<ProductResponse>> = _uiState

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            try {
                val product = repository.getProductById(id)
                _uiState.value = Resource.Success(product)
            } catch (t: Throwable) {
                _uiState.value = Resource.Error(t.message ?: "Unknown error", t)
            }
        }
    }
}