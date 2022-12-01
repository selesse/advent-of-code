import java.util.Arrays;

public class Day1 extends AocChallenge {
    public void run() {
        int maxCalories = part1Solution();

        System.out.println("Part 1");
        System.out.println("Max calories: " + maxCalories);
    }

    public int part1Solution() {
        var input = getPart1AsString();
        var sequences = input.split("\n\n");
        return Arrays.stream(sequences)
                .map(x -> sumOfArrayOfStringNumbers(x.split("\n")))
                .max(Integer::compareTo)
                .orElseThrow();
    }

    private static Integer sumOfArrayOfStringNumbers(String[] numbers) {
        return Arrays.stream(numbers).map(Integer::parseInt).reduce(0, Integer::sum);
    }
}
