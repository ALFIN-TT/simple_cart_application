package com.alfin.productlistingapp.domain.usecase

import com.alfin.productlistingapp.core.data.network.Resource
import com.alfin.productlistingapp.presentation.screen.productdetail.state.GetProductResponseData
import kotlinx.coroutines.flow.Flow

interface GetProductUseCase {
     suspend operator fun invoke(productId: Int): Flow<Resource<GetProductResponseData>>
}