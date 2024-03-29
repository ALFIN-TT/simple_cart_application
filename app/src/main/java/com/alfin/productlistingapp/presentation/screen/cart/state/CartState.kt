package com.alfin.productlistingapp.presentation.screen.cart.state

import com.alfin.productlistingapp.data.network.response.Product

data class CartState(
    val cart: List<Product> = emptyList(),
    val totalAmount: Int = 0
)



