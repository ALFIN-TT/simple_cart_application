package com.alfin.simplecartapp.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alfin.simplecartapp.data.network.pagination.PRODUCT_PAGE_SIZE
import com.alfin.simplecartapp.data.network.pagination.ProductPagingSource
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : GetProductsUseCase {
    override suspend fun invoke(): Flow<PagingData<Product>> =
        Pager(
            config = PagingConfig(pageSize = PRODUCT_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                ProductPagingSource(repository)
            }
        ).flow
}