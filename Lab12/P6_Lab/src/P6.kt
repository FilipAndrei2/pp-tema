fun removeDuplicates(input: String): String
{
    return input.asSequence().distinct().joinToString("")
}

fun main()
{
    print("Introduceti un sir : ")
    val input = readlnOrNull() ?: ""
    println("Sirul de caractere intial este : $input")
    val result = removeDuplicates(input)
    println("Sirul de caractere fara caractere duplicate este : $result")
}