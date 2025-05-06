package proxy

import GetRequest
import HTTPGet
import khttp.responses.Response

class CleanGetRequest(private val getRequest: GetRequest) : HTTPGet
{
    private val parentalControlDissalow : List<String> = listOf(
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

    override fun getResponse(): Response
    {
        if(parentalControlDissalow.contains(getRequest.getGenericRequest().url.substringAfter("/").drop(1).substringBefore("/").substringAfter("www.")))
        {
            throw Exception("Site-ul nu este acceptat de controlul parental!")
        }
        return getRequest.getResponse()
    }

    fun getURL() : String
    {
        return getRequest.getGenericRequest().url
    }
}