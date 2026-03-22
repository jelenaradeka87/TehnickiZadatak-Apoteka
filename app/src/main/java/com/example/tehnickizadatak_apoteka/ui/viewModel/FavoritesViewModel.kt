package com.example.tehnickizadatak_apoteka.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tehnickizadatak_apoteka.data.local.entity.ProductEntity
import com.example.tehnickizadatak_apoteka.data.repository.ProductRepository
import com.example.tehnickizadatak_apoteka.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<Resource<List<ProductEntity>>> =
        MutableStateFlow(Resource.Loading())
    val uiState: StateFlow<Resource<List<ProductEntity>>> = _uiState

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            try {
                repository.observeFavorites().collectLatest { list ->
                    _uiState.value = Resource.Success(list)
                }
            } catch (t: Throwable) {
                _uiState.value = Resource.Error(t.message ?: "Unknown error", t)
            }
        }
    }
}
