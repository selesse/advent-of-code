import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3Test {
    @Test
    public void canReturnSolutionForPart1() {
        Day3 day3 = new Day3();
        assertThat(day3.part1()).isEqualTo(157);
    }
}