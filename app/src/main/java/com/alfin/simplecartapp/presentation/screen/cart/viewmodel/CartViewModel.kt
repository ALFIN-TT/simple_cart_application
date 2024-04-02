package com.alfin.simplecartapp.presentation.screen.cart.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.usecase.AddToCartUseCase
import com.alfin.simplecartapp.domain.usecase.DeleteCartProductUseCase
import com.alfin.simplecartapp.domain.usecase.GetCartUseCase
import com.alfin.simplecartapp.domain.usecase.GetTotalAmountUseCase
import com.alfin.simplecartapp.presentation.screen.cart.state.CartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val getTotalAmountUseCase: GetTotalAmountUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val deleteCartProductUseCase: DeleteCartProductUseCase
) : ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    private val _isCartUpdated = mutableStateOf(false)
    val isCartUpdated: State<Boolean> = _isCartUpdated

    fun fetchCart() {
        viewModelScope.launch(Dispatchers.IO) {
            getCart()
        }
    }

    private suspend fun getCart() {
        coroutineScope {
            val deferredProductsFromCart = async { getCartUseCase() }
            val deferredTotalAmount = async { getTotalAmountUseCase() }
            val productsFromCart = deferredProductsFromCart.await()
            val totalAmountResult = deferredTotalAmount.await()
            withContext(Dispatchers.Main) {
                productsFromCart.combine(totalAmountResult) { productFromCart, totalAmount ->
                    _cartState.value = CartState(productFromCart, totalAmount)
                }.collect()
            }
        }
    }

    fun addToCart(product: Product, selectedQuantity: Int) = viewModelScope.launch(Dispatchers.IO) {
        addToCartUseCase(product = product.apply { quantity = selectedQuantity })
        withContext(Dispatchers.Main) {
            _isCartUpdated.value = !isCartUpdated.value
        }
    }


    fun deleteCart(product: Product, selectedQuantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCartProductUseCase(product = product.apply { quantity = selectedQuantity })
            withContext(Dispatchers.Main) {
                _isCartUpdated.value = !isCartUpdated.value
            }
        }
    }
}