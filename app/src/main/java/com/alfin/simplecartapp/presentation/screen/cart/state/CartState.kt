package com.alfin.simplecartapp.presentation.screen.cart.state

import com.alfin.simplecartapp.data.network.response.Product

data class CartState(
    val cart: List<Product> = emptyList(),
    val totalAmount: Int = 0
)



