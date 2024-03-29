package com.alfin.productlistingapp.presentation.screen.productdetail

import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.repository.ProductRepository
import com.alfin.productlistingapp.domain.repository.ProductRepositoryImplTest
import com.alfin.productlistingapp.domain.usecase.AddToCartUseCase
import com.alfin.productlistingapp.domain.usecase.AddToCartUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.DeleteCartProductUseCase
import com.alfin.productlistingapp.domain.usecase.DeleteCartProductUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetCartProductUseCase
import com.alfin.productlistingapp.domain.usecase.GetCartProductUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetProductUseCase
import com.alfin.productlistingapp.domain.usecase.GetProductUseCaseImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductUseCase: GetProductUseCase
    private lateinit var getCartProductUseCase: GetCartProductUseCase
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var deleteCartProductUseCase: DeleteCartProductUseCase


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        productRepository = ProductRepositoryImplTest()
        getProductUseCase = GetProductUseCaseImpl(productRepository)
        getCartProductUseCase = GetCartProductUseCaseImpl(productRepository)
        addToCartUseCase = AddToCartUseCaseImpl(productRepository)
        deleteCartProductUseCase = DeleteCartProductUseCaseImpl(productRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getProduct() = testScope.runTest {
        (productRepository as ProductRepositoryImplTest).shouldReturnNetworkError= false

        val testData =
            Product(id = 1, title = "iPhone x", quantity = 1, price = 1000, thumbnail = "")
        addToCartUseCase(testData)
        addToCartUseCase(product = testData)
        val productFromCart = getCartProductUseCase(id = testData.id ?: 1).first()
        val productFromAPI = getProductUseCase(productId = testData.id ?: 1).first()
        Truth.assertThat(productFromCart!!.id).isEqualTo(productFromAPI.data!!.data.id)
    }
}