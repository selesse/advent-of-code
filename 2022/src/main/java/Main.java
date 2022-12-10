import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        Day9 day9 = new Day9();
        day9.run();

        if (args.length == 1) {
            printSolution(Integer.parseInt(args[0]));
        }
    }

    private static void printSolution(int day) throws Exception {
        Class<?> clazz = Class.forName("Day" + day);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method run = clazz.getMethod("printSolution");
        run.invoke(instance);
        System.out.println("");
    }
}
