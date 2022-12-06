import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class Day6 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1");
        System.out.println(part1());
    }

    public String part1() {
        var input = getPart1AsString();

        return "" + charactersUntilStartOfPacket(input);
    }

    public int charactersUntilStartOfPacket(String buffer) {
        for (int i = 0; i < buffer.length(); i++) {
            if (i + 4 < buffer.length()) {
                var chunk = buffer.substring(i, i + 4);
                boolean allCharactersAreDifferent =
                        chunk.chars().mapToObj(x -> (char) x).collect(Collectors.toCollection(LinkedHashSet::new)).size() == 4;
                if (allCharactersAreDifferent) {
                    return i + 4;
                }
            }
        }
        throw new IllegalArgumentException();
    }
}
