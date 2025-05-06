package com.pp.laborator.chain

import kotlinx.coroutines.*

class CEOHandler(
    override var next1: Handler? = null,
    override var next2: Handler? = null,
    override var next3: Handler? = null
): Handler
{
    override suspend fun handleRequest(forwardDirection: String, messageToBeProcessed: String): Unit = coroutineScope{
        val priority = messageToBeProcessed.split(":")[0]
        if(priority.toInt() < 1 || priority.toInt() > 4)
        {
            throw IllegalArgumentException("Nu se poate ca prioritatea sa fie atat, trebuie sa fie intre 1 si 4!")
        }

        val message = messageToBeProcessed.split(":")[1]

        val ForwardDirection: String = forwardDirection

        when(ForwardDirection)
        {
            "None" ->
            {
                val job = launch { println("Sarcina acceptatita de superior!") }

                job.join()
            }

            "Right" -> {
                if (priority == "1")
                {
                    val job1 = launch { println("Sunt CEO si prelucrez mesajul : $message") }

                    delay((1..1000L).random())

                    job1.join()
                }

                val job2 = launch { next1?.handleRequest("Right", messageToBeProcessed) }

                job2.join()
            }

            "Up" ->
            {
                val job = launch { next2?.handleRequest("None", messageToBeProcessed) }
                job.join()
            }

            else ->
            {
                throw IllegalArgumentException("Nu este un argument valid, se poate doar : None, Right, Up!")
            }
        }
    }
}