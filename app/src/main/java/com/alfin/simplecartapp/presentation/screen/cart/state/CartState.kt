package com.alfin.simplecartapp.presentation.screen.cart.state

import com.alfin.simplecartapp.data.network.response.Product

/**
 * UI state for [com.alfin.simplecartapp.presentation.screen.cart.presentation.CartUI]
 * @param cart list of carted items.
 * @param totalAmount
 */
data class CartState(
    val cart: List<Product> = emptyList(),
    val totalAmount: Int = 0
)



