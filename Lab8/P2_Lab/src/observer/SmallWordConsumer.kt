package observer

import memento.Caretaker

class SmallWordConsumer(private val caretaker: Caretaker): Observer
{
    private var wordCount = 0

    override fun update()
    {
        if(caretaker.getSavedStates()[caretaker.getSavedStates().size - 1].getState().length <= 7)
        {
            println("Afisare cuvant pentru observer SmallWordConsumer: ${caretaker.getSavedStates()[caretaker.getSavedStates().size - 1].getState()}")
            wordCount++
            if (wordCount % 10 == 0)
            {
                println("*".repeat(50))
                val index = caretaker.getSavedStates().size % 10
                val state = caretaker.getSavedStates()[index]
                println("Restaurare stare pentru observer.SmallWordConsumer")
                println("Stare restaurata: ${state.getState()}")
                println("*".repeat(50))
            }
        }
    }
}