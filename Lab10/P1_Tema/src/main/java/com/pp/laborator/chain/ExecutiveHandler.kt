package com.pp.laborator.chain

import kotlinx.coroutines.*

class ExecutiveHandler(
    override var next1: Handler? = null,
    override var next2: Handler? = null,
    override var next3: Handler? = null
): Handler
{
    override suspend fun handleRequest(forwardDirection: String, messageToBeProcessed: String): Unit = coroutineScope{
        val priority = messageToBeProcessed.split(":")[0]
        if(priority.toInt() < 1 || priority.toInt() > 4)
        {
            throw IllegalArgumentException("Nu se poate ca prioritatea sa fie atat, trebuie sa fie intre 2 si 4!")
        }

        val message = messageToBeProcessed.split(":")[1]

        val ForwardDirection: String =
        if(forwardDirection != "None" && forwardDirection != "Up")
        {
            if(priority == "2" || priority == "1")
            {
                "Down"
            }
            else
            {
                "Right"
            }
        }
        else
        {
            forwardDirection
        }

        when(ForwardDirection)
        {
            "None" ->
            {
                val job = launch { println("Sarcina incheiata cu succes din Executive!") }

                job.join()
            }

            "Right" ->
            {
                val job = launch { next1?.handleRequest("Right", messageToBeProcessed) }

                job.join()
            }

            "Down" ->
            {
                val job1 = launch { println("Sunt Executive si prelucrez mesajul : $message") }

                delay((1..1000L).random())

                val job2 =
                if(priority == "1")
                {
                    launch { next3?.handleRequest("Up", messageToBeProcessed) }
                }
                else
                {
                    launch { next2?.handleRequest("Up", messageToBeProcessed) }
                }

                job1.join()
                job2.join()
            }

            "Up" ->
            {
                val job = launch {
                    if(priority == "2")
                    {
                        next2?.handleRequest("None", messageToBeProcessed)
                    }
                    next3?.handleRequest("Up", messageToBeProcessed)
                }

                job.join()
            }

            else ->
            {
                throw IllegalArgumentException("Nu este un argument valid, se poate doar : None, Right, Down, Up!")
            }
        }
    }
}