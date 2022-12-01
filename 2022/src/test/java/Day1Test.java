import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1Test {
    @Test
    public void canReturnSolution() {
        Day1 day1 = new Day1();
        int maxCalories = day1.part1Solution();

        assertThat(maxCalories).isEqualTo(24000);
    }
}