package com.alfin.productlistingapp.presentation.screen.productdetail.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfin.productlistingapp.core.data.state.resolveState
import com.alfin.productlistingapp.data.constants.Constants
import com.alfin.productlistingapp.data.constants.Keys
import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.usecase.AddToCartUseCase
import com.alfin.productlistingapp.domain.usecase.DeleteCartProductUseCase
import com.alfin.productlistingapp.domain.usecase.GetCartCountUseCase
import com.alfin.productlistingapp.domain.usecase.GetCartProductUseCase
import com.alfin.productlistingapp.domain.usecase.GetProductUseCase
import com.alfin.productlistingapp.presentation.screen.productdetail.state.ProductDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

typealias UIState = com.alfin.productlistingapp.core.data.state.State

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductUseCase: GetProductUseCase,
    private val getCartProductUseCase: GetCartProductUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val deleteCartProductUseCase: DeleteCartProductUseCase,
    private val cartCountUseCase: GetCartCountUseCase
) : ViewModel() {


    private val _productDetailState = mutableStateOf(ProductDetailUIState())
    val productDetailState: State<ProductDetailUIState> = _productDetailState

    private val _cartCountState: MutableStateFlow<Int> = MutableStateFlow(value = 0)
    val cartCountState: MutableStateFlow<Int> get() = _cartCountState

    private var productId: Int =
        savedStateHandle.get<Int>(Keys.VALUE_PRODUCT_ID) ?: Constants.INVALID_ID

    init {
        getProduct()
        getCartCount()
    }

    fun getProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val deferredProductFromCart = async { getCartProductUseCase(id = productId) }
            val deferredProductFromAPI = async { getProductUseCase(productId = productId) }
            val productFromCartResult = deferredProductFromCart.await()
            val productFromAPIResult = deferredProductFromAPI.await()
            withContext(Dispatchers.Main) {
                productFromAPIResult.combine(productFromCartResult) { productFromAPI, productFromCart ->
                    productFromAPI.data?.dataState?.resolveState(
                        loading = {
                            _productDetailState.value = ProductDetailUIState().apply {
                                uiState = UIState.LOADING
                            }
                        },
                        success = {
                            val cartQuantity = productFromCart?.quantity ?: 0
                            productFromAPI.data.data.quantity = cartQuantity
                            _productDetailState.value = ProductDetailUIState().apply {
                                data = productFromAPI.data.data
                                uiState = UIState.SUCCESS
                            }
                        },
                        error = {
                            _productDetailState.value = ProductDetailUIState().apply {
                                errorMessage = productFromAPI.data.error
                                uiState = UIState.ERROR
                            }
                        }
                    )
                }.collect()
            }
        }
    }


    fun addToCart(product: Product, selectedQuantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addToCartUseCase(product = product.apply { quantity = selectedQuantity })
            withContext(Dispatchers.Main) {
                _productDetailState.value = ProductDetailUIState(product).apply {
                    data.apply {
                        quantity = selectedQuantity
                        uiState = _productDetailState.value.uiState
                        errorMessage = _productDetailState.value.errorMessage
                    }
                }
            }
            getCartCount()
        }
    }

    fun deleteCart(product: Product, selectedQuantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCartProductUseCase(product = product.apply { quantity = selectedQuantity })
            withContext(Dispatchers.Main) {
                _productDetailState.value = ProductDetailUIState(product).apply {
                    data.apply {
                        quantity = selectedQuantity
                        uiState = _productDetailState.value.uiState
                        errorMessage = _productDetailState.value.errorMessage
                    }
                }
            }
            getCartCount()
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
}
