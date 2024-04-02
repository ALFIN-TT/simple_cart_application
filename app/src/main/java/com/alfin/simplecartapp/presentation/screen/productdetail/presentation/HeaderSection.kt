package com.alfin.simplecartapp.presentation.screen.productdetail.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfin.simplecartapp.R
import com.alfin.simplecartapp.core.presentation.composable.DrawBadgedBox
import com.alfin.simplecartapp.data.network.response.Product
import com.alfin.simplecartapp.presentation.theme.RATING_COLOR
import com.alfin.simplecartapp.presentation.theme.montserratFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DrawProductDetailHeader(
    product: Product,
    cartCount: Int,
    onBackPress: () -> Unit,
    onClickCart: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 12.dp, end = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackPress,
        ) {
            Icon(
                painterResource(id = R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = null
            )
        }
        DrawBadgedBox(
            badgeCount = cartCount,
            painter = painterResource(id = R.drawable.ic_cart),
            onClick = onClickCart,
        )
    }
    Text(
        text = product.title ?: "",
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.SemiBold,
        fontFamily = montserratFamily,
        fontSize = 22.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .padding(top = 8.dp)
            .basicMarquee()
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.Star,
            contentDescription = null,
            tint = RATING_COLOR
        )
        Text(
            text = "${product.rating}".plus(stringResource(id = R.string.str_maximum_rating)),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Normal,
            fontFamily = montserratFamily,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}