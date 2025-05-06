fun main() {
    val user = User("Iulian Rusu")
    val notepad = NoteManager()
    user.startApp(notepad)
}

//Note.kt
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter

class Note(private var title : String? = null, private var author:String? = null, private var date: String? = null, private var content : String = "",
           private var type : Int = 0) {
    override fun toString() :String { return "${"*".repeat(60)}\nTitlul: $title\nAutorul $author\nData crearii: $date\nTipul: $type\n\n$content${"*".repeat(60)}\n"
    }
    fun getTitle() : String? {
        return title
    }
    fun setTitle(newTitle:String) {
        title=newTitle
    }
    fun setType(newType:Int) {
        type=newType
    }
    fun getType() : Int {
        return type
    }
    fun setContent(text:String) {
        content=text
    }
    fun writeToFile(pathName:String) {
        val saveFile = File("$pathName/$title")
        val writer = BufferedWriter(FileWriter(saveFile))
        writer.write("Autorul: $author\nData crearii: $date\nTipul: $type\n")
        writer.write("\n"+content)
        writer.close()
        println("Notita a fost salvata la adresa: $pathName/$title")
    }
    fun setAuthor(author:String?) {
        this.author=author
    }
    fun setDate(date:String) {
        this.date=date
    }
}

//User.kt
class User(private var name:String? = null) {
    fun startApp(app:NoteManager) {
        app.start(name)
    }
}

//NoteManager.kt
import java.io.BufferedWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.io.File
import java.io.FileWriter

class NoteManager {
    private var notes : MutableSet<Note> = mutableSetOf()
    private var savePath : String = System.getProperty("user.home") + "/Notepad"
    private fun create(name:String?) {
        try {
            var ok : Boolean
            var title : String
            do{
                ok=true;
                print("Titlu notita: ")
                title = readln()
                if(searchNote(title)!=null) {println("Exista deja o notita cu acest titlu");ok=false;}
            }while(!ok)
            print("Tipul notitei: ( 1-Notita simpla, 2-Notita importnata, 3-Lista, 4-Sticky note) >> ")
            val opt = readln().toInt()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            println("Incepe sa scrii. Pentru a incheia scrie \"terminat\" pe un rand nou")
            var text = ""
            var line = readln()
            while(line.trim()!="terminat") {
                text += line +"\n"
                line = readln()
            }
            val note = Note(title,name,currentDate,text,opt)
            note.writeToFile(savePath)
            notes.add(note)
        } catch ( e: NumberFormatException) {
            println("Nu ati introdus un numar valid de notita")
        }
    }
    private fun searchNote(noteName:String) : Note? {
        for(note in notes) {
            if(note.getTitle()==noteName) {
                return note
            }
        }
        return null
    }
    private fun modify(note:Note?) {
        println("Ce nota modifici!")
        var opt : Int?
        do {
            print("Ce trebuie modoficat?\n\t1 - Titlul\n\t2 - Tipul\n\t3 - Continutul\n\t0 - Exit\n >> ")
            opt = try {
                readln().toInt()
            } catch(e: NumberFormatException) {
                50
            }
            when(opt) {
                0 -> {
                    println("\nEditare terminata!")
                    note!!.writeToFile(savePath)
                }
                1 -> {
                    val file = File(savePath+"/"+note!!.getTitle())
                    if ( file.exists()) file.delete()
                    note.setTitle(readln())
                }
                2 -> {
                    note!!.setType(try{readln().toInt()} catch(e : NumberFormatException) {note.getType()})
                }
                3-> {
                    println("Incepe sa scrii. Pentru a incheia scrie \"terminat\" pe un rand nou")
                    var text = ""
                    var line = readln()
                    while(line.trim()!="terminat") {
                        text += line +"\n"
                        line = readln()
                    }
                    note!!.setContent(text)
                }
                else -> {
                    println("\nNu ati introdus un numar valid. Reintrodu:\n")
                }
            }
        }while(opt!=0)
    }
    private fun close() {
        val file = File("$savePath/.data")
        val writer = BufferedWriter(FileWriter(file))
        for ( note in notes ) {
            writer.write(note.toString())
        }
        writer.close()
    }
    fun start(name:String?) {
        try {
            var idx : Int = 0
            var text = ""
            val file = File("$savePath/.data")
            var note = Note()
            if (file.exists()) {
                val lines = file.readLines()
                for (line in lines) {
                    if (line.trim() == "*".repeat(60)) {
                        idx++
                        if (idx==2) {
                            note.setContent(text)
                            notes.add(note)
                            note = Note()
                            idx=0
                            text=""
                        }
                    } else {
                        if(line.startsWith("Titlul:")) note.setTitle(line.substring(line.indexOf(' ')+1))
                        else if(line.startsWith("Tipul:")) note.setType(line.substring(line.indexOf(' ')+1).toInt())
                        else if(line.startsWith("Data crearii:")) note.setDate(line.substring(line.indexOf(' ')+1))
                        else if(line.startsWith("Autorul:")) note.setAuthor(line.substring(line.indexOf(' ')+1))
                        else if(line.trim()!="\n" && line.trim()!="") text+=line+"\n"
                    }
                }
            }
        } catch ( e: Exception) {
            println("Eroare: ${e.message}")
        }
        print("Ati deschis notitele")
        var opt = 1
        while(opt!=0) {
            print("\nSelectati o optiune\n\t1 - Creaza o nota noua\n\t2 - Modifica nota\n\t" +
                    "3 - Sterge nota\n\t4 - Afiseaza notele\n\t0 - Exit\n\t >> ")
            opt = try {
                readln().toInt()
            } catch(e:NumberFormatException) {
                80
            }
            when(opt) {
                0 -> {
                    println("\nAti iesit din notite!")
                    close()
                }
                1 -> {
                    create(name)
                }
                2 -> {
                    print("Ce nota doresti sa modifici(introdu titlul): ")
                    val noteTitle = readln()
                    if(searchNote(noteTitle)!=null) {
                        modify(searchNote(noteTitle))
                    }
                    else println("\nNu exista nota cu titlul specificat\n")
                }
                3 -> {
                    print("Ce nota doresti sa stergi(introdu titlul): ")
                    val noteTitle = readln()
                    if(searchNote(noteTitle)!=null) {
                        notes.remove(searchNote(noteTitle));
                        val file = File("$savePath/$noteTitle")
                        if ( file.exists()) file.delete()
                        println("Nota a fost stearsa!")}
                    else println("\nNu exista nota cu titlul specificat\n")
                }
                4 -> {
                    println(toString())
                }
                else -> {
                    println("\nNu ati inrodus un numar valid. Reintrodu:\n")
                }
            }
        }
    }
    override fun toString() : String {
        var res = ""
        for ( note in notes) {
            res += note.toString() + "\n"
        }
        return res
    }
}

