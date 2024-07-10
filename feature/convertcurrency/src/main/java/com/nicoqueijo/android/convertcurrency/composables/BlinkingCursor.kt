package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicoqueijo.android.ui.XXXXS

/**
 * Composable function for a Blinking Cursor.
 *
 * This function creates a blinking cursor effect using an infinite transition that
 * alternates the cursor's alpha value between 1 and 0, creating a blinking effect.
 *
 * @param modifier The modifier to be applied to the cursor's Box layout.
 */
@Composable
fun BlinkingCursor(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "",
    )

    Box(
        modifier = modifier
            .size(
                width = XXXXS,
                height = 20.dp
            )
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
            )
    )
}