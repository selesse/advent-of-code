import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class Day2Test {
    @Test
    public void canReturnSolutionForPart1() {
        Day2 day2 = new Day2();
        assertThat(day2.part1()).isEqualTo(15);
    }

    @Test
    public void canReturnSolutionForPart2() {
        Day2 day2 = new Day2();
        assertThat(day2.part2()).isEqualTo(12);
    }
}