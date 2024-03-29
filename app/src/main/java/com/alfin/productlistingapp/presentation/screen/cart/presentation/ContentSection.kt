package com.alfin.productlistingapp.presentation.screen.cart.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.alfin.productlistingapp.R
import com.alfin.productlistingapp.core.presentation.composable.CartButton
import com.alfin.productlistingapp.data.network.response.Product
import com.alfin.productlistingapp.presentation.theme.montserratFamily

@Composable
fun DrawCartedProducts(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onCartButtonClick: (Product, Int) -> Unit,
) {
    LazyColumn {
        items(products) { product ->
            DrawCartedProductCard(
                product = product,
                onProductClick = onProductClick,
                onCartButtonClick = onCartButtonClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DrawCartedProductCard(
    product: Product,
    onProductClick: (Product) -> Unit,
    onCartButtonClick: (Product, Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        val painter =
            rememberAsyncImagePainter(product.thumbnail)
        val transition by animateFloatAsState(
            targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
            label = ""
        )
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (productImage, name, price, cartButton) = createRefs()
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(productImage) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)

                    }
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .alpha(transition)
            )

            Text(
                text = product.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = montserratFamily,
                modifier = Modifier
                    .constrainAs(name) {
                        start.linkTo(productImage.end, 8.dp)
                        top.linkTo(parent.top, 14.dp)
                        end.linkTo(parent.end,8.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = montserratFamily,
                fontSize = 18.sp,
                modifier = Modifier
                    .constrainAs(price) {
                        start.linkTo(productImage.end, 8.dp)
                        top.linkTo(name.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                    .basicMarquee()
            )
            CartButton(modifier = Modifier.constrainAs(cartButton) {
                top.linkTo(name.bottom)
                end.linkTo(parent.end, 12.dp)
                bottom.linkTo(parent.bottom, 8.dp)
            }, quantity = product.quantity) {
                onCartButtonClick(product, it)
            }
        }
    }
}

@Composable
fun DrawEmptyCart() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.str_no_items_in_cart),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = montserratFamily,
            modifier = Modifier.padding(horizontal = 22.dp)
        )
    }
}