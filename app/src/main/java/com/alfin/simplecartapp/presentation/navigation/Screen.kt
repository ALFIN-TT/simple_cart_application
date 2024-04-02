package com.alfin.simplecartapp.presentation.navigation

sealed class Screen(val route: String) {
    object HomeUI : Screen("home_ui")
    object ProductDetailUI : Screen("product_detail_ui")
    object CartUI : Screen("cart_ui")
}
