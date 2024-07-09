package com.nicoqueijo.android.ui.extensions

import android.content.Context

/**
 * TODO: add name parameter
 * Retrieves string resources using a String instead of an int.
 * Credit: https://stackoverflow.com/a/11595723/5906793
 */
fun Context.getStringResourceByName(name: String): String {
    val resId = resources.getIdentifier(name, "string", packageName)
    return getString(resId)
}

/**
 * TODO: add name parameter
 * Retrieves drawable resources using a String instead of an int.
 * Credit: https://stackoverflow.com/a/11595723/5906793
 */
fun Context.getDrawableResourceByName(name: String): Int {
    return resources.getIdentifier(name, "drawable", packageName)
}