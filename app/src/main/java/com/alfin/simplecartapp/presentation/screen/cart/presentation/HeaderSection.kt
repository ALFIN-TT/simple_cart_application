package com.alfin.simplecartapp.presentation.screen.cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.alfin.simplecartapp.presentation.theme.montserratFamily


/**
 *  Draw header section of the cart ui (title, back button, home button).
 *  @param onBackPress callback for handle back button click
 *  @param onClickHome callback for handle home button click
 */
@Composable
fun DrawCartHeader(
    onBackPress: () -> Unit,
    onClickHome: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackPress,
        ) {
            Icon(
                painterResource(id = R.drawable.ic_back),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                contentDescription = null
            )
        }

        Text(
            text = stringResource(id = R.string.str_cart),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontFamily = montserratFamily,
            fontSize = 22.sp,
        )
        IconButton(
            onClick = onClickHome,
        ) {
            Icon(
                painterResource(id = R.drawable.ic_home),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                contentDescription = null
            )
        }
    }
}