package observer

import memento.Caretaker

class LargeWordConsumer(private val caretaker: Caretaker) : Observer
{
    private var wordCount = 0

    override fun update()
    {
        if(caretaker.getSavedStates()[caretaker.getSavedStates().size - 1].getState().length > 7)
        {
            println("Afisare cuvant pentru observer LargeWordConsumer: ${caretaker.getSavedStates()[caretaker.getSavedStates().size - 1].getState()}")
            wordCount++
            if (wordCount % 7 == 0)
            {
                println("*".repeat(50))
                val index = caretaker.getSavedStates().size % 7
                val state = caretaker.getSavedStates()[index]
                println("Restaurare stare pentru observer.LargeWordConsumer")
                println("Stare restaurata: ${state.getState()}")
                println("*".repeat(50))
            }
        }
    }
}