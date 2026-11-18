package com.iti.design_system.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iti.design_system.theme.CurrencyConvertorTheme

@Composable
fun MCBackgroundScreen(
    modifier: Modifier = Modifier,
    dismissKeyboard: Boolean = true,
    content: @Composable () -> Unit
) {
//    val focusManager = LocalFocusManager.current
//    val isKeyboardVisible by isKeyboardVisibleAsState()
//
//    LaunchedEffect(dismissKeyboard, isKeyboardVisible) {
//        if (dismissKeyboard && !isKeyboardVisible) {
//            focusManager.clearFocus()
//        }
//    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(dismissKeyboard) {
                if (dismissKeyboard) {
                    detectTapGestures {
//                        focusManager.clearFocus()
                    }
                }
            },
        color = MaterialTheme.colorScheme.background
    ) {
        val maxWidth = with(LocalDensity.current) {
            LocalConfiguration.current.screenWidthDp.dp.toPx()
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationY = -maxWidth
                    scaleX = 2f
                }
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0x7CC9C9FF),
                        Color(0xFFDDF6F3),
                        Color.White,
                    )
                )
            )
        }
        content()
    }
}

@Preview
@Composable
private fun MCBackgroundScreenPreview() {
    CurrencyConvertorTheme {
        MCBackgroundScreen {}
    }
}