import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class Day4 extends AocChallenge {
    @Override
    void run() {
        System.out.println("Part 1:");
        System.out.println("Number of assignment pairs: " + part1());

        System.out.println("Part 2:");
        System.out.println("Overlapping pairs: " + part2());
    }

    public int part1() {
        return getLinesForPart1().stream()
                .filter(line -> {
                    var parts = line.split(",");
                    var firstAssignment = parts[0];
                    var secondAssignment = parts[1];

                    var firstRange = getRangeFromAssignment(firstAssignment);
                    var secondRange = getRangeFromAssignment(secondAssignment);

                    boolean firstRangeContainsSecond = new HashSet<>(firstRange).containsAll(secondRange);
                    boolean secondRangeContainsFirst = new HashSet<>(secondRange).containsAll(firstRange);

                    return firstRangeContainsSecond || secondRangeContainsFirst;
                })
                .toList()
                .size();
    }

    public int part2() {
        return getLinesForPart1().stream()
                .filter(line -> {
                    var parts = line.split(",");
                    var firstAssignment = parts[0];
                    var secondAssignment = parts[1];

                    var firstRange = getRangeFromAssignment(firstAssignment);
                    var secondRange = getRangeFromAssignment(secondAssignment);

                    boolean firstRangeContainsSecond = new HashSet<>(firstRange).stream().anyMatch(secondRange::contains);
                    boolean secondRangeContainsFirst = new HashSet<>(secondRange).stream().anyMatch(firstRange::contains);

                    return firstRangeContainsSecond || secondRangeContainsFirst;
                })
                .toList()
                .size();
    }

    private List<Integer> getRangeFromAssignment(String assignment) {
        var parts = assignment.split("-");
        var beginning = Integer.parseInt(parts[0]);
        var end = Integer.parseInt(parts[1]);

        return IntStream.rangeClosed(beginning, end).boxed().toList();
    }
}
