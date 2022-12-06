import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Day6 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1");
        System.out.println(part1());

        System.out.println("Part 2");
        System.out.println(part2());
    }

    public String part1() {
        var input = getPart1AsString();

        return "" + charactersUntilStartOfPacket(input, 4);
    }

    public String part2() {
        var input = getPart1AsString();

        return "" + charactersUntilStartOfPacket(input, 14);
    }

    public int charactersUntilStartOfPacket(String buffer, int chunkSize) {
        for (int i = 0; i < buffer.length(); i++) {
            if (i + chunkSize < buffer.length()) {
                var chunk = buffer.substring(i, i + chunkSize);
                boolean allCharactersAreDifferent =
                        toLinkedHashSet(chunk).size() == chunkSize;
                if (allCharactersAreDifferent) {
                    return i + chunkSize;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private static LinkedHashSet<Character> toLinkedHashSet(String chunk) {
        return chunk.chars().mapToObj(x -> (char) x).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
