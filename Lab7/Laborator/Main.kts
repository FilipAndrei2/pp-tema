import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class SyslogRecord(val timestamp: LocalDateTime, val hostname : String, val applicationName : String, val pid : String?, val logEntry : String)


fun parseSyslog(line : String) : SyslogRecord?
{
   val pattern = """^(\S+) (\S+) (\S+?)(?:\[(\S+)])?: (.+)$""".toRegex()


   val matchResult = pattern.find(line) ?: return null


   val (timestampStr, hostName, applicationName, pid, logEntry) = matchResult.destructured


   val parsedPid = pid.ifEmpty { null }


   val timestamp = try
   {
       LocalDateTime.parse(timestampStr, DateTimeFormatter.ISO_DATE_TIME)
   }
   catch (e : Exception)
   {
       return null
   }


   return SyslogRecord(timestamp, hostName, applicationName, parsedPid, logEntry)
}




fun main()
{
   val thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30)
   val logEntries = File("syslog.txt").useLines { lines ->
       lines.mapNotNull(::parseSyslog)
           .filter { it.timestamp.isAfter(thirtyMinutesAgo) }
           .filter { it.pid != null }
           .groupBy { it.applicationName }
           .mapValues { (_, records) ->
               records.sortedBy { it.logEntry }
           }
   }




   for ((application, records) in logEntries)
   {
       println("Application: $application")
       records.forEach(::println)
       println()
   }
}
