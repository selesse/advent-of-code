import java.util.Arrays;
import java.util.Collections;

public class Day1 extends AocChallenge {
    @Override
    public void run() {
        int maxCalories = part1Solution();

        System.out.println("Part 1");
        System.out.println("Max calories: " + maxCalories);

        System.out.println("Part 2");
        System.out.println("Top 3 calories counted: " + part2Solution());
    }

    public int part1Solution() {
        var input = getPart1AsString();
        var sequences = input.split("\n\n");
        return Arrays.stream(sequences)
                .map(x -> sumOfArrayOfStringNumbers(x.split("\n")))
                .max(Integer::compareTo)
                .orElseThrow();
    }

    public int part2Solution() {
        var input = getPart1AsString();
        var sequences = input.split("\n\n");
        var topThreeElves = Arrays.stream(sequences)
                .map(x -> sumOfArrayOfStringNumbers(x.split("\n")))
                .sorted(Collections.reverseOrder())
                .limit(3)
                .toList();
        return topThreeElves.stream().reduce(0, Integer::sum);
    }

    private static Integer sumOfArrayOfStringNumbers(String[] numbers) {
        return Arrays.stream(numbers).map(Integer::parseInt).reduce(0, Integer::sum);
    }
}
