package com.alfin.simplecartapp.presentation.screen.productdetail.state

import com.alfin.simplecartapp.core.data.state.BaseData
import com.alfin.simplecartapp.core.presentation.state.BaseState
import com.alfin.simplecartapp.data.network.response.Product


class ProductDetailUIState(
    var data: Product = Product(),
    var isLoadCartCount: Boolean = true
) : BaseState()


class GetProductResponseData(
    var data: Product = Product()
) : BaseData()

