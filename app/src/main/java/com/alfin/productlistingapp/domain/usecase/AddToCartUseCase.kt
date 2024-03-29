package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.data.network.response.Product

interface AddToCartUseCase {
    suspend operator fun invoke(product: Product):Long
}