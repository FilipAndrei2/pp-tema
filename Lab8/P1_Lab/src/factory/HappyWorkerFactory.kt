package factory

import chain.Handler
import chain.HappyWorkerHandler

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