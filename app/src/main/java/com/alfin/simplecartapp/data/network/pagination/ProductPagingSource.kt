package com.alfin.simplecartapp.data.network.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.repository.ProductRepository
import retrofit2.HttpException
import java.io.IOException

const val PRODUCT_PAGE_SIZE = 10

class ProductPagingSource(
    private val repository: ProductRepository
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: 0
            val product = repository.getProducts(
                limit = PRODUCT_PAGE_SIZE,
                page = currentPage
            )
            LoadResult.Page(
                data = product.body()?.products ?: emptyList(),
                prevKey = if (currentPage == 0) null else currentPage - PRODUCT_PAGE_SIZE,
                nextKey = if (product.body()?.products?.isEmpty() == true) null else currentPage + PRODUCT_PAGE_SIZE
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }
}
