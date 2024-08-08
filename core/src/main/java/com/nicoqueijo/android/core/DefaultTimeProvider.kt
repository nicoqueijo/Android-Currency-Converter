package com.nicoqueijo.android.core

import com.nicoqueijo.android.core.extensions.toSeconds

class DefaultTimeProvider : TimeProvider {
    override fun currentTimeSeconds(): Long = System.currentTimeMillis().toSeconds()
}