package com.alfin.productlistingapp.presentation.screen.cart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alfin.productlistingapp.MainCoroutineRule
import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.repository.ProductRepository
import com.alfin.productlistingapp.domain.usecase.AddToCartUseCase
import com.alfin.productlistingapp.domain.usecase.AddToCartUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.DeleteCartProductUseCase
import com.alfin.productlistingapp.domain.usecase.DeleteCartProductUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetCartUseCase
import com.alfin.productlistingapp.domain.usecase.GetCartUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetTotalAmountUseCase
import com.alfin.productlistingapp.domain.usecase.GetTotalAmountUseCaseImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class CartViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)
    private lateinit var productRepository: ProductRepository
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var getTotalAmountUseCase: GetTotalAmountUseCase
    private lateinit var getCartUseCase: GetCartUseCase
    private lateinit var deleteCartProductUseCase: DeleteCartProductUseCase

    private lateinit var cartViewModel: CartViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)

        productRepository = Mockito.mock(ProductRepository::class.java)

        addToCartUseCase = AddToCartUseCaseImpl(productRepository)
        getCartUseCase = GetCartUseCaseImpl(productRepository)
        getTotalAmountUseCase = GetTotalAmountUseCaseImpl(productRepository)
        deleteCartProductUseCase = DeleteCartProductUseCaseImpl(productRepository)

        cartViewModel =
            CartViewModel(
                getCartUseCase,
                getTotalAmountUseCase,
                addToCartUseCase,
                deleteCartProductUseCase
            )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun testAddToCart() = testScope.runBlockingTest {
        val previousState = cartViewModel.isCartUpdated.value
        cartViewModel.addToCart(Product(brand = "Apple", quantity = 2), selectedQuantity = 4)
        advanceUntilIdle()
        Truth.assertThat(!previousState).isEqualTo(cartViewModel.isCartUpdated.value)

    }

    @Test
    fun testDeleteCart() = testScope.runBlockingTest {
        val previousState = cartViewModel.isCartUpdated.value
        cartViewModel.deleteCart(Product(brand = "Apple", quantity = 2), selectedQuantity = 4)
        advanceUntilIdle()
        Truth.assertThat(!previousState).isEqualTo(cartViewModel.isCartUpdated.value)
    }
}













