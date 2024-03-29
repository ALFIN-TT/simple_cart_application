@file:Suppress("DEPRECATION")

package com.alfin.productlistingapp.presentation.screen.cart.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.domain.repository.ProductRepository
import com.alfin.productlistingapp.domain.repository.ProductRepositoryImpl
import com.alfin.productlistingapp.domain.usecase.GetCartCountUseCase
import com.alfin.productlistingapp.domain.usecase.GetCartCountUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetProductsUseCase
import com.alfin.productlistingapp.domain.usecase.GetProductsUseCaseImpl
import com.alfin.productlistingapp.presentation.screen.home.viewmodel.HomeViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
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
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var cartCountUseCase: GetCartCountUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        productRepository = Mockito.mock(ProductRepositoryImpl::class.java)
        getProductsUseCase = GetProductsUseCaseImpl(productRepository)
        cartCountUseCase = GetCartCountUseCaseImpl(productRepository)
        homeViewModel = HomeViewModel(getProductsUseCase, cartCountUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun testGetProducts() = testScope.runBlockingTest {
        val testData = PagingData.from(
            listOf(
                Product(brand = "Apple"),
                Product(brand = "Oppo"),
            )
        )
        Mockito.`when`(getProductsUseCase()).thenReturn(flowOf(testData))
        val data = homeViewModel.productsState.first()
        Truth.assertThat(testData).isEqualTo(data)
    }
}