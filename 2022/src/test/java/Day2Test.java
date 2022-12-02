import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class Day2Test {
    @Test
    public void canReturnSolution() {
        Day2 day2 = new Day2();
        assertThat(day2.part1()).isEqualTo(15);
    }
}