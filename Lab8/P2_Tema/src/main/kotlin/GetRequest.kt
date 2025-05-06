import khttp.get
import khttp.responses.Response
import prototype.GenericRequest

class GetRequest(url: String, params: Map<String, String>, private val timeout: Int) : HTTPGet
{
    private var genericReq : GenericRequest = GenericRequest("", null)

    init
    {
        genericReq.url = url
        genericReq.params = params
    }

    override fun getResponse(): Response
    {
        return get(genericReq.url, genericReq.params!!, timeout = timeout.toDouble())
    }

    fun getGenericRequest() : GenericRequest
    {
        return genericReq
    }

}