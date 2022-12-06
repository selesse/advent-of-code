import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class Day6Test {
    private final TestCondition testCondition;

    public record TestCondition(String inputString, int charactersUntilStartOfPacketPart1,
                                int charactersUntilStartOfPacketPart2) {
    }

    public Day6Test(TestCondition testCondition) {
        this.testCondition = testCondition;
    }

    @Parameterized.Parameters
    public static List<TestCondition> testConditions() {
        return List.of(
                new TestCondition("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, 19),
                new TestCondition("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, 23),
                new TestCondition("nppdvjthqldpwncqszvftbrmjlhg", 6, 23),
                new TestCondition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, 29),
                new TestCondition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, 26)
        );
    }


    @Test
    public void canReturnCharactersGivenInputPart1() {
        Day6 day6 = new Day6();
        assertThat(
                day6.charactersUntilStartOfPacket(testCondition.inputString, 4)
        ).isEqualTo(testCondition.charactersUntilStartOfPacketPart1);
    }

    @Test
    public void canReturnCharactersGivenInputPart2() {
        Day6 day6 = new Day6();
        assertThat(
                day6.charactersUntilStartOfPacket(testCondition.inputString, 14)
        ).isEqualTo(testCondition.charactersUntilStartOfPacketPart2);
    }
}
