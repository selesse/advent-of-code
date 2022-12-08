import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day8 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1");
        System.out.println(part1());
    }

    public int part1() {
        var lines = getLinesForPart1();

        Forest forest = getForestFromInput(lines);

        int visibleEdges = forest.visibleTreesAtEdge();
        int interiorEdges = forest.interiorVisibleEdges();

        return visibleEdges + interiorEdges;
    }

    private Forest getForestFromInput(List<String> lines) {
        int height = lines.size();
        int width = lines.get(0).length();

        int[][] trees = new int[width][height];

        for (int currentHeight = height - 1; currentHeight >= 0; currentHeight--) {
            String line = lines.get(currentHeight);
            for (int currentWidth = 0; currentWidth < width; currentWidth++) {
                trees[currentHeight][currentWidth] = Integer.parseInt("" + line.toCharArray()[currentWidth]);
            }
        }


        return new Forest(trees, width, height);
    }

    public record Forest(int[][] trees, int width, int height) {
        public int visibleTreesAtEdge() {
            return width + width + height + height - 4;
        }

        public int interiorVisibleEdges() {
            List<Visible> visibilities = new ArrayList<>();
            for (int x = 1; x < width - 1; x++) {
                for (int y = 1; y < height - 1; y++) {
                    visibilities.add(computeVisibility(x, y));
                }
            }
            return (int) visibilities.stream().filter(x -> x.totalVisible() >= 1).count();
        }

        private Visible computeVisibility(int initialX, int initialY) {
            int currentValue = trees[initialX][initialY];

            List<Integer> northTrees = new ArrayList<>();
            List<Integer> southTrees = new ArrayList<>();
            List<Integer> eastTrees = new ArrayList<>();
            List<Integer> westTrees = new ArrayList<>();

            for (int j = height - 1; j > initialY; j--) {
                eastTrees.add(trees[initialX][j]);
            }
            for (int j = 0; j < initialY; j++) {
                westTrees.add(trees[initialX][j]);
            }
            for (int i = width - 1; i > initialX; i--) {
                southTrees.add(trees[i][initialY]);
            }
            for (int i = 0; i < initialX; i++) {
                northTrees.add(trees[i][initialY]);
            }

            boolean north = northTrees.stream().allMatch(tree -> tree < currentValue);
            boolean south = southTrees.stream().allMatch(tree -> tree < currentValue);
            boolean east = eastTrees.stream().allMatch(tree -> tree < currentValue);
            boolean west = westTrees.stream().allMatch(tree -> tree < currentValue);

            return new Visible(initialX, initialY, currentValue, north, south, east, west);
        }
    }

    public record Visible(int x, int y, int currentValue, boolean north, boolean south, boolean east, boolean west) {
        public int totalVisible() {
            return Stream.of(north, south, east, west).map(x -> x ? 1 : 0).reduce(0, Integer::sum);
        }
    }
}
