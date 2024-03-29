package com.alfin.simplecartapp.domain.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alfin.simplecartapp.core.data.network.Resource
import com.alfin.simplecartapp.core.data.state.State
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.data.network.response.Products
import com.alfin.simplecartapp.presentation.screen.productdetail.state.GetProductResponseData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import retrofit2.Response


class ProductRepositoryImplTest : ProductRepository {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    var shouldReturnNetworkError = false
    override suspend fun getProducts(limit: Int, page: Int): Response<Products> {
        return if (shouldReturnNetworkError) {
            /*val errorCode = 404
            val errorBody = "Not Found"
            Response.error( errCode, errorBody.toResponseBody("application/json".toMediaType()))*/
            throw Exception("Not Found")
        } else {
            val products = Products(
                products = listOf(
                    Product(
                        price = 899,
                        stock = 5,
                        title = "iPhone X",
                        brand = "Apple"
                    ),
                    Product(
                        price = 900,
                        stock = 5,
                        title = "iPhone 14",
                        brand = "Apple"
                    )
                ),
                limit = 0, skip = 2, total = 100
            )
            Response.success(products)
        }
    }

    override suspend fun getProduct(productId: Int): Flow<Resource<GetProductResponseData>> =
        flow {
            if (!shouldReturnNetworkError) {
                val product = Product(
                    price = 899,
                    stock = 5,
                    title = "iPhone X",
                    brand = "Apple"
                )
                val responseData =
                    GetProductResponseData(product).apply { dataState = State.INITIAL }
                emit(
                    Resource.Success(data = responseData)
                )
            } else {
                val productResponseData = GetProductResponseData()
                productResponseData.dataState = State.ERROR
                productResponseData.error = "Unable to fetch product"
                emit(Resource.Error(data = productResponseData))
            }
        }

    override suspend fun addToCart(product: Product): Long {
        TODO()
    }

    override suspend fun getCart(): Flow<List<Product>> {
        TODO()
    }

    override suspend fun getCartCount(): Int {
        TODO()
    }

    override suspend fun getTotalAmount(): Flow<Int> {
        TODO()
    }

    override suspend fun getCartProduct(id: Int): Flow<Product> {
        TODO()
    }

    override suspend fun deleteCartProduct(product: Product) {
        TODO()
    }
}