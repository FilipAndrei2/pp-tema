package bridge

abstract class AndGateAbstaction(val implementation: AndGateImplementation)
{
    abstract fun calculate() : Boolean
}