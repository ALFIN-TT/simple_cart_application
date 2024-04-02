package com.alfin.simplecartapp.data.network

import androidx.paging.PagingSource
import com.alfin.simplecartapp.data.network.pagination.ProductPagingSource
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.data.network.response.Products
import com.alfin.simplecartapp.domain.repository.ProductRepository
import com.alfin.simplecartapp.domain.repository.ProductRepositoryImplTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductPagingSourceTest {

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)
    private lateinit var productRepository: ProductRepository
    private lateinit var productPagingSource: ProductPagingSource
    private val testData = Products(
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
        limit = 10, skip = 0, total = 100
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        productRepository = ProductRepositoryImplTest()
        productPagingSource = ProductPagingSource(productRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenProductsAreGivenForNextPagesThenUsersPagingSourceReturnsSuccessAppendLoadResult() =
        testScope.runTest {
            (productRepository as ProductRepositoryImplTest).shouldReturnNetworkError = false
            val params = PagingSource
                .LoadParams
                .Append(
                    key = 10,
                    loadSize = 1,
                    placeholdersEnabled = false
                )

            val expected = PagingSource.LoadResult.Page(
                data = testData.products!!,
                prevKey = 0,
                nextKey = 20
            )

            // when
            val actual = productPagingSource.load(params = params)

            // then
            assertEquals(expected, actual)
        }

    @Test
    fun whenProductsAreGivenThenProductsPagingSourceReturnsSuccessLoadResult() =
        testScope.runTest {
            (productRepository as ProductRepositoryImplTest).shouldReturnNetworkError = false

            val params = PagingSource
                .LoadParams
                .Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )

            val expected = PagingSource
                .LoadResult
                .Page(
                    data = testData.products!!,
                    prevKey = null,
                    nextKey = 10
                )

            // when
            val actual = productPagingSource.load(params = params)

            // then
            assertEquals(expected, actual)
        }

    @Test
    fun whenProductsAreGivenThenProductsPagingSourceReturnsErrorLoadResult() =
        testScope.runTest {
            (productRepository as ProductRepositoryImplTest).shouldReturnNetworkError = true
            val params = PagingSource
                .LoadParams
                .Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )

            val expected = PagingSource
                .LoadResult
                .Error<Int, Product>(
                    throwable = RuntimeException("no data found")
                )::class.java

            // when
            val actual = productPagingSource.load(params = params)::class.java

            // then
            assertEquals(expected, actual)
        }

}