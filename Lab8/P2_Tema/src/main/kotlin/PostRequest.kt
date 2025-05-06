import khttp.post
import khttp.responses.Response
import prototype.GenericRequest

class PostRequest(url: String, params: Map<String, String>)
{
    private var genericRequest: GenericRequest = GenericRequest("", null)

    init
    {
        genericRequest.url = url
        genericRequest.params = params
    }

    fun postData() : Response
    {
        val parentalControlDissalow : List<String> = listOf(
            "pornhub.com",
            "8tube.xxx",
            "redtube.com",
            "kink.com",
            "youjizz.com",
            "xvideos.com",
            "youporn.com",
            "brazzers.com",
            "omegle.com",
            "paltalk.com",
            "talkwithstranger.com",
            "chatroulette.com",
            "chat-avenue.com",
            "chatango.com",
            "teenchat.com",
            "wireclub.com",
            "chathour.com",
            "chatzy.com",
            "chatib.us",
            "e-chat.co",
            "4chan.org",
            "reddit.com",
            "somethingawful.com",
            "topix.com",
            "stormfront.org",
            "kiwifarms.net",
            "voat.co",
            "8kun.top",
            "incels.me",
            "tinder.com",
            "match.com",
            "bumble.com",
            "meetme.com",
            "okcupid.com",
            "pof.com",
            "eharmony.com",
            "zoosk.com",
            "hinge.co",
            "grindr.com",
            "ashleymadison.com",
            "adultfriendfinder.com",
            "betonline.ag",
            "freespin.com",
            "bovada.lv",
            "slotocash.im",
            "royalacecasino.com",
            "pokerstars.com",
            "888casino.com",
            "sportsbetting.ag",
            "betway.com",
            "liveleak.com",
            "bestgore.com",
            "theync.com",
            "documentingreality.com",
            "ogrish.tv",
            "hackthissite.org",
            "thepiratebay.org",
            "wikileaks.org",
            "darkweblinks.net",
            "illegalhack.com",
            "stormfront.org",
            "4chan.org",
            "gab.com",
            "nationalvanguard.org",
            "dailystormer.su"
        )
        if(parentalControlDissalow.contains(genericRequest.url.substringAfter("/").drop(1).substringBefore("/").substringAfter("www.")))
        {
            throw Exception("Site-ul nu este acceptat de controlul parental!")
        }
        return post(genericRequest.url, genericRequest.params!!)
    }
}