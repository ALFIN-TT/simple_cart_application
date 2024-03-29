package com.alfin.simplecartapp.presentation.screen.productdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.alfin.simplecartapp.MainCoroutineRule
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.repository.ProductRepository
import com.alfin.simplecartapp.domain.usecase.AddToCartUseCase
import com.alfin.simplecartapp.domain.usecase.AddToCartUseCaseImpl
import com.alfin.simplecartapp.domain.usecase.DeleteCartProductUseCase
import com.alfin.simplecartapp.domain.usecase.DeleteCartProductUseCaseImpl
import com.alfin.simplecartapp.domain.usecase.GetCartCountUseCase
import com.alfin.simplecartapp.domain.usecase.GetCartCountUseCaseImpl
import com.alfin.simplecartapp.domain.usecase.GetCartProductUseCase
import com.alfin.simplecartapp.domain.usecase.GetCartProductUseCaseImpl
import com.alfin.simplecartapp.domain.usecase.GetProductUseCase
import com.alfin.simplecartapp.domain.usecase.GetProductUseCaseImpl
import com.alfin.simplecartapp.domain.usecase.GetTotalAmountUseCase
import com.alfin.simplecartapp.domain.usecase.GetTotalAmountUseCaseImpl
import com.alfin.simplecartapp.presentation.screen.productdetail.viewmodel.ProductDetailViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
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
class ProductDetailViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(this.testDispatcher)
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductUseCase: GetProductUseCase
    private lateinit var getTotalAmountUseCase: GetTotalAmountUseCase
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var deleteCartProductUseCase: DeleteCartProductUseCase
    private lateinit var getCartProductUseCase: GetCartProductUseCase
    private lateinit var getCartCountUseCase: GetCartCountUseCase

    private lateinit var productDetailViewModel: ProductDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)

        productRepository = Mockito.mock(ProductRepository::class.java)
        addToCartUseCase = AddToCartUseCaseImpl(productRepository)
        getProductUseCase = GetProductUseCaseImpl(productRepository)
        getTotalAmountUseCase = GetTotalAmountUseCaseImpl(productRepository)
        deleteCartProductUseCase = DeleteCartProductUseCaseImpl(productRepository)
        getCartCountUseCase = GetCartCountUseCaseImpl(productRepository)
        getCartProductUseCase = GetCartProductUseCaseImpl(productRepository)

        productDetailViewModel = ProductDetailViewModel(
            SavedStateHandle(),
            getProductUseCase,
            getCartProductUseCase,
            addToCartUseCase,
            deleteCartProductUseCase,
            getCartCountUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun testDeleteCart() = testScope.runBlockingTest {
        productDetailViewModel.deleteCart(
            Product(id = 2, brand = "Apple", quantity = 2),
            selectedQuantity = 0
        )
        testScheduler.apply { advanceTimeBy(0);runCurrent() }
        Truth.assertThat(productDetailViewModel.productDetailState.value.data.quantity).isEqualTo(0)
    }

    @Test
    fun testAddToCart() = testScope.runBlockingTest {
        Mockito.`when`(addToCartUseCase(Product(id = 2, brand = "Apple", quantity = 2)))
            .thenReturn(4)
        productDetailViewModel.addToCart(
            Product(id = 2, brand = "Apple", quantity = 2),
            selectedQuantity = 4
        )
        testScheduler.apply { advanceTimeBy(0);runCurrent() }
        Truth.assertThat(productDetailViewModel.productDetailState.value.data.quantity).isEqualTo(4)
    }

    @Test
    fun getCartCount() = testScope.runBlockingTest {
        Mockito.`when`(getCartCountUseCase()).thenReturn(10)
        productDetailViewModel.getCartCount()
        testScheduler.apply { advanceTimeBy(0);runCurrent() }
        val cartCount = productDetailViewModel.cartCountState.first()
        Truth.assertThat(cartCount).isEqualTo(10)
    }
}













