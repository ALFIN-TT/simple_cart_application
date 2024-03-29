package com.alfin.productlistingapp.data.di


import android.content.Context
import com.alfin.productlistingapp.core.data.network.BaseApi
import com.alfin.productlistingapp.data.network.api.ProductApi
import com.alfin.productlistingapp.data.roomdb.AppDatabase
import com.alfin.productlistingapp.domain.repository.ProductRepository
import com.alfin.productlistingapp.domain.repository.ProductRepositoryImpl
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
import com.alfin.productlistingapp.domain.usecase.GetProductsUseCase
import com.alfin.productlistingapp.domain.usecase.GetProductsUseCaseImpl
import com.alfin.productlistingapp.domain.usecase.GetTotalAmountUseCase
import com.alfin.productlistingapp.domain.usecase.GetTotalAmountUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesProductApi(): ProductApi = BaseApi(ProductApi::class.java, ProductApi.BASE_URL)

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context = context)

    @Singleton
    @Provides
    fun providesProductRepository(
        productApi: ProductApi,
        appDatabase: AppDatabase
    ): ProductRepository {
        return ProductRepositoryImpl(
            productApi = productApi,
            appDatabase = appDatabase
        )
    }


    @Provides
    fun providesGetProductsUseCase(
        productRepository: ProductRepository
    ): GetProductsUseCase {
        return GetProductsUseCaseImpl(productRepository)
    }

    @Provides
    fun providesGetProductUseCase(
        productRepository: ProductRepository
    ): GetProductUseCase {
        return GetProductUseCaseImpl(productRepository)
    }

    @Provides
    fun providesAddToCartUseCase(
        productRepository: ProductRepository
    ): AddToCartUseCase {
        return AddToCartUseCaseImpl(productRepository)
    }

    @Provides
    fun providesGetCartUseCase(
        productRepository: ProductRepository
    ): GetCartUseCase {
        return GetCartUseCaseImpl(productRepository)
    }

    @Provides
    fun providesDeleteCartProductUseCase(
        productRepository: ProductRepository
    ): DeleteCartProductUseCase {
        return DeleteCartProductUseCaseImpl(productRepository)
    }

    @Provides
    fun providesGetTotalAmountUseCase(
        productRepository: ProductRepository
    ): GetTotalAmountUseCase {
        return GetTotalAmountUseCaseImpl(productRepository)
    }

    @Provides
    fun providesGetCartProductUseCase(
        productRepository: ProductRepository
    ): GetCartProductUseCase {
        return GetCartProductUseCaseImpl(productRepository)
    }

    @Provides
    fun providesGetCartCountUseCaseUseCase(
        productRepository: ProductRepository
    ): GetCartCountUseCase {
        return GetCartCountUseCaseImpl(productRepository)
    }
}