import java.text.SimpleDateFormat
import java.util.Date

fun String.convertStringToDate(format: String) : Date?
{
    return try
    {
        val formatter = SimpleDateFormat(format)
        formatter.parse(this)
    }
    catch (e: Exception)
    {
        null
    }
}

fun main()
{
    val dateString = "2024-05-04 14:32:50"
    val format = "yyyy-MM-dd HH:mm:ss"
    val date = dateString.convertStringToDate(format)
    println("Date: $date")
}