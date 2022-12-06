import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            for (int i = 1; i < 26; i++) {
                try {
                    printSolution(i);
                } catch (Exception ignored) {}
            }
        }

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
