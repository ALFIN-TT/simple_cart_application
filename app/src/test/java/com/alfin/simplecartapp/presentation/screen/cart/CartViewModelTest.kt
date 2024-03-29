package com.alfin.simplecartapp.presentation.screen.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alfin.simplecartapp.MainCoroutineRule
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.repository.ProductRepository
import com.alfin.simplecartapp.domain.usecase.AddToCartUseCase
import com.alfin.simplecartapp.domain.usecase.AddToCartUseCaseImpl
import com.alfin.simplecartapp.domain.usecase.DeleteCartProductUseCase
import com.alfin.simplecartapp.domain.usecase.DeleteCartProductUseCaseImpl
import com.alfin.simplecartapp.domain.usecase.GetCartUseCase
import com.alfin.simplecartapp.domain.usecase.GetCartUseCaseImpl
import com.alfin.simplecartapp.domain.usecase.GetTotalAmountUseCase
import com.alfin.simplecartapp.domain.usecase.GetTotalAmountUseCaseImpl
import com.alfin.simplecartapp.presentation.screen.cart.viewmodel.CartViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        testScheduler.apply { advanceTimeBy(0);runCurrent() }
        Truth.assertThat(!previousState).isEqualTo(cartViewModel.isCartUpdated.value)
    }
}













