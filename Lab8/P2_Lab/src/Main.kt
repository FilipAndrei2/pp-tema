import java.io.File
import memento.*
import observer.*

fun main()
{
    val caretaker = Caretaker()
    val smallWordConsumer = SmallWordConsumer(caretaker)
    val largeWordConsumer = LargeWordConsumer(caretaker)

    val observable = Observable()
    observable.add(smallWordConsumer)
    observable.add(largeWordConsumer)

    val originator = Originator()

    val text = File("lorem_ipsum").readText()

    val words = text.split(" ")

    for(word in words)
    {
        originator.setMessage(word)
        caretaker.addMemento(originator.saveToMemento())
        observable.notifyObservers()
    }
}