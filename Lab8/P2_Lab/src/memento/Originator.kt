package memento

class Originator(private var message: String = "")
{
    fun saveToMemento() : Memento
    {
        return Memento(message)
    }

    fun restoreFromMemento(memento : Memento)
    {
        message = memento.getState()
    }

    fun setMessage(message : String)
    {
        this.message = message
    }
}