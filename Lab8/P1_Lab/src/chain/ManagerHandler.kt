package chain

class ManagerHandler(override var next1: Handler? = null, override var next2: Handler? = null): Handler
{
    override fun handleRequest(forwardDirection: String, messageToBeProcessed: String)
    {
        val priority = messageToBeProcessed.split(":")[0]
        if(priority.toInt() < 3 || priority.toInt() > 4)
        {
            throw IllegalArgumentException("Nu se poate ca prioritatea sa fie atat, trebuie sa fie 3 sau 4!")
        }

        val message = messageToBeProcessed.split(":")[1]

        var ForwardDirection = "Right"
        if(forwardDirection != "None" && forwardDirection != "Up")
        {
            if(priority == "3")
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
                println("Sarcina incheiata cu succes din Manager!")
            }

            "Right" ->
            {
                next1?.handleRequest("Right", messageToBeProcessed)
            }

            "Down" ->
            {
                println("Sunt Manager si prelucrez mesajul : $message")
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