package com.alfin.simplecartapp.presentation.screen.productdetail.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alfin.simplecartapp.core.presentation.composable.CartButton
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.presentation.screen.productdetail.viewmodel.ProductDetailViewModel
import com.alfin.simplecartapp.presentation.theme.montserratFamily

@Composable
fun DrawProductDetailBottom(
    product: Product,
    viewModel: ProductDetailViewModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp)
    ) {
        val (price, cartButton) = createRefs()
        Text(
            modifier = Modifier.constrainAs(price) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            text = "$${product.price}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = montserratFamily,
            textAlign = TextAlign.Start
        )
        CartButton(
            modifier = Modifier.constrainAs(cartButton) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            quantity = product.quantity
        ) { quantity ->
            if (quantity > 0) {
                viewModel.addToCart(product = product, selectedQuantity = quantity)
            } else {
                viewModel.deleteCart(product = product, selectedQuantity = quantity)
            }
        }
    }
}