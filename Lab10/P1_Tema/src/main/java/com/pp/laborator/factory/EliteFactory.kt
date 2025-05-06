package com.pp.laborator.factory

import com.pp.laborator.chain.CEOHandler
import com.pp.laborator.chain.ExecutiveHandler
import com.pp.laborator.chain.Handler
import com.pp.laborator.chain.ManagerHandler

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