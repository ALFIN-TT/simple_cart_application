package com.alfin.productlistingapp.presentation.screen.cart.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alfin.productlistingapp.data.constants.Constants
import com.alfin.productlistingapp.data.constants.Keys
import com.alfin.productlistingapp.presentation.navigation.Screen
import com.alfin.productlistingapp.presentation.screen.cart.viewmodel.CartViewModel

@Composable
fun CartUI(
    navController: NavController = rememberNavController(),
    viewModel: CartViewModel
) {
    DrawRootView(
        navController = navController,
        viewModel = viewModel
    )
}

@Composable
private fun DrawRootView(
    navController: NavController,
    viewModel: CartViewModel
) {
    GetCart(viewModel = viewModel)
    Scaffold(
        bottomBar = {
            if (viewModel.cartState.value.cart.isNotEmpty()) {
                DrawCheckout(viewModel.cartState.value.totalAmount)
            }
        }
    ) { it ->
        Column(modifier = Modifier.padding(it)) {
            DrawScreenBody(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}


@Composable
private fun DrawScreenBody(
    navController: NavController = rememberNavController(),
    viewModel: CartViewModel
) {
    DrawCartHeader(
        onBackPress = { navController.navigateUp() },
        onClickHome = {
            navController.navigate(Screen.HomeUI.route) {
                popUpTo(Screen.HomeUI.route) { inclusive = true }
            }
        }
    )
    DrawCartContents(
        navController = navController,
        viewModel = viewModel
    )
}

@Composable
private fun GetCart(viewModel: CartViewModel) {
    LaunchedEffect(viewModel.isCartUpdated.value) {
        viewModel.fetchCart()
    }
}

@Composable
private fun DrawCartContents(navController: NavController, viewModel: CartViewModel) {
    if (viewModel.cartState.value.cart.isNotEmpty()) {
        DrawCartedProducts(
            products = viewModel.cartState.value.cart,
            onCartButtonClick = { product, quantity ->
                if (quantity > 0) {
                    viewModel.addToCart(product = product, selectedQuantity = quantity)
                } else {
                    viewModel.deleteCart(product = product, selectedQuantity = quantity)
                }
            },
            onProductClick = {
                navController.navigate(Screen.ProductDetailUI.route.plus("?${Keys.KEY_PRODUCT_ID}=${it.id ?: Constants.INVALID_ID}"))
            }
        )
    } else {
        DrawEmptyCart()
    }
}