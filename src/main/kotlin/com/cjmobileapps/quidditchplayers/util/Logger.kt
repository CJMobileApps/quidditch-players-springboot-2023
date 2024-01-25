package com.cjmobileapps.quidditchplayers.util

import java.io.PrintWriter
import java.io.StringWriter

object Logger {

    private const val ANSI_RED = "\u001B[31m"
    private const val ANSI_BLACK = "\u001B[30m"
    private const val ANSI_WHITE = "\u001B[38m"

    fun debug(title: String = "", message: String) {
        var debugTitle = title

        if(debugTitle.isNotBlank()) {
            debugTitle = "$debugTitle, "
        }

        println(ANSI_WHITE + debugTitle + message)
    }

    fun error(title: String, message: String) {
        var errorTitle = title

        if(errorTitle.isNotBlank()) {
            errorTitle = "$errorTitle, "
        }

        println(ANSI_RED + errorTitle + message)
    }

    fun errorStackTrace(title: String, e: Exception) {
        var errorTitle = title

        if(errorTitle.isNotBlank()) {
            errorTitle = "$errorTitle, "
        }

        val stringWriter = StringWriter()
        e.printStackTrace(PrintWriter(stringWriter))
        val exceptionAsString = stringWriter.toString()
        println(ANSI_RED + errorTitle + exceptionAsString)
    }
}
