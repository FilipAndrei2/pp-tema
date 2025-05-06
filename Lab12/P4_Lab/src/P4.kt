import kotlin.properties.Delegates

fun Int.isPrime() : Boolean
{
    if(this == 2 || this == 3)
    {
        return true
    }

    if(this <= 1 || this % 2 == 0 || this % 3 == 0)
    {
        return false
    }

    for(i in 5..this step 6)
    {
        if(i * i <= this)
        {
            if(this % i == 0 || this % (i + 2) == 0)
                return false
        }
    }
    return true
}

var primeNumber : Int by Delegates.vetoable(2) {_, _, newValue -> newValue.isPrime()}

fun main()
{
    println("Numar prim initial: $primeNumber")

    primeNumber = 4
    println("Dupa ce incerc sa pun 4: $primeNumber")

    primeNumber = 5
    println("Dupa ce pun 5: $primeNumber")
}