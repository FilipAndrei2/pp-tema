import org.graalvm.polyglot.*;

class Statistics
{
    private static int[] PythonRead()
    {
        int[] numbers = new int[2];

        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        Value result =  polyglot.eval("python",
                 "l = []" + "\n" +
                        "nr = int(input(\"" + "Dati numarul de aruncari : " + "\"))" + "\n" +
                        "while nr < 1 :" + "\n" +
                        "   print(\"" + "Greseala la introducere, reintrouduceti : " + "\")" + "\n" +
                        "   nr = int(input())" + "\n" +
                        "l.append(nr)" + "\n" +
                        "x = int(input(\"" + "Dati numarul x care trebuie sa fie intre 1 si numarul de aruncari : " + "\"))" + "\n" +
                        "while x < 1 or x > nr :" + "\n" +
                        "   print(\"" + "Greseala la introducere, reintrouduceti : " + "\")" + "\n" +
                        "   x = int(input())" + "\n" +
                        "l.append(x)" + "\n" +
                        "l");

        numbers[0] = result.getArrayElement(0).asInt();
        numbers[1] = result.getArrayElement(1).asInt();


        polyglot.close();

        return numbers;
    }
    private static double RStatistic(int nr_aruncari, int x)
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        Value result = polyglot.eval("R", 
                 "nr_aruncari <- " + nr_aruncari + "\n" +
                        "p <- 0.5" + "\n" +
                        "x <- " + x + "\n" +
                        "pbinom(x, nr_aruncari, p)");

        double res = result.asDouble();

        polyglot.close();
        
        return res;
    }

    public static void main(String[] args)
    {
        int[] numbers = PythonRead();
        double probability = RStatistic(numbers[0], numbers[1]);
        System.out.println("Probabilitatea : " + probability);
    }
}