package observer

class Observable(private val observer: MutableList<Observer> = mutableListOf())
{
    fun add(observer: Observer)
    {
        this.observer.add(observer)
    }

    fun remove(observer: Observer)
    {
        this.observer.remove(observer)
    }

    fun notifyObservers()
    {
        observer.forEach{ it.update() }
    }
}