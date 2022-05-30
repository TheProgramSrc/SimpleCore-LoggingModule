package xyz.theprogramsrc.loggingmodule

import java.io.PrintWriter
import java.io.StringWriter

object Utils  {

    private val cache = mutableMapOf<String, String>()

    fun getStackTrace(e: Exception): String = cache.computeIfAbsent(e.toString()) {
        val sw = StringWriter()
        e.printStackTrace(PrintWriter(sw))
        sw.toString()
    }

}