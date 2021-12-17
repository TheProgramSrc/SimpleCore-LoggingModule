package xyz.theprogramsrc.loggingmodule

import xyz.theprogramsrc.simplecoreapi.libs.zip4j.ZipFile
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.logging.Level

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

    fun info(message: String){
        javaLogger.info(message)
        writeToFile(Level.INFO, message)
    }

    fun warning(message: String){
        javaLogger.warning(message)
        writeToFile(Level.WARNING, message)
    }

    fun severe(message: String){
        javaLogger.severe(message)
        writeToFile(Level.SEVERE, message)
    }

    fun fine(message: String){
        javaLogger.fine(message)
        writeToFile(Level.FINE, message)
    }

    fun finer(message: String){
        javaLogger.finer(message)
        writeToFile(Level.FINER, message)
    }

    fun finest(message: String){
        javaLogger.finest(message)
        writeToFile(Level.FINEST, message)
    }


}