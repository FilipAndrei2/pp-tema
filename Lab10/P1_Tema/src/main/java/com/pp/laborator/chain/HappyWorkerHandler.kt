package com.pp.laborator.chain

import kotlinx.coroutines.*

class HappyWorkerHandler(
    override var next1: Handler? = null,
    override var next2: Handler? = null,
    override var next3: Handler? = null
): Handler
{
    override suspend fun handleRequest(forwardDirection: String, messageToBeProcessed: String): Unit = coroutineScope{
        val priority = messageToBeProcessed.split(":")[0]
        if(priority.toInt() != 4)
        {
            throw IllegalArgumentException("Nu se poate ca prioritatea sa fie atat, trebuie sa fie doar 4!")
        }

        val message = messageToBeProcessed.split(":")[1]

        val ForwardDirection: String =
        if(forwardDirection != "None" && forwardDirection != "Up")
        {
            "Down"
        }
        else
        {
            forwardDirection
        }

        when(ForwardDirection)
        {
            "None" ->
            {
                val job = launch { println("Sarcina incheiata cu succes din HappyWorker!") }
                job.join()
            }

            "Down" ->
            {
                val job1 = launch { println("Sunt HappyWorker si prelucrez mesajul : $message") }

                delay((1..1000L).random())

                val job2 = launch { next2?.handleRequest("Up", messageToBeProcessed) }

                job1.join()
                job2.join()
            }

            "Up" ->
            {
                val job = launch {
                    next2?.handleRequest("None", messageToBeProcessed)
                    next1?.handleRequest("Up", messageToBeProcessed)
                }

                job.join()
            }

            else ->
            {
                throw IllegalArgumentException("Nu este un argument valid, se poate doar : None, Down, Up!")
            }
        }
    }
}