fun replicateElements(n: Int, list: List<Int>): List<Int>
{
    return list.flatMap { element -> List(n) { element } }
}

fun main()
{
    println("Enter a number:")
    val n = readlnOrNull()?.toIntOrNull() ?: throw NumberFormatException("Nu se poate da de la tastatura ceva ce nu e un numar!")

    val list = listOf(1, 2, 3)
    val replicatedList = replicateElements(n, list)

    println("Original list: $list")
    println("Replicated list: $replicatedList")
}