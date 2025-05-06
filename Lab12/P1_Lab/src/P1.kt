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

fun main()
{

    val a = 2
    val b = 4

    println("Este " + a + " prim? " + a.isPrime())
    println("Este " + b + " prim? " + b.isPrime())
}