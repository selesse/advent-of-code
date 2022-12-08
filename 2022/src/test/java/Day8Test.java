import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day8Test {
    @Test
    public void canReturnSolutionForPart1() {
        Day8 day8 = new Day8();
        assertThat(day8.part1()).isEqualTo(21);
    }

    @Test
    public void canReturnSolutionForPart2() {
        Day8 day8 = new Day8();
        assertThat(day8.part2()).isEqualTo(8);
    }
}
