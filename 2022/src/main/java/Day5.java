import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1:");
        System.out.println("Top crates: " + part1());
    }

    public String part1() {
        var parts = getPart1AsString().split("\n\n");
        var startingState = parts[0];
        var moves = parts[1];

        var stacks = toListOfStacks(startingState);
        var instructions = toListOfInstructions(moves);

        moveBoxes(stacks, instructions);
        return stacks.stream().map(stack -> stack.boxes().peek()).collect(Collectors.joining(""));
    }

    private static final Pattern instructionPattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
    private List<Instruction> toListOfInstructions(String moves) {
        return Arrays.stream(moves.split("\n"))
                .map(instructionLine -> {
                    Matcher matcher = instructionPattern.matcher(instructionLine);
                    if (!matcher.matches()) {
                        throw new AssertionError();
                    }
                    return new Instruction(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3))
                    );
                }).toList();
    }

    private List<BoxStack> toListOfStacks(String state) {
        var listOfStacks = new ArrayList<BoxStack>();

        var lines = state.split("\n");
        var stackContents = Arrays.stream(lines).toList().subList(0, lines.length - 1);
        var lineWithBoxes = lines[lines.length - 1];
        var stacks = Arrays.stream(lineWithBoxes.split(" ")).filter(x -> !x.isEmpty()).toList();
        stacks.forEach(stack -> listOfStacks.add(new BoxStack(stack, new Stack<>())));

        stackContents.forEach(stackLine -> {
            var spaces =Arrays.stream(stackLine.split("    ")).collect(Collectors.joining(" ")).split(" ");
            for (int i = 0; i < spaces.length; i++) {
                String space = spaces[i].replace("[", "").replace("]", "");
                if (!space.isEmpty()) {
                    BoxStack boxStack = listOfStacks.get(i);
                    boxStack.boxes.push(space);
                }
            }
        });

        return listOfStacks.stream().map(x -> new BoxStack(x.name, reverseStack(x.boxes))).toList();
    }

    private Stack<String> reverseStack(Stack<String> stack) {
        var reversedStack = new Stack<String>();
        List<String> items = new ArrayList<>(stack);
        Collections.reverse(items);
        items.forEach(reversedStack::push);
        return reversedStack;
    }

    private void moveBoxes(List<BoxStack> stacks, List<Instruction> instructions) {
        instructions.forEach(instruction -> {
            BoxStack fromBox = stacks.get(instruction.from - 1);
            BoxStack toBox = stacks.get(instruction.to - 1);
            for (int i = 0; i < instruction.amount; i++) {
                String element = fromBox.boxes().pop();
                toBox.boxes().push(element);
            }
        });
    }


    public record BoxStack(String name, Stack<String> boxes) {}
    public record Instruction(int amount, int from, int to) {}
}
