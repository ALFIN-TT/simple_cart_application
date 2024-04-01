@file:Suppress("DEPRECATION")

package com.alfin.simplecartapp.presentation.screen.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.domain.usecase.GetCartCountUseCase
import com.alfin.simplecartapp.domain.usecase.GetProductsUseCase
import com.alfin.simplecartapp.presentation.screen.home.viewmodel.HomeViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
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
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Mock
    private lateinit var cartCountUseCase: GetCartCountUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
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
        homeViewModel.productsState.value = testData
        val data = homeViewModel.productsState.first()
        Truth.assertThat(testData).isEqualTo(data)
    }

    @Test
    fun testGetCartCount() = runBlocking {
        Mockito.`when`(cartCountUseCase.invoke()).thenReturn(1)
        homeViewModel.getCartCount()
        delay(100)
        //testScheduler.apply { advanceTimeBy(0); runCurrent() }
        Truth.assertThat(homeViewModel.cartCountState.value).isEqualTo(1)
    }
}
