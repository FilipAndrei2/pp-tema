//import libraria principala polyglot din graalvm
import org.graalvm.polyglot.*;
import java.util.Hashtable;
import java.util.Vector;

//clasa principala - aplicatie JAVA
class Polyglot {
    //metoda privata pentru conversie low-case -> up-case folosind functia toupper() din R
    private static String RToUpper(String token){
        //construim un context care ne permite sa folosim elemente din R
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei funcitiei R, toupper(String)
        //pentru aexecuta instructiunea I din limbajul X, folosim functia graalvm polyglot.eval("X", "I");
        Value result = polyglot.eval("R", "toupper(\""+token+"\");");
        //utilizam metoda asString() din variabila incarcata cu output-ul executiei pentru a mapa valoarea generica la un String
        String resultString = result.asString();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultString;
    }

    //metoda privata pentru evaluarea unei sume de control simple a literelor unui text ASCII, folosind PYTHON
    private static int SumCRC(String token){
        //construim un context care ne permite sa folosim elemente din PYTHON
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();
        //folosim o variabila generica care va captura rezultatul excutiei functiei PYTHON, sum()
        //avem voie sa inlocuim anumite elemente din scriptul pe care il construim spre evaluare, aici token provine din JAVA, dar va fi interpretat de PYTHON
        Value result = polyglot.eval("python",
                 "def calculate_sum(token) : " + "\n" +
                 "   sum = 0" + "\n" +
                 "   for ch in token :" + "\n" +
                 "       x = ord(ch)" + "\n" +
                 "       sum += 6 * x * x * x - 4 * x * x + 8 * x - 1 " + "\n" +
                 "   return sum" + "\n" +
                 "\n" +
                 "calculate_sum(\"" + token.substring(1, token.length() - 1) + "\")");
        //utilizam metoda asInt() din variabila incarcata cu output-ul executiei, pentru a mapa valoarea generica la un Int
        int resultInt = result.asInt();
        // inchidem contextul Polyglot
        polyglot.close();

        return resultInt;
    }

    //functia MAIN
    public static void main(String[] args) {
        //construim un context pentru evaluare elemente JS
        Context polyglot = Context.create();
        //construim un array de string-uri, folosind cuvinte din pagina web:  https://chrisseaton.com/truffleruby/tenthings/
        Value array = polyglot.eval("js", "[\"If\",\"we\",\"run\",\"the\"];");
        //pentru fiecare cuvant, convertim la upcase folosind R si calculam suma de control folosind PYTHON
        Hashtable<Integer, Vector<String>> hashtable = new Hashtable<>();
        for (int i = 0; i < array.getArraySize();i++){
            String element = array.getArrayElement(i).asString();
            String upper = RToUpper(element);
            int crc = SumCRC(upper);

            Vector<String> words = hashtable.getOrDefault(crc, new Vector<>());
            words.add(upper);

            hashtable.put(crc, words);
        }

        for(Integer key : hashtable.keySet())
        {
            System.out.println(key + " : " + hashtable.get(key));
        }

        // inchidem contextul Polyglot
        polyglot.close();
    }
}

