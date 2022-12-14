import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1");
        System.out.println(part1());

        System.out.println("Part 2");
        System.out.println(part2());
    }

    public int part1() {
        var lines = getLinesForPart1();
        var sequences = getSequenceFromInput(lines);

        var rootDirectory = buildFilesystem(sequences);
        var directories = getDirectories(rootDirectory);
        var allDirectories = new ArrayList<Directory>();
        allDirectories.add(rootDirectory);
        allDirectories.addAll(directories);

        return allDirectories.stream()
                .map(Directory::size)
                .filter(x -> x <= 100e3)
                .reduce(0, Integer::sum);
    }

    public int part2() {
        var lines = getLinesForPart1();
        var sequences = getSequenceFromInput(lines);

        var rootDirectory = buildFilesystem(sequences);
        var directories = getDirectories(rootDirectory);
        var allDirectories = new ArrayList<Directory>();
        allDirectories.add(rootDirectory);
        allDirectories.addAll(directories);

        var fileSystemSize = 70000000;
        var currentSize = rootDirectory.size();
        int unusedSpace = fileSystemSize - currentSize;
        int deletionRequired = 30000000 - unusedSpace;

        return allDirectories.stream()
                .map(Directory::size)
                .filter(x -> x >= deletionRequired)
                .sorted()
                .findFirst().orElseThrow();
    }

    public List<Directory> getDirectories(Directory directory) {
        List<Directory> directories = new ArrayList<>();
        for (Node node : directory.nodes) {
            if (node instanceof Directory directoryNode) {
                directories.add(directoryNode);
                directories.addAll(getDirectories(directoryNode));
            }
        }
        return directories;
    }

    private Directory buildFilesystem(List<Sequence> sequences) {
        List<Node> nodes = new ArrayList<>();

        String currentDirectoryPath = "/";
        for (Sequence sequence : sequences) {
            if (sequence instanceof ChangeDirectoryInstruction) {
                String currentDirectoryString = ((ChangeDirectoryInstruction) sequence).desiredDirectory();
                if (!currentDirectoryString.equals("..")) {
                    if (!currentDirectoryString.equals("/")) {
                        currentDirectoryPath = buildPath(currentDirectoryPath, currentDirectoryString);
                    }
                } else {
                    var parts = List.of(currentDirectoryPath.split("/"));
                    currentDirectoryPath = String.join("/", parts.subList(0, parts.size() - 1));
                }
            }
            if (sequence instanceof ListDirectoryContents) {
                var currentDirectory =
                        new Directory(
                                currentDirectoryPath,
                                getNodesFromOutput(currentDirectoryPath, ((ListDirectoryContents) sequence).output())
                        );
                nodes.add(currentDirectory);
            }
        }

        var rootNode = (Directory) nodes.get(0);
        List<Directory> directories = new ArrayList<>();
        for (Node node : nodes) {
            var directory = ((Directory) node);
            directories.add(directory);
        }
        for (Directory directory : directories) {
            if (directory.path.equals("/")) {
                continue;
            }
            Directory newDirectory =
                    directories.stream().filter(x -> x.path.equals(directory.path)).findFirst().orElseThrow();
            rootNode.add(newDirectory);
        }
        return rootNode;
    }

    private List<Node> getNodesFromOutput(String currentDirectoryPath, List<String> output) {
        return output.stream()
                .filter(line -> !line.startsWith("dir"))
                .map(line -> {
                    var parts = line.split(" ");
                    if (line.startsWith("dir")) {
                        return new Directory(buildPath(currentDirectoryPath, parts[1]), new ArrayList<>());
                    }
                    return new Leaf(Integer.parseInt(parts[0]), buildPath(currentDirectoryPath, parts[1]));
                }).toList();
    }

    public String buildPath(String base, String path) {
        if (base.equals("/")) {
            return base + path;
        }
        return base + "/" + path;
    }

    public List<Sequence> getSequenceFromInput(List<String> lines) {
        var instructions = new ArrayList<Sequence>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.startsWith("$ ")) {
                String[] promptCommandAndInstruction = line.split(" ");
                var instructionType = TerminalInstruction.fromString(promptCommandAndInstruction[1]);
                if (instructionType == TerminalInstruction.CHANGE_DIRECTORY) {
                    instructions.add(new ChangeDirectoryInstruction(promptCommandAndInstruction[2]));
                }
                else if (instructionType == TerminalInstruction.LIST_DIRECTORY_CONTENTS) {
                    List<String> lsOutput = new ArrayList<>();
                    String nextLine;
                    while (i + 1 < lines.size() && (nextLine = lines.get(i + 1)) != null) {
                        if (nextLine.startsWith("$ ")) {
                            break;
                        }
                        lsOutput.add(nextLine);
                        i++;
                    }
                    instructions.add(new ListDirectoryContents(lsOutput));
                }
            }
        }

        return instructions;
    }

    public interface Sequence {}

    public record ChangeDirectoryInstruction(String desiredDirectory) implements Sequence {}
    public record ListDirectoryContents(List<String> output) implements Sequence {}

    public static class Leaf extends Node {
        private final int size;
        private final String name;

        public Leaf(int size, String name) {
            this.size = size;
            this.name = name;
        }

        @Override
        public int size() {
            return size;
        }
    }
    public abstract static class Node {
        abstract int size();
    }
    public static class Directory extends Node {
        private final String path;
        private List<Node> nodes;

        public Directory(String path, List<Node> nodes) {
            this.path = path;
            this.nodes = nodes;
        }

        public String getPath() {
            return path;
        }

        public Node getNode(String desiredPath) {
            if (path.equals(desiredPath)) {
                return this;
            }
            for (Node node : nodes) {
                if (node instanceof Leaf) {
                    var name = path + "/" + ((Leaf) node).name;
                    if (name.equals(desiredPath)) {
                        return node;
                    }
                }
                if (node instanceof Directory) {
                    var name = path + "/" + ((Directory) node).getPath();
                    if (name.equals(desiredPath)) {
                        return node;
                    }
                    Node node1 = ((Directory) node).getNode(desiredPath);
                    if (node1 != null) {
                        return node1;
                    }
                }
            }
            return null;
        }

        @Override
        public int size() {
            return nodes.stream().map(Node::size).reduce(0, Integer::sum);
        }

        public void add(Directory node) {
            var parts = List.of(node.path.split("/"));
            String parentPath = String.join("/", parts.subList(0, parts.size() - 1));
            if (parentPath.isBlank()) {
                parentPath = "/";
            }
            if (parentPath.equals(this.path)) {
                var newList = new ArrayList<>(nodes);
                newList.add(node);
                this.nodes = newList;
            }
            else {
                Directory parent = (Directory) getNode(parentPath);
                parent.add(node);
            }
        }
    }


    public enum TerminalInstruction {
        CHANGE_DIRECTORY("cd"),
        LIST_DIRECTORY_CONTENTS("ls");

        private final String command;

        TerminalInstruction(String command) {
            this.command = command;
        }

        public static TerminalInstruction fromString(String string) {
            return Arrays.stream(values()).filter(x -> x.command.equals(string)).findFirst().orElseThrow();
        }
    }
}
