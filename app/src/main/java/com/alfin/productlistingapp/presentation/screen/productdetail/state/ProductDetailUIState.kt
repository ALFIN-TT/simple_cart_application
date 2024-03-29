package com.alfin.productlistingapp.presentation.screen.productdetail.state

import com.alfin.productlistingapp.core.data.state.BaseData
import com.alfin.productlistingapp.core.presentation.state.BaseState
import com.alfin.productlistingapp.data.network.response.Product


class ProductDetailUIState(
    var data: Product = Product(),
    var isLoadCartCount: Boolean = true
) : BaseState()


class GetProductResponseData(
    var data: Product = Product()
) : BaseData()

