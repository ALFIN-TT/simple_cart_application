package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.data.network.response.Product

interface DeleteCartProductUseCase {
    suspend operator fun invoke(product: Product)
}