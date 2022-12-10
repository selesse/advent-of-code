import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day9Test {
    private Day9 day9;

    @Before
    public void setup() {
        day9 = new Day9();
    }

    @Test
    public void canReturnSolutionForPart1() {
        assertThat(day9.part1()).isEqualTo(13);
    }

    @Test
    public void canMoveRight() {
        Day9.Coordinate tailCoordinate = new Day9.Coordinate(4, 4);
        Day9.Coordinate headCoordinate = new Day9.Coordinate(6, 4);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(5, 4));
    }

    @Test
    public void canMoveLeft() {
        Day9.Coordinate tailCoordinate = new Day9.Coordinate(4, 4);
        Day9.Coordinate headCoordinate = new Day9.Coordinate(2, 4);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(3, 4));
    }

    @Test
    public void canMoveUp() {
        Day9.Coordinate tailCoordinate = new Day9.Coordinate(4, 4);
        Day9.Coordinate headCoordinate = new Day9.Coordinate(4, 6);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(4, 5));
    }

    @Test
    public void canMoveDown() {
        Day9.Coordinate tailCoordinate = new Day9.Coordinate(4, 4);
        Day9.Coordinate headCoordinate = new Day9.Coordinate(4, 2);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(4, 3));
    }

    @Test
    public void canStandStill() {
        Day9.Coordinate tailCoordinate = new Day9.Coordinate(2, 4);
        Day9.Coordinate headCoordinate = new Day9.Coordinate(1, 4);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(2, 4));

    }

    @Test
    public void canMoveNortheast() {
        var tailCoordinate = new Day9.Coordinate(3, 3);
        var headCoordinate = new Day9.Coordinate(4, 5);
        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(4, 4));
    }

    @Test
    public void canMoveNorthwest() {
        Day9.Coordinate tailCoordinate = new Day9.Coordinate(3, 3);
        Day9.Coordinate headCoordinate = new Day9.Coordinate(5, 4);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(4, 4));
    }

    @Test
    public void canMoveSoutheast() {
        Day9.Coordinate tailCoordinate = new Day9.Coordinate(3, 3);
        Day9.Coordinate headCoordinate = new Day9.Coordinate(2, 1);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(2, 2));
    }

    @Test
    public void canMoveSouthwest() {
        Day9.Coordinate tailCoordinate = new Day9.Coordinate(3, 3);
        Day9.Coordinate headCoordinate = new Day9.Coordinate(1, 2);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(2, 2));
    }

    @Test
    public void canDoCornersProperly() {
        var tailCoordinate = new Day9.Coordinate(4, 3);
        var headCoordinate = new Day9.Coordinate(2, 4);

        assertThat(day9.moveTailCloserToHead(tailCoordinate, headCoordinate))
                .isEqualTo(new Day9.Coordinate(3, 4));
    }
}
