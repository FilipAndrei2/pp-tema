package factory

import chain.*

class EliteFactory: AbstractFactory()
{
    override fun getHandler(handler: String): Handler
    {
        return when(handler)
        {
            "CEO" ->
            {
                CEOHandler()
            }

            "Executive" ->
            {
                ExecutiveHandler()
            }

            "Manager" ->
            {
                ManagerHandler()
            }

            else ->
            {
                throw IllegalArgumentException("Ati dat un nume de handler gresit, poate fi doar : CEO, Executive sau Manager!")
            }
        }
    }
}