package CinemaBuilding

interface PaymentMethod {
    fun pay(fee:Double) : Boolean
    fun getBalance() : Double
}

//CashPayment

package CinemaBuilding

class CashPayment() : PaymentMethod {
    private var availableAmount : Double = 0.0
    override fun pay(fee:Double) : Boolean{
        if(availableAmount+fee<0) {
            println("Nu mai sunt bani!\n")
            return false
        }
        else {
            println("Plata efectuata!\n")
            availableAmount+=fee
            return true
        }
    }

    override fun getBalance(): Double {
        return availableAmount
    }
}

//CardPayment

package CinemaBuilding

class CardPayment(): PaymentMethod {
    private var bankAccount : BankAccount = BankAccount()
    override fun pay(fee:Double) : Boolean {
        println("Introdu PIN: ")
        if(readln().toInt() == bankAccount.getCvvCode()) {
            return bankAccount.updateAmount(fee)
        }
        else {
            println("PIN incorect!")
            return false
        }
    }

    override fun getBalance(): Double {
        return bankAccount.getAvailable()
    }
}

//BankAccount

package CinemaBuilding

import java.time.LocalDate
import java.util.Date

class BankAccount() {
    private var availableAmount : Double = 0.0
    private var cardNumber : String? = null
    private var expirationDate : Date? = null
    private var cvvCode : Int = 101
    private var userName : String? = null
    fun updateAmount(value: Double) : Boolean {
        if(availableAmount + value <0 ) {
            println("Fonduri insuficiente!")
            return false
        }
        else {
            availableAmount += value
            if(value<0) println("Aprobat!") else println("Bani ramasi: +$value")
            return true
        }
    }
    fun getCvvCode() : Int {
        return cvvCode
    }
    fun getAvailable() : Double {
        return availableAmount
    }
}

//Cinema

package CinemaBuilding

class Cinema {
    private var films : MutableSet<Film> = mutableSetOf()
    private var profit : Double = 0.0
    private var clientBase : MutableSet<Client> = mutableSetOf()
    fun registerClient() {
        val client = Client()
        print("Numele - ")
        client.setName(readln())
        print("Varsta - ")
        client.setAge(readln().toInt())
        print("Parola ")
        client.setPassword(readln())
        clientBase.add(client)
    }

    fun searchClient(name:String) : Client? {
        for ( client in clientBase) {
            if(client.getName() == name) {
                return client
            }
        }
        return null
    }
    fun displayFilms() {
        for ( film in films) {
            film.displayInfo()
        }
    }
    fun removeFilm(name:String) {
        for(film in films) {
            if(film.getName() === name) {
                films.remove(film)
            }
        }
    }
    fun addFilm(newFilm: Film) {
        films.add(newFilm)
    }
    private fun searchFilm(name : String) : Film?{
        for(film in films) {
            if(film.getName() == name) {
                return film
            }
        }
        return null
    }

    fun displayPaymentMethod(client: Client) {
        print("Alege metoda de plata: ")
        if(client.getPaymentMethod()==0) print("1-cash\n")
        else print("2-card\n")
    }
    fun sellTicket(client: Client) {
        println("La ce film doresti sa te uiti?")
        var ok = false
        while(!ok) {
            ok=true
            val movieName = readln()
            val movie = searchFilm(movieName)
            if(movie == null) {
                println("Nu avem acest film momentan!")
                ok=false
            }
            else {
                println("Cate bilete doresti?")
                val nr = readln().toInt()
                print("Total: " + movie.getPrice() * nr + "")
                client.pay(movie.getPrice()*nr)
            }
        }
    }
}

//Client

package CinemaBuilding

class Client {
    private var name : String? = null
    private var age : Int? = null
    private var password : String? = null
    private var payType : Int = 0
    //0-cash is default
    //1-card
    private val cash : PaymentMethod = CashPayment()
    private val card : PaymentMethod = CardPayment()

    fun pay(fee:Double) {
        if(payType==0) cash.pay(-1*fee) else card.pay(-1*fee)
    }
    fun setName(name:String) {
        this.name=name
    }
    fun setAge(age:Int) {
        this.age=age
    }
    fun setPassword(pw:String) {
        this.password=pw
    }
    fun getName() : String? {
        return name
    }
    fun getPassword() : String? {
        return password
    }
    fun addBalance(amount:Double) {
        if(payType==0) cash.pay(amount) else card.pay(amount)
    }
    fun getBalance() : Double{
        if(payType==0) return cash.getBalance() else return card.getBalance()
    }
    fun setPaymentMethod(opt:Int) {
        payType=opt
    }
    fun getPaymentMethod() : Int {
        return payType
    }
}

//Film

package CinemaBuilding

class Film(fname:String, fPrice:Double) {
    private var name : String =fname
    private var price : Double = fPrice
    fun displayInfo() {
        println("Filmul: $name\nPretul biletului: $price\n")
    }
    fun getPrice() : Double {
        return price
    }
    fun getName() : String {
        return name
    }
}

//main

package CinemaBuilding

fun main() {
    val cinemaCity = Cinema()
    var film = Film("Zootropolis",17.5)
    cinemaCity.addFilm(film)
    film = Film("Kung Fu Panda", 13.0)
    cinemaCity.addFilm(film)
    film = Film("Masini", 10.0)
    cinemaCity.addFilm(film)
    film = Film("Aladin", 21.5)
    cinemaCity.addFilm(film)
    var run = true
    var user: Client? = Client()
    while (run == true) {
        run = false
        var registered = false
        while (registered == false) {
            registered = true
            println("Welcome to Cinema City! Aveti un cont? y/n")

            val opt = readln()
            if (opt.trim() == "n") {
                cinemaCity.registerClient()
                registered = false
            }
            else if (opt.trim() == "y") {
                print("Sign in:\n\tName: ")
                val name = readln()
                user = cinemaCity.searchClient(name)
                if (user == null) {
                    println("Nu aveti cont!")
                    registered = false
                }
            }
            else registered=false
        }
        var loggedin = false
        while (loggedin == false) {
            loggedin = true
            print("Parola: ")
            val pw = readln()
            if (pw == user?.getPassword()) {
                println("Bine ai venit, " + user.getName() + "!")
                var dowork = true
                while(dowork==true) {
                    dowork=false
                    print(
                        "Ce doresti sa faci?\n\t 1 - Afisare filme" +
                                "\n\t 2 - Cumpara bilete\n\t 3 - Arata fonduri\n\t 4 - Adauga fonduri\n\t" +
                                " 5 - Schimba metoda de plata\n\t 0 - Log out\n\t >> "
                    )

                    val opt = readln().toInt()
                    when (opt) {
                        0 -> {
                            println("Logging out ...")
                        }

                        1 -> {
                            cinemaCity.displayFilms()
                            dowork=true
                        }

                        2 -> {
                            cinemaCity.sellTicket(user)
                            dowork=true
                        }
                        3 -> {
                            if(user.getPaymentMethod()==0) print("cash") else print("card")
                            println(user.getBalance())
                            dowork=true
                        }
                        4 -> {
                            print("Cat doresti sa adaugi?")
                            user.addBalance(readln().toDouble())
                            dowork=true
                        }
                        5 -> {
                            cinemaCity.displayPaymentMethod(user)
                            val opt = readln().toInt()
                            user.setPaymentMethod(opt)
                            dowork=true
                        }
                        else -> {
                            println("Nu ati introdu o optiune valida")
                            dowork=true
                        }
                    }
                }
            } else {
                println("Parola incorecta!")
                loggedin = false
            }
        }
    }
}



