import org.jsoup.Jsoup
import org.jsoup.parser.Parser

data class RSSItem(val title : String, val link : String, val description : String, val pubDate : String)

class RSSFeed
{
    val items = mutableListOf<RSSItem>()

    fun addItem(item : RSSItem)
    {
        items.add(item)
    }
}

fun main()
{
    print("Datu url-ul de unde doriti sa luati feed-ul RSS : ")
    val url : String? = readlnOrNull()

    val document = Jsoup.connect(url).parser(Parser.xmlParser()).get()
    val itemElements = document.select("item")

    val feed = RSSFeed()

    for(item in itemElements)
    {
        val title = item.select("title").text()
        val link = item.select("link").text()
        val description =  item.select("description").text()
        val pubDate =  item.select("pubDate").text()


        val rssItem = RSSItem(title, link, description, pubDate)

        feed.addItem(rssItem)
    }

    feed.items.forEach{
        println("Titlu : ${it.title}")
        println("Link : ${it.link}")
        println("Descriere : ${it.description}")
        println("Data publicatiei : ${it.pubDate}")
        println("\n")
    }
}
