package com.pp.laborator.factory

import com.pp.laborator.chain.Handler

abstract class AbstractFactory
{
    abstract fun getHandler(handler: String): Handler
}