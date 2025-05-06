class Birth(private val year: Int, private val Month: Int, private val Day: Int)
{
    override fun toString() : String
    {
        return "($Day.$Month.$year)"
    }
}

class Contact(val Name: String, var Phone: String, private val BirthDate: Birth)
{
    fun ChangePhone(NewPhone : String?)
    {
        if(NewPhone?.matches(Regex("[0-9]+")) == true && NewPhone.length >= 4)
        {
            Phone = NewPhone
        }
        else
        {
            println("Nu s-a schimbat numarul pentru ca nu este un numar valid!")
        }
    }

    fun Search(Alegere : String?, NameOrPhone : String?) : Boolean
    {
        when(Alegere)
        {
            "1" ->
            {
                if(Name == NameOrPhone)
                {
                    println("A fost gasit numele!")
                    Print()
                    return true
                }
            }
            "2" ->
            {
                if(Phone == NameOrPhone)
                {
                    println("A fost gasit numarul!")
                    Print()
                    return true
                }
            }
            else ->
            {
                println("Nu exista aceasta alegere, doar 1 sau 2!")
                return false
            }
        }
        println("Nu a fost gasit data $NameOrPhone!")
        return false
    }

    fun Print()
    {
        println("Name: $Name, Mobile: $Phone, Date: $BirthDate")
    }
}

fun main()
{
    val agenda = mutableListOf<Contact>()
    agenda.add(Contact("Mihai", "0744321987", Birth(1900, 11, 25)))
    agenda += Contact("George", "0761332100", Birth(2002, 3, 14))
    agenda += Contact("Liviu" , "0231450211", Birth(1999, 7, 30))
    agenda += Contact("Popescu", "0211342787", Birth(1955, 5, 12))
    for (persoana in agenda)
    {
        persoana.Print()
        println("Doriti sa schimbati numarul de telefon? Daca de apasati 1 : ")
        val ok : String? = readlnOrNull()
        if(ok == "1")
        {
            print("Dati numarul de telefon pe care doriti sa-l puneti : ")
            val NewPhone : String? = readlnOrNull()
            persoana.ChangePhone(NewPhone)
        }
    }

    println("Daca doriti sa cautati o persoana dupa nume sau numar de telefon? Apasati 1 :")
    val ok : String? = readlnOrNull()
    if(ok == "1")
    {
        println("Dupa ce doriti? Dupa nume sau dupa numar de telefon?1 pentru nume 2 pentru numar de telefon.")
        val alegere : String? = readlnOrNull()
        when(alegere)
        {
            "1" -> print("Introduceti numele : ")
            "2" -> print("Introduceti numarul : ")
        }
        val NameOrPhone: String? = readlnOrNull()
        if(alegere == "1" || alegere == "2")
        {
            for (persoana in agenda)
            {
                if (persoana.Search(alegere, NameOrPhone))
                {
                    break
                }
            }
        }
        else
        {
            println("Nu exita alta alegere in afara de 1 si 2!")
        }
    }

    println("Agenda dupa eliminare contact [George]:")
    agenda.removeAt(1)
    for (persoana in agenda)
    {
        persoana.Print()
    }
    agenda.remove(Contact("Liviu" , "0231450211", Birth(1999, 7, 30)))
    println("Agenda dupa eliminare contact [Liviu]:")
    agenda.removeAt(1)
    for (persoana in agenda)
    {
        persoana.Print()
    }
}


