package xyz.theprogramsrc.loggingmodule.filter

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.Logger
import org.apache.logging.log4j.core.filter.AbstractFilter
import org.apache.logging.log4j.message.Message

/**
 * Representation of a LogFilter
 * @param result The result to use if the filter matches
 * @param filteredStrings The strings that a message must contain to be filtered
 */
open class LogFilter(val result: FilterResult = FilterResult.NEUTRAL, val filteredStrings: Array<String>): AbstractFilter() {

    /**
     * Extra requirements for a string filter to be matched
     */
    open val extraRequirements = emptyArray<String>()

    private fun process(message: String?): Filter.Result {
        if(message != null){
            if(filteredStrings.any { message.lowercase().contains(it.lowercase()) } && extraRequirements.any { message.lowercase().contains(it.lowercase()) }){
                return Filter.Result.valueOf(result.name)
            }
        }

        return Filter.Result.NEUTRAL
    }

    /**
     * Registers this log filter to work with the root logger.
     */
    fun register() {
        val logger = LogManager.getRootLogger() as Logger
        logger.addFilter(this)
    }

    override fun filter(event: LogEvent?): Filter.Result = process(event?.message?.formattedMessage)

    override fun filter(logger: Logger?, level: Level?, marker: Marker?, msg: Message, t: Throwable?): Filter.Result = process(msg.formattedMessage)

    override fun filter(logger: Logger?, level: Level?, marker: Marker?, msg: Any, t: Throwable?): Filter.Result = process(msg.toString())

    override fun filter(logger: Logger?, level: Level?, marker: Marker?, msg: String?, vararg params: Any?): Filter.Result = process(msg)

}
