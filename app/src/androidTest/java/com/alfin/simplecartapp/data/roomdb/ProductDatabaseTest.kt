package com.alfin.simplecartapp.data.roomdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.alfin.simplecartapp.data.network.response.Product
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
class ProductDatabaseTest {

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    private lateinit var appDatabase: AppDatabase
    private lateinit var productDao: ProductDao

    @Before
    fun setUpDataBase() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        productDao = appDatabase.productDao()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        appDatabase.close()
    }

    @Test
    fun testInsertProductOperationIsSuccess() = testScope.runTest {
        val testData =
            Product(id = 1, title = "iPhone x", quantity = 1, price = 1000, thumbnail = "")
        productDao.addToCart(product = testData)
        val product = productDao.getCartProduct(testData.id ?: 1).first()
        Truth.assertThat(product).isEqualTo(testData)
    }


    @Test
    fun testGetCartedProductsOperationIsSuccess() = testScope.runTest {
        val testData1 =
            Product(id = 2, title = "iPhone x", quantity = 1, price = 500, thumbnail = "")
        val testData2 = Product(
            id = 3, title = "iPhone 14", quantity = 2, price = 800, thumbnail = ""
        )
        productDao.addToCart(product = testData1)
        productDao.addToCart(product = testData2)
        val products = productDao.getCartProducts().first()
        Truth.assertThat(products).contains(testData1)
    }


    @Test
    fun testTotalAmountOperationIsSuccess() = testScope.runTest {
        val testData1 =
            Product(id = 1, title = "iPhone x", quantity = 1, price = 500, thumbnail = "")
        val testData2 = Product(
            id = 2, title = "iPhone 14", quantity = 2, price = 800, thumbnail = ""
        )
        productDao.addToCart(product = testData1)
        productDao.addToCart(product = testData2)
        val totalAmount = productDao.getTotalAmount().first()
        Truth.assertThat(totalAmount).isEqualTo(2100)
    }

    @Test
    fun testDeleteCartedProductOperationIsSuccess() = testScope.runTest {
        val testData1 =
            Product(id = 2, title = "iPhone x", quantity = 1, price = 500, thumbnail = "")
        val testData2 = Product(
            id = 3, title = "iPhone 14", quantity = 2, price = 800, thumbnail = ""
        )
        productDao.addToCart(product = testData1)
        productDao.addToCart(product = testData2)
        productDao.deleteCartProduct(testData2)
        val products = productDao.getCartProducts().first()
        Truth.assertThat(products).doesNotContain(testData2)
    }
}