package chain

interface Handler
{
    var next1 : Handler?
    var next2 : Handler?
    fun handleRequest(forwardDirection: String, messageToBeProcessed: String)
}