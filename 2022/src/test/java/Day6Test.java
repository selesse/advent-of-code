import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class Day6Test {
    private final TestCondition testCondition;

    public record TestCondition(String inputString, int charactersUntilStartOfPacket) {
    }

    public Day6Test(TestCondition testCondition) {
        this.testCondition = testCondition;
    }

    @Parameterized.Parameters
    public static List<TestCondition> testConditions() {
        return List.of(
                new TestCondition("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7),
                new TestCondition("bvwbjplbgvbhsrlpgdmjqwftvncz", 5),
                new TestCondition("nppdvjthqldpwncqszvftbrmjlhg", 6),
                new TestCondition("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10),
                new TestCondition("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11)
        );
    }


    @Test
    public void canReturnCharactersGivenInput() {
        Day6 day6 = new Day6();
        assertThat(
                day6.charactersUntilStartOfPacket(testCondition.inputString)
        ).isEqualTo(testCondition.charactersUntilStartOfPacket);
    }
}
