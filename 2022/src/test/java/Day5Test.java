import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5Test {
    @Test
    public void canReturnSolutionForPart1() {
        Day5 day5 = new Day5();
        assertThat(day5.part1()).isEqualTo("CMZ");
    }
}
