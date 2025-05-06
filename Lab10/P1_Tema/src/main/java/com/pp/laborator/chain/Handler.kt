package com.pp.laborator.chain

interface Handler
{
    var next1 : Handler?
    var next2 : Handler?
    var next3 : Handler?
    suspend fun handleRequest(forwardDirection: String, messageToBeProcessed: String)
}