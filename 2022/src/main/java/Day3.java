import java.util.HashMap;
import java.util.Map;

public class Day3 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1");
        System.out.println(part1());

        System.out.println("Part 2");
        System.out.println(part2());
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

    public int part2() {
        var sum = 0;

        var lines = getLinesForPart1();
        for (int i = 0; i < lines.size(); i += 3) {
            var firstGroup1 = lines.get(i);
            var firstGroup2 = lines.get(i + 1);
            var firstGroup3 = lines.get(i + 2);

            var firstGroup1Keys = toFrequencyMap(firstGroup1).keySet();
            var firstGroup2Keys = toFrequencyMap(firstGroup2).keySet();
            var firstGroup3Keys = toFrequencyMap(firstGroup3).keySet();

            var commonElement = firstGroup1Keys.stream()
                    .filter(c -> firstGroup2Keys.contains(c) && firstGroup3Keys.contains(c))
                    .findFirst()
                    .orElseThrow();

            sum += toCharacterPriority(commonElement);
        }
        return sum;
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
