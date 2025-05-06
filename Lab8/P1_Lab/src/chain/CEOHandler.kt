package chain

class CEOHandler(override var next1: Handler? = null, override var next2: Handler? = null): Handler
{
    override fun handleRequest(forwardDirection: String, messageToBeProcessed: String)
    {
        val priority = messageToBeProcessed.split(":")[0]
        if(priority.toInt() < 1 || priority.toInt() > 4)
        {
            throw IllegalArgumentException("Nu se poate ca prioritatea sa fie atat, trebuie sa fie intre 1 si 4!")
        }

        val message = messageToBeProcessed.split(":")[1]

        var ForwardDirection = "Right"
        if(forwardDirection != "None" && forwardDirection != "Up")
        {
            if(priority == "1")
            {
                ForwardDirection = "Down"
            }
        }
        else
        {
            ForwardDirection = forwardDirection
        }

        when(ForwardDirection)
        {
            "None" ->
            {
                println("Sarcina incheiata cu succes din CEO!")
            }

            "Right" ->
            {
                next1?.handleRequest("Right", messageToBeProcessed)
            }

            "Down" ->
            {
                println("Sunt CEO si prelucrez mesajul : $message")
                next2?.handleRequest("Up", messageToBeProcessed)
            }

            "Up" ->
            {
                next2?.handleRequest("None", messageToBeProcessed)
            }

            else ->
            {
                throw IllegalArgumentException("Nu este un argument valid, se poate doar : None, Right, Down, Up!")
            }
        }
    }
}