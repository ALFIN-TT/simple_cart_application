package com.alfin.simplecartapp.presentation.screen.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alfin.simplecartapp.R
import com.alfin.simplecartapp.core.presentation.composable.DrawBadgedBox
import com.alfin.simplecartapp.presentation.theme.montserratFamily

@Composable
fun DrawHomeHeader(
    cartCount : Int,
    onClickCart: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 12.dp, end = 12.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(id = R.string.str_dashboard),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontFamily = montserratFamily,
            fontSize = 22.sp,
            modifier = Modifier.padding(start = 12.dp),
        )
        DrawBadgedBox(
            badgeCount = cartCount,
            painter = painterResource(id = R.drawable.ic_cart),
            onClick = onClickCart,
        )
    }
}