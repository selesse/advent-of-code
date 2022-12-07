import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day7Test {
    @Test
    public void canReturnSolutionForPart1() {
        Day7 day7 = new Day7();
        assertThat(day7.part1()).isEqualTo(95437);
    }
}
