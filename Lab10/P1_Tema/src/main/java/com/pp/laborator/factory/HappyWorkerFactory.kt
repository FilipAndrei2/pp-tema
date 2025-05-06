package com.pp.laborator.factory

import com.pp.laborator.chain.Handler
import com.pp.laborator.chain.HappyWorkerHandler

class HappyWorkerFactory: AbstractFactory()
{
    override fun getHandler(handler: String): Handler
    {
        return when(handler)
        {
            "HappyWorker" ->
            {
                HappyWorkerHandler()
            }

            else ->
            {
                throw IllegalArgumentException("Ati dat un nume de handler gresit, poate fi doar HappyWorker!")
            }
        }
    }
}