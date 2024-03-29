package com.alfin.simplecartapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alfin.simplecartapp.data.constants.Keys
import com.alfin.simplecartapp.presentation.screen.cart.presentation.CartUI
import com.alfin.simplecartapp.presentation.screen.cart.viewmodel.CartViewModel
import com.alfin.simplecartapp.presentation.screen.home.presentation.HomeUI
import com.alfin.simplecartapp.presentation.screen.home.viewmodel.HomeViewModel
import com.alfin.simplecartapp.presentation.screen.productdetail.presentation.ProductDetailUI
import com.alfin.simplecartapp.presentation.screen.productdetail.viewmodel.ProductDetailViewModel

@Composable
fun ScreenNavigation(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.HomeUI.route) {
        composable(route = Screen.HomeUI.route) {
            NavigateToHomeUI(navController = navHostController)
        }
        composable(
            route = Screen.ProductDetailUI.route.plus("?${Keys.KEY_PRODUCT_ID}={${Keys.VALUE_PRODUCT_ID}}"),
            arguments = listOf(
                navArgument(Keys.VALUE_PRODUCT_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            NavigateToProductDetailUI(navController = navHostController)
        }
        composable(route = Screen.CartUI.route) {
            NavigateToCartUI(navController = navHostController)
        }
    }
}

@Composable
fun NavigateToHomeUI(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    HomeUI(navController = navController, homeViewModel = viewModel)
}

@Composable
fun NavigateToProductDetailUI(navController: NavController) {
    val viewModel = hiltViewModel<ProductDetailViewModel>()
    ProductDetailUI(navController = navController, viewModel = viewModel)
}

@Composable
fun NavigateToCartUI(navController: NavController) {
    val viewModel = hiltViewModel<CartViewModel>()
    CartUI(navController = navController, viewModel = viewModel)
}