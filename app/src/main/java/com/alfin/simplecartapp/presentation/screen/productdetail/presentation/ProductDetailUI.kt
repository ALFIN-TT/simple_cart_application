package com.alfin.simplecartapp.presentation.screen.productdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alfin.simplecartapp.core.data.state.State
import com.alfin.simplecartapp.core.presentation.composable.DrawProgressLoader
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.presentation.navigation.Screen
import com.alfin.simplecartapp.presentation.screen.productdetail.viewmodel.ProductDetailViewModel
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun ProductDetailUI(
    navController: NavController = rememberNavController(),
    viewModel: ProductDetailViewModel
) {
    DrawRootView(
        navController = navController,
        viewModel = viewModel
    )
}

@Composable
private fun DrawRootView(
    navController: NavController,
    viewModel: ProductDetailViewModel
) {

    GetProductDetails(viewModel = viewModel)
    Scaffold { it ->
        Column(modifier = Modifier.padding(it)) {
            DrawScreenBody(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}


@Composable
fun GetProductDetails(viewModel: ProductDetailViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getProduct()
        viewModel.getCartCount()
    }
}

@Composable
private fun DrawScreenBody(
    navController: NavController = rememberNavController(),
    viewModel: ProductDetailViewModel
) {

    when (viewModel.productDetailState.value.uiState) {
        State.LOADING -> {
            DrawProgressLoader()
        }

        State.SUCCESS -> {
            DrawContent(
                product = viewModel.productDetailState.value.data,
                viewModel = viewModel,
                navController = navController
            )
        }

        State.ERROR -> {
            DrawErrorContent(
                errorMessage = viewModel.productDetailState.value.errorMessage ?: "",
            ) {
                viewModel.getProduct()
            }
        }

        else -> {}
    }
}

@Composable
private fun DrawContent(
    product: Product,
    viewModel: ProductDetailViewModel,
    navController: NavController,
) {
    val cartCountState = viewModel.cartCountState.asStateFlow()
    DrawProductDetailHeader(
        product = product,
        cartCount = cartCountState.value,
        onBackPress = { navController.navigateUp() },
        onClickCart = {
            navController.navigate(Screen.CartUI.route)
        }
    )
    DrawProductDetailContent(product = product)
    DrawProductDetailBottom(product = product, viewModel = viewModel)
}