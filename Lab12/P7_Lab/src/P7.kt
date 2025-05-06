fun shrinkString(input: String): String
{
    return input.asSequence()
        .groupBy { it }
        .map{ (char, group) -> if(group.size > 1) "$char${group.size}" else char }
        .joinToString("")
}

fun main()
{
    print("Introduceti un sir : ")
    val input = readlnOrNull() ?: ""
    println("Sirul de caractere intial este : $input")
    val result = shrinkString(input)
    println("Sirul de caractere redus este : $result")
}