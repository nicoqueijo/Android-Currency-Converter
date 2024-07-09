package com.nicoqueijo.android.ui.extensions

import android.content.Context

/**
 * Retrieves a string resource using a String instead of an Int.
 *
 * @param name The name of the string resource.
 * @return The string value of the specified resource.
 * Credit: https://stackoverflow.com/a/11595723/5906793
 */
fun Context.getStringResourceByName(name: String): String {
    val resId = resources.getIdentifier(name, "string", packageName)
    return getString(resId)
}

/**
 * Retrieves a drawable resource using a String instead of an Int.
 *
 * @param name The name of the drawable resource.
 * @return The resource ID of the specified drawable.
 * Credit: https://stackoverflow.com/a/11595723/5906793
 */
fun Context.getDrawableResourceByName(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}