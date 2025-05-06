import org.graalvm.polyglot.*;

import java.util.ArrayList;
import java.util.List;

class Polyglot
{

    private static List<Integer> PythonRandomNumerList()
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        Value result = polyglot.eval("python",
                "import random" + "\n" +
                       "def generate_random() : " + "\n" +
                       "   sample = [random.randint(-2147483648, 2147483647) for x in range(20)]" + "\n" +
                       "   return sample" + "\n\n" +
                       "generate_random()");

        List<Integer> list= new ArrayList<>();

        for(int i = 0; i < 20; ++i)
        {
            list.add(result.getArrayElement(i).asInt());
        }

        polyglot.close();

        return list;
    }

    private static void JSPrintList(List<Integer> list)
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        polyglot.eval("js", "console.log(" + list + ")");

        polyglot.close();
    }

    private static Double RSort(List<Integer> list)
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        Value result = polyglot.eval("R",
                "sorted <- sort(c(" + list.toString().replaceAll("[\\[\\]]", "") + "))" + "\n" +
                       "print(sorted)" + "\n" +
                       "mean(sorted[(floor(0.2 * length(sorted)) + 1)  : (length(sorted) - floor(0.2 * length(sorted)))])");

        return result.asDouble();
    }

    public static void main(String[] args)
    {
        List<Integer> result = PythonRandomNumerList();
        JSPrintList(result);
        Double mean = RSort(result);

        System.out.println("Media : " + mean);
    }
}