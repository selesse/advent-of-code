import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1");
        System.out.println(part1());

        System.out.println("Part 2");
        System.out.println(part2(getMoves(getInstructions(getLinesForPart1()))));
    }

    public int part1() {
        var instructions = getInstructions(getLinesForPart1());
        List<Coordinate> tailMoves = getMoves(instructions).tailMoves();
        return (int) tailMoves.stream().distinct().count();
    }

    public int part2(Moves moves) {
        var headMoves = moves.headMoves();
        var tailFollowers = new Followers(9);
        for (Coordinate headMove : headMoves) {
            tailFollowers.slitherTowards(headMove);
        }

        return tailFollowers.visits.get(8).stream().distinct().toList().size();
    }

    private static class Followers {
        private final List<Coordinate> followers;
        private final List<List<Coordinate>> visits;

        public Followers(int capacity) {
            followers = new ArrayList<>(capacity);
            visits = new ArrayList<>(capacity);
            for (int i = 0; i < capacity; i++) {
                followers.add(new Coordinate(0, 0));
                List<Coordinate> visits = new ArrayList<>();
                this.visits.add(visits);
            }
        }

        public void slitherTowards(Coordinate headMove) {
            var currentHead = headMove;
            for (int i = 0; i < followers.size(); i++) {
                Coordinate moveToMake = followers.get(i).follow(currentHead);
                followers.set(i, moveToMake);
                visits.get(i).add(moveToMake);
                currentHead = moveToMake;
            }
        }
    }

    Moves getMoves(List<Instruction> instructions) {
        var tailMoves = new ArrayList<Coordinate>();
        var headMoves = new ArrayList<Coordinate>();

        var initialCoordinate = new Coordinate(0, 0);

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
            var currentHeadMoves = computeHeadMoves(currentCoordinate, newCoordinate);
            var tailCatchupMoves = computeTailMoves(currentHeadMoves, currentTailCoordinate);

            headMoves.addAll(currentHeadMoves);
            tailMoves.addAll(tailCatchupMoves);

            currentCoordinate = newCoordinate;
            currentTailCoordinate = tailCatchupMoves.get(tailCatchupMoves.size() - 1);
        }
        return new Moves(headMoves, tailMoves);
    }

    List<Instruction> getInstructions(List<String> lines) {
        return lines.stream()
                .map(line -> {
                    var directionAndAmount = line.split(" ");
                    var direction = Direction.fromString(directionAndAmount[0]);
                    var amount = Integer.parseInt(directionAndAmount[1]);
                    return new Instruction(direction, amount);
                }).toList();
    }

    private List<Coordinate> computeTailMoves(List<Coordinate> headMoves, Coordinate currentTailCoordinate) {
        List<Coordinate> tailMoves = new ArrayList<>();

        for (Coordinate head : headMoves) {
            currentTailCoordinate = currentTailCoordinate.follow(head);
            tailMoves.add(currentTailCoordinate);
        }
        return tailMoves;
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

    public record Moves(List<Coordinate> headMoves, List<Coordinate> tailMoves) {
    }

    public record Coordinate(int x, int y) {
        public boolean isOneAway(Coordinate otherCoordinate) {
            return Math.abs(x - otherCoordinate.x) <= 1 && Math.abs(y - otherCoordinate.y) <= 1;
        }

        public Coordinate follow(Coordinate head) {
            if (isOneAway(head)) {
                return this;
            }
            var diff = new Coordinate(head.x - x, head.y - y);
            if (diff.x < -1) {
                diff = new Coordinate(-1, diff.y);
            }
            if (diff.x > 1) {
                diff = new Coordinate(1, diff.y);
            }
            if (diff.y > 1) {
                diff = new Coordinate(diff.x, 1);
            }
            if (diff.y < -1) {
                diff = new Coordinate(diff.x, -1);
            }
            return new Coordinate(x + diff.x, y + diff.y);
        }

    }
}
