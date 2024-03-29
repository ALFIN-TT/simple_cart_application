package com.alfin.simplecartapp.domain.usecase

import com.alfin.simplecartapp.core.data.network.Resource
import com.alfin.simplecartapp.presentation.screen.productdetail.state.GetProductResponseData
import kotlinx.coroutines.flow.Flow

interface GetProductUseCase {
     suspend operator fun invoke(productId: Int): Flow<Resource<GetProductResponseData>>
}