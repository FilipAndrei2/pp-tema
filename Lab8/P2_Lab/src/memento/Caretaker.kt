package memento

class Caretaker(private val savedStates : MutableList<Memento> = mutableListOf())
{
    fun addMemento(memento: Memento)
    {
        savedStates.add(memento)
    }

    fun getSavedStates() : List<Memento>
    {
        return savedStates
    }
}