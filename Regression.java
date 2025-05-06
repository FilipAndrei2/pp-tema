import org.graalvm.polyglot.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Regression
{
    private static void RPrintRegressionInFile(String filename, String path, String colors)
    {
        Context polyglot = Context.newBuilder().allowAllAccess(true).build();

        polyglot.eval("R",
                "library(lattice)" + "\n" +
                "filename <- '" + filename + "'\n" +
                "path <- '" + path + "'\n" +
                "colors <- c('" + colors.replace(",", "','") + "')" + "\n" +
                "data <- read.table('src/dataset.txt', header = TRUE)" + "\n" +
                "regression <- lm(y ~ x, data = data)" + "\n" +
                "panel_with_regression <- function(x, y, ...) " + "\n" +
                "{" + "\n" +
                "   panel.xyplot(x, y, ...)" + "\n" +
                "   panel.abline(coef(regression), col = colors[2], lwd = 2)" + "\n" +
                "}" + "\n" +
                "plot <- xyplot(y ~ x, data = data, col = colors[1], panel = panel_with_regression)" + "\n" +
                "png(file = paste0(path, '/', filename, '.png'))" + "\n" +
                "print(plot)" + "\n" +
                "dev.off()");

        polyglot.close();
    }

    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the filename : ");
        String filename = reader.readLine();

        System.out.print("Enter the path : ");
        String path = reader.readLine();

        System.out.print("Enter the colors : ");
        String colors = reader.readLine();

        RPrintRegressionInFile(filename, path, colors);
    }
}