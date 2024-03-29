package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.data.network.response.Product

interface DeleteCartProductUseCase {
    suspend operator fun invoke(product: Product)
}