package com.alfin.productlistingapp.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.usecase.GetCartCountUseCase
import com.alfin.productlistingapp.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userCase: GetProductsUseCase,
    private val cartCountUseCase: GetCartCountUseCase
) : ViewModel() {

    private val _productsState: MutableStateFlow<PagingData<Product>> =
        MutableStateFlow(value = PagingData.empty())
    val productsState: MutableStateFlow<PagingData<Product>> get() = _productsState

    private val _cartCountState: MutableStateFlow<Int> = MutableStateFlow(value = 0)
    val cartCountState: MutableStateFlow<Int> get() = _cartCountState

    init {
        fetchProducts()
        getCartCount()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            getProducts()
        }
    }
    fun getCartCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val cartCount = cartCountUseCase()
            withContext(Dispatchers.Main) {
                _cartCountState.value = cartCount
            }
        }
    }

    suspend fun getProducts() {
        userCase()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _productsState.value = it
            }
    }

}