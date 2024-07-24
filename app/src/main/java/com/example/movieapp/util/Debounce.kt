package com.example.movieapp.util

import android.os.Handler
import android.os.Looper

class Debounce(private val delayMillis: Long) {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    fun debounce(action: () -> Unit) {
        runnable?.let { handler.removeCallbacks(it) }

        runnable = Runnable {
            action.invoke()
        }

        handler.postDelayed(runnable!!, delayMillis)
    }

    fun cancel() {
        runnable?.let { handler.removeCallbacks(it) }
    }
}
