import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1");
        System.out.println(part1());
    }

    public int part1() {
        var instructions = getInstructionsFromInput();
        var tailMoves = new ArrayList<Coordinate>();

        var initialCoordinate = new Coordinate(0, 0);
        tailMoves.add(initialCoordinate);

        var currentCoordinate = initialCoordinate;
        var currentTailCoordinate = initialCoordinate;

        for (Instruction instruction : instructions) {
            var newCoordinate = switch (instruction.direction) {
                case WEST -> new Coordinate(currentCoordinate.x - instruction.numberOfSteps, currentCoordinate.y);
                case EAST -> new Coordinate(currentCoordinate.x + instruction.numberOfSteps, currentCoordinate.y);
                case NORTH -> new Coordinate(currentCoordinate.x, currentCoordinate.y + instruction.numberOfSteps);
                case SOUTH -> new Coordinate(currentCoordinate.x, currentCoordinate.y - instruction.numberOfSteps);
                default -> throw new IllegalStateException("Unexpected value: " + instruction.direction);
            };
            var tailCatchupMoves = computeTailMoves(currentCoordinate, newCoordinate, currentTailCoordinate);
            currentCoordinate = newCoordinate;
            tailMoves.addAll(tailCatchupMoves);
            currentTailCoordinate = tailCatchupMoves.get(tailCatchupMoves.size() - 1);
            tailMoves.add(currentTailCoordinate);
        }
        return (int) tailMoves.stream().distinct().count();
    }

    private List<Instruction> getInstructionsFromInput() {
        return getLinesForPart1().stream()
                .map(line -> {
                    var directionAndAmount = line.split(" ");
                    var direction = Direction.fromString(directionAndAmount[0]);
                    var amount = Integer.parseInt(directionAndAmount[1]);
                    return new Instruction(direction, amount);
                }).toList();
    }

    private List<Coordinate> computeTailMoves(
            Coordinate currentCoordinate,
            Coordinate newCoordinate,
            Coordinate currentTailCoordinate
    ) {
        List<Coordinate> headMoves = computeHeadMoves(currentCoordinate, newCoordinate);
        List<Coordinate> tailMoves = new ArrayList<>();

        for (Coordinate headCoordinate : headMoves) {
            currentTailCoordinate = moveTailCloserToHead(currentTailCoordinate, headCoordinate);
            tailMoves.add(currentTailCoordinate);
        }
        return tailMoves;
    }

    Coordinate moveTailCloserToHead(Coordinate tail, Coordinate head) {
        if (tail.isOneAway(head)) {
            return tail;
        }
        boolean left = head.x > tail.x;
        boolean up = head.y > tail.y;
        boolean down = head.y < tail.y;
        boolean right = head.x < tail.x;
        boolean isDiagonal = (up && left) || (up && right) || (down && left) || (down && right);
        var vectorDifference = new Coordinate(head.x - tail.x, head.y - tail.y);
        if (isDiagonal) {
            boolean closerHorizontally = Math.abs(head.x - tail.x) < Math.abs(head.y - tail.y);
            if (closerHorizontally) {
                return new Coordinate(tail.x + vectorDifference.x, tail.y + vectorDifference.y + (up ? -1 : 1));
            } else {
                return new Coordinate(tail.x + vectorDifference.x + (left ? -1 : 1), tail.y + vectorDifference.y);
            }
        }
        if (left) {
            return new Coordinate(tail.x + vectorDifference.x - 1, tail.y);
        } else if (right) {
            return new Coordinate(tail.x + vectorDifference.x + 1, tail.y);
        } else if (up) {
            return new Coordinate(tail.x, tail.y + vectorDifference.y - 1);
        } else if (down) {
            return new Coordinate(tail.x, tail.y + vectorDifference.y + 1);
        }
        throw new IllegalArgumentException();
    }

    private List<Coordinate> computeHeadMoves(Coordinate currentCoordinate, Coordinate newCoordinate) {
        List<Coordinate> coordinates = new ArrayList<>();
        boolean horizontal = currentCoordinate.y == newCoordinate.y;
        boolean vertical = currentCoordinate.x == newCoordinate.x;
        if (horizontal) {
            boolean left = currentCoordinate.x > newCoordinate.x;
            if (left) {
                for (int i = currentCoordinate.x - 1; i >= newCoordinate.x; i--) {
                    coordinates.add(new Coordinate(i, currentCoordinate.y));
                }
            } else {
                for (int i = currentCoordinate.x + 1; i <= newCoordinate.x; i++) {
                    coordinates.add(new Coordinate(i, currentCoordinate.y));
                }
            }
        }
        if (vertical) {
            boolean up = currentCoordinate.y < newCoordinate.y;
            if (up) {
                for (int j = currentCoordinate.y + 1; j <= newCoordinate.y; j++) {
                    coordinates.add(new Coordinate(currentCoordinate.x, j));
                }
            } else {
                for (int j = currentCoordinate.y - 1; j >= newCoordinate.y; j--) {
                    coordinates.add(new Coordinate(currentCoordinate.x, j));
                }
            }
        }
        return coordinates;
    }

    public record Instruction(Direction direction, int numberOfSteps) {
    }

    public enum Direction {
        WEST("L"),
        EAST("R"),
        NORTH("U"),
        SOUTH("D"),
        NORTHWEST("NW"),
        NORTHEAST("NE"),
        SOUTHWEST("SW"),
        SOUTHEAST("SE"),
        ;

        private final String stringValue;

        Direction(String stringValue) {
            this.stringValue = stringValue;
        }

        public static Direction fromString(String stringValue) {
            return Arrays.stream(values()).filter(x -> stringValue.equals(x.stringValue)).findFirst().orElseThrow();
        }
    }

    public record Coordinate(int x, int y) {
        public boolean isOneAway(Coordinate otherCoordinate) {
            return Math.abs(x - otherCoordinate.x) <= 1 && Math.abs(y - otherCoordinate.y) <= 1;
        }
    }
}
