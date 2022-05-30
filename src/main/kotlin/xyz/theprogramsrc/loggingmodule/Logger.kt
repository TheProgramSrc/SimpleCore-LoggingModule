package xyz.theprogramsrc.loggingmodule

import xyz.theprogramsrc.simplecoreapi.libs.zip4j.ZipFile
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.Level

/**
 * Represents a logger for the logging module.
 * @param name The name of the logger
 *
 * How's this different from other loggers?
 * This logger will store the data in isolated folders
 */
class Logger(name: String) {

    private val javaLogger = java.util.logging.Logger.getLogger(name)
    private val logFile: File

    init {
        javaLogger.level = Level.ALL
        val logsFolder = File("logs/$name/")
        if(!logsFolder.exists()) logsFolder.mkdirs()
        logFile = File("logs/$name/latest.log")
        if(logFile.exists()){
            ZipFile(File("logs/$name/${DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS").format(LocalDateTime.now())}.zip")).use {
                it.addFile(logFile)
                logFile.delete()
            }
        }

        logFile.createNewFile()
    }

    private fun writeToFile(level: Level, message: String){
        val now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now())
        logFile.appendText("[$now] [${level.name}] $message\n")
    }

    /**
     * Logs a message at the [Level.INFO] level.
     * @param message The message to log
     */
    fun info(message: String){
        javaLogger.info(message)
        writeToFile(Level.INFO, message)
    }

    /**
     * Logs a message at the [Level.WARNING] level.
     * @param message The message to log
     */
    fun warning(message: String){
        javaLogger.warning(message)
        writeToFile(Level.WARNING, message)
    }

    /**
     * Logs a message at the [Level.SEVERE] level.
     * @param message The message to log
     */
    fun severe(message: String){
        javaLogger.severe(message)
        writeToFile(Level.SEVERE, message)
    }

    /**
     * Logs a message at the [Level.SEVERE] level with an exception.
     * @param message The message to log
     * @param exception The throwable to log
     */
    fun severe(message: String, exception: Exception) {
        val newMessage = "$message\n${Utils.getStackTrace(exception)}"
        javaLogger.severe(newMessage)
        writeToFile(Level.SEVERE, newMessage)
    }

    /**
     * Logs a message at the [Level.FINE] level.
     * @param message The message to log
     */
    fun fine(message: String){
        javaLogger.fine(message)
        writeToFile(Level.FINE, message)
    }

    /**
     * Logs a message at the [Level.FINER] level.
     * @param message The message to log
     */
    fun finer(message: String){
        javaLogger.finer(message)
        writeToFile(Level.FINER, message)
    }

    /**
     * Logs a message at the [Level.FINEST] level.
     * @param message The message to log
     */
    fun finest(message: String){
        javaLogger.finest(message)
        writeToFile(Level.FINEST, message)
    }

    /**
     * Logs a message at the [Level.ALL] level with the prefix '[DEBUG]'.
     * @param message The message to log
     */
    fun debug(message: String) {
        writeToFile(Level.ALL, "[DEBUG] $message")
        if(File(".loggingmodule_debug").exists()) {
            javaLogger.log(Level.ALL, "[DEBUG] $message")
        }
    }


}