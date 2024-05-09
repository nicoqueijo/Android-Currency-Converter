package com.nicoqueijo.android.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * An annotation for creating composable previews in both light and dark mode.
 *
 * This annotation can be used on a composable function to generate previews for:
 *  * Light mode (using Configuration.UI_MODE_NIGHT_NO)
 *  * Dark mode (using Configuration.UI_MODE_NIGHT_YES)
 *
 * Both previews will target a Pixel 7 Pro device and use API level 33 (or higher if available).
 */
@Preview(
    name = "Light Mode",
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_7_PRO
)
@Preview(
    name = "Dark Mode",
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_7_PRO
)
annotation class DarkLightPreviews
