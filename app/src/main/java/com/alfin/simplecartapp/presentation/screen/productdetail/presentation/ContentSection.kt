package com.alfin.simplecartapp.presentation.screen.productdetail.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.alfin.simplecartapp.core.presentation.composable.DrawErrorMessage
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.presentation.theme.montserratFamily

@Composable
fun DrawProductDetailContent(
    product: Product
) {
    val painter =
        rememberAsyncImagePainter(product.thumbnail)
    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
        label = ""
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
    ) {
        Box(
            modifier = Modifier
                .height(320.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .alpha(transition)
            )

        }
        Text(
            text = product.title ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            fontFamily = montserratFamily,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            text = product.description ?: "",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Normal,
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            text = product.brand ?: "",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium,
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun DrawErrorContent(errorMessage: String, onClickRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        DrawErrorMessage(
            message = errorMessage, onClickRetry = onClickRetry
        )
    }
}