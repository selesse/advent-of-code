import java.lang.reflect.Method;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            runSolution(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        }

        if (args.length == 1) {
            runSolution(Integer.parseInt(args[0]));
        }
    }

    private static void runSolution(int day) throws Exception {
        Class<?> clazz = Class.forName("Day" + day);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method run = clazz.getDeclaredMethod("run");
        run.invoke(instance);
    }
}
