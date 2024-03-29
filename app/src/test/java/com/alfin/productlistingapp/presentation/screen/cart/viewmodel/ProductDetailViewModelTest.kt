package com.alfin.productlistingapp.presentation.screen.cart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.alfin.productlistingapp.MainCoroutineRule
import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.repository.ProductRepository
import com.alfin.productlistingapp.domain.usecase.AddToCartUseCase
import com.alfin.productlistingapp.domain.usecase.AddToCartUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.DeleteCartProductUseCase
import com.alfin.productlistingapp.domain.usecase.DeleteCartProductUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetCartCountUseCase
import com.alfin.productlistingapp.domain.usecase.GetCartCountUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetCartProductUseCase
import com.alfin.productlistingapp.domain.usecase.GetCartProductUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetCartUseCase
import com.alfin.productlistingapp.domain.usecase.GetCartUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetProductUseCase
import com.alfin.productlistingapp.domain.usecase.GetProductUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetTotalAmountUseCase
import com.alfin.productlistingapp.domain.usecase.GetTotalAmountUseCaseImpl
import com.alfin.productlistingapp.presentation.screen.productdetail.viewmodel.ProductDetailViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
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
      fun testAddToCart() = testScope.runBlockingTest {
          productDetailViewModel.addToCart(Product(id = 2, brand = "Apple", quantity = 2), selectedQuantity = 4)
         // advanceUntilIdle()
          delay(1000)
          Truth.assertThat(productDetailViewModel.productDetailState.value.data.id).isEqualTo(2)

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













