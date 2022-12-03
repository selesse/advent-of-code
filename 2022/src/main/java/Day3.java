import java.util.HashMap;
import java.util.Map;

public class Day3 extends AocChallenge {
    @Override
    void run() {
        System.out.println("Part 1");
        System.out.println(part1());
    }

    public int part1() {
        var lines = getLinesForPart1();
        return lines.stream()
                .map(line -> {
                    var firstCompartmentItems = line.substring(0, line.length() / 2);
                    var secondCompartmentItems = line.substring(line.length() / 2);

                    var firstKeys = toFrequencyMap(firstCompartmentItems).keySet();
                    var secondKeys = toFrequencyMap(secondCompartmentItems).keySet();

                    var commonElement = firstKeys.stream().filter(secondKeys::contains).findFirst().orElseThrow();

                    return toCharacterPriority(commonElement);
                }).reduce(0, Integer::sum);
    }

    private Map<Character, Integer> toFrequencyMap(String string) {
        Map<Character, Integer> map = new HashMap<>();
        for (char character : string.toCharArray()) {
            map.merge(character, 1, Integer::sum);
        }
        return map;
    }

    private int toCharacterPriority(Character character) {
        if (character >= 97) {
            return character - 'a' + 1;
        }
        return character - 'A' + 26 + 1;
    }
}
