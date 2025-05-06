package com.pp.laborator.factory

class FactoryProducer
{
    fun getFactory(choice: String): AbstractFactory
    {
        return when(choice)
        {
            "Elite" ->
            {
                EliteFactory()
            }

            "HappyWorker" ->
            {
                HappyWorkerFactory()
            }

            else ->
            {
                throw IllegalArgumentException("Nume de fabrica gresit, poate fi doar Elite sau HappyWorker!")
            }
        }
    }
}