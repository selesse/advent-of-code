import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day4Test {
    @Test
    public void canReturnSolutionForPart1() {
        Day4 day4 = new Day4();
        assertThat(day4.part1()).isEqualTo(2);
    }

    @Test
    public void canReturnSolutionForPart2() {
        Day4 day4 = new Day4();
        assertThat(day4.part2()).isEqualTo(4);
    }
}
