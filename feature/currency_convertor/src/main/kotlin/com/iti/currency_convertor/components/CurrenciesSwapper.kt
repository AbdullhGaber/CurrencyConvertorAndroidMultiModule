package com.iti.currency_convertor

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.design_system.theme.CurrencyConvertorTheme
import com.iti.design_system.R
import kotlinx.coroutines.launch

@Composable
fun CurrenciesSwapper(
    modifier: Modifier = Modifier,
    onSwap: () -> Unit
) {
    val animatable = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        HorizontalDivider()
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    if(animatable.isRunning) return@clickable

                    scope.launch {
                        onSwap()
                        animatable.animateTo(
                            animatable.value + 180f,
                            tween(300)
                        )
                    }
                }.testTag("swap"),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_trade),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CurrencyConvertorScreenPreview() {
    CurrencyConvertorTheme {
        CurrencyConvertorScreen(
            uiState = CurrencyConvertorUiState.PreviewData,
            onFromCurrencyChange = {},
            onToCurrencyChange = {},
            onSwap = {}
        )
    }
}