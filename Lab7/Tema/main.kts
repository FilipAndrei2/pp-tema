import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class HistoryLogRecord(val timestamp: LocalDateTime, val commandLine: String) : Comparable<HistoryLogRecord>
{
   override fun compareTo(other: HistoryLogRecord): Int
   {
       return timestamp.compareTo(other.timestamp)
   }
}


fun <T : Comparable<T>> max(a : T, b : T) : T
{
   return if (a > b) a else b
}


fun <K : Comparable<K>, V> replace(target: V, replacement: V, map: MutableMap<K, out V>)
{
   val mutableMap = map as MutableMap<K, V>
   mutableMap.forEach { (key, value) ->
       if (value == target)
       {
           mutableMap[key] = replacement
       }
   }
}




fun main()
{
   val historyLogRecords = mutableListOf<HistoryLogRecord>()
   val lines = File("history.log").readLines().asSequence()
   var i = 0
   while (i < lines.count() && historyLogRecords.size < 50)
   {
       val recordLines = lines.drop(i).takeWhile { it != "" }
       val recordData = recordLines.map { it.split(": ").let { it[0] to it[1] } }.toMap()
       val timestamp = LocalDateTime.parse(recordData["Start-Date"], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
       val commandLine = recordData["Commandline"] ?: ""
       historyLogRecords.add(HistoryLogRecord(timestamp, commandLine))
       i += recordLines.count() + 1
   }


   val historyLogMap = historyLogRecords.associateBy { it.timestamp }.toMutableMap()


   val target = HistoryLogRecord(
       LocalDateTime.parse("2024-04-01 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
       "apt-get install python3"
   )
   val replacement = HistoryLogRecord(
       LocalDateTime.parse("2024-04-01 10:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
       "apt-get install python3.8",
   )
   replace(target, replacement, historyLogMap)


   print(historyLogMap)
}
