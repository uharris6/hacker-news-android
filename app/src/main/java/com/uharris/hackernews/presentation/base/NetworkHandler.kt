package com.uharris.hackernews.presentation.base

import android.content.Context
import com.uharris.hackernews.utils.networkInfo
import javax.inject.Inject

class NetworkHandler @Inject constructor(private val context: Context) {
    open val isConnected get() = context.networkInfo?.isConnected
}