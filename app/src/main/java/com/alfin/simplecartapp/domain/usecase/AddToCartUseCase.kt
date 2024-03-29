package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.data.network.response.Product

interface AddToCartUseCase {
    suspend operator fun invoke(product: Product):Long
}