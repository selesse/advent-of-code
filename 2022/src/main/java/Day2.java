import java.util.Arrays;

public class Day2 extends AocChallenge {
    enum GameState {
        WIN("Z"), LOSE("X"), DRAW("Y");

        private final String stringValue;

        GameState(String stringValue) {
            this.stringValue = stringValue;
        }

        static GameState fromString(String value) {
            return Arrays.stream(GameState.values()).filter(x -> x.stringValue.equals(value)).findFirst().orElseThrow();
        }
    }

    enum RockPaperScissors {
        ROCK("A", "X", 1),
        PAPER("B", "Y", 2),
        SCISSORS("C", "Z", 3),
        ;

        final String opponentMove;
        final String myMove;
        final int score;

        RockPaperScissors(String opponentMove, String myMove, int score) {
            this.opponentMove = opponentMove;
            this.myMove = myMove;
            this.score = score;
        }

        static RockPaperScissors fromOpponentMove(String move) {
            return Arrays.stream(RockPaperScissors.values())
                    .filter(x -> x.opponentMove.equals(move))
                    .findFirst()
                    .orElseThrow();
        }

        static RockPaperScissors fromMyMove(String move) {
            return Arrays.stream(RockPaperScissors.values())
                    .filter(x -> x.myMove.equals(move))
                    .findFirst()
                    .orElseThrow();
        }

        int score(RockPaperScissors other) {
            boolean win =
                 this == ROCK && other == SCISSORS ||
                         this == PAPER && other == ROCK ||
                         this == SCISSORS && other == PAPER;

            boolean draw = this == other;
            if (win) {
                return 6 + score;
            }
            if (draw) {
                return 3 + score;
            }
            return score;
        }

        public RockPaperScissors oppositeMoveFor(GameState desiredOutcome) {
            return switch (desiredOutcome) {
                case WIN -> switch (this) {
                    case ROCK -> PAPER;
                    case SCISSORS -> ROCK;
                    case PAPER -> SCISSORS;
                };
                case LOSE -> switch (this) {
                    case ROCK -> SCISSORS;
                    case SCISSORS -> PAPER;
                    case PAPER -> ROCK;
                };
                case DRAW -> this;
            };
        }

        ;
    }


    @Override
    public void run() {
        System.out.println("Score for part 1");
        System.out.println(part1());

        System.out.println("Score for part 2");
        System.out.println(part2());
    }

    public int part1() {
        var lines = getLinesForPart1();
        return lines.stream()
                .map(x -> {
                    var parts = x.split(" ");
                    var opponentMove = RockPaperScissors.fromOpponentMove(parts[0]);
                    var myMove = RockPaperScissors.fromMyMove(parts[1]);

                    return myMove.score(opponentMove);
                })
                .reduce(0, Integer::sum);
    }

    public int part2() {
        var lines = getLinesForPart1();
        return lines.stream()
                .map(x -> {
                    var parts = x.split(" ");
                    var opponentMove = RockPaperScissors.fromOpponentMove(parts[0]);
                    var desiredOutcome = GameState.fromString(parts[1]);

                    var myMove = opponentMove.oppositeMoveFor(desiredOutcome);
                    return myMove.score(opponentMove);
                })
                .reduce(0, Integer::sum);
    }
}
