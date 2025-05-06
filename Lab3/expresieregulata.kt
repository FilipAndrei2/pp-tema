import java.io.BufferedReader
import java.io.FileReader

fun EliminareNumarPagina(File : String) : String
{
    var sirModificat = ""

    BufferedReader(FileReader(File)).use {
        Reader -> var Line : String?

        while(Reader.readLine().also{ Line=it } != null)
        {
           sirModificat += Line?.let { Regex("^\\d+$").replace(it, "") } + "\n"
        }
    }

    return sirModificat
}

fun EliminareSalturiLaLinieNouaMultiple(sir : String) : String
{
    val sir1 : String = Regex("\\n+").replace(sir, "\n")
    return sir1
}

fun EliminareSpatiiMultiple(sir : String) : String
{
    val sir1 : String = Regex("\\h+").replace(sir, " ")
    return sir1
}

fun SchimbareMapareVeche(sir : String) : String
{
    val sir1 : String = sir.replace('Ţ', 'Ț')
    val sir2 : String = sir1.replace('ţ', 'ț')
    val sir3 : String = sir2.replace('ş', 'ș')
    val sir4 : String = sir3.replace('Ş', 'Ș')
    return sir4
}

fun EliminareNumeAutor(sir : String) : String
{
    val sir1 : String = Regex("(©|author|written\\s+by)\\s*\\p{L}+", RegexOption.IGNORE_CASE).replace(sir, "")
    return sir1
}

fun EliminareCapitole(sir : String) : String
{
    val sir1 : String = Regex("(Chapter\\s+\\d+|Capitolul\\s+\\d+).*\\n", RegexOption.IGNORE_CASE).replace(sir, "$1\n")
    return sir1
}

fun main() {
    print("Dati caleea fisierului de unde doriti sa luati : ")
    val file: String? = readLine()

    if (file != null) {
        val str1 = EliminareNumarPagina(file)

        if (str1 != null) {
            val str2 = EliminareSalturiLaLinieNouaMultiple(str1)

            if (str2 != null) {
                val str3 = EliminareSpatiiMultiple(str2)

                if (str3 != null) {
                    val str4 = EliminareNumeAutor(str3)

                    if (str4 != null) {
                        val str5 = EliminareCapitole(str4)

                        if (str5 != null) {
                            val str6 = SchimbareMapareVeche(str5)
                            print(str6)
                        }
                    }
                }
            }
        }
    }
}
