package org.ash.test.uf.util

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class Logger {

    companion object {

        @SuppressLint("ConstantLocale")
        private val DATE_FORMATTER = SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault())

        fun L(any: Any?) {
            var message = any?.toString() ?: "NULL"
            val timestamp = DATE_FORMATTER.format(Date())
            message = "[$timestamp] ---> ${composeStackInfo()} \n $message"
            Log.d("@#@", message)
        }

        private fun composeStackInfo(): String {
            val stackTrace = Thread.currentThread().stackTrace[4]
            val fileName = stackTrace.fileName
            val methodName = stackTrace.methodName
            val line = stackTrace.lineNumber
            return "$fileName -> $methodName:$line"
        }
    }
}