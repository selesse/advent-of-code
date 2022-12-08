import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Day8 extends AocChallenge {
    @Override
    public void run() {
        System.out.println("Part 1");
        System.out.println(part1());

        System.out.println("Part 2");
        System.out.println(part2());
    }

    public int part1() {
        var lines = getLinesForPart1();

        Forest forest = getForestFromInput(lines);

        int visibleEdges = forest.visibleTreesAtEdge();
        int interiorEdges = forest.interiorVisibleEdges();

        return visibleEdges + interiorEdges;
    }

    public int part2() {
        var lines = getLinesForPart1();

        Forest forest = getForestFromInput(lines);
        return forest.maxScenicScore();
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

        private Visible computeVisibility(int x, int y) {
            int currentValue = trees[x][y];

            boolean north = computeNorthTrees(x, y).stream().allMatch(tree -> tree < currentValue);
            boolean south = computeSouthTrees(x, y).stream().allMatch(tree -> tree < currentValue);
            boolean east = computeEastTrees(x, y).stream().allMatch(tree -> tree < currentValue);
            boolean west = computeWestTrees(x, y).stream().allMatch(tree -> tree < currentValue);

            return new Visible(x, y, currentValue, north, south, east, west);
        }

        public int maxScenicScore() {
            List<ScenicScore> scenicScores = new ArrayList<>();
            for (int x = 1; x < width - 1; x++) {
                for (int y = 1; y < height - 1; y++) {
                    scenicScores.add(computeScenicScore(x, y));
                }
            }
            return scenicScores.stream().map(ScenicScore::totalScore).max(Integer::compareTo).orElseThrow();
        }

        private ScenicScore computeScenicScore(int x, int y) {
            int currentValue = trees[x][y];

            var northTrees = computeNorthTrees(x, y);
            var southTrees = computeSouthTrees(x, y);
            var eastTrees = computeEastTrees(x, y);
            var westTrees = computeWestTrees(x, y);
            // Thanks Java!!!!
            Collections.reverse(northTrees);
            Collections.reverse(southTrees);
            Collections.reverse(eastTrees);
            Collections.reverse(westTrees);
            int north = computeVisibilityCount(currentValue, northTrees);
            int south = computeVisibilityCount(currentValue, southTrees);
            int east = computeVisibilityCount(currentValue, eastTrees);
            int west = computeVisibilityCount(currentValue, westTrees);

            return new ScenicScore(x, y, currentValue, north, south, east, west);
        }

        private int computeVisibilityCount(int value, List<Integer> trees) {
            int count = 0;
            for (Integer tree : trees) {
                count++;
                if (tree >= value) {
                    break;
                }
            }
            return count;
        }

        private List<Integer> computeNorthTrees(int x, int y) {
            List<Integer> northTrees = new ArrayList<>();
            for (int i = 0; i < x; i++) {
                northTrees.add(trees[i][y]);
            }
            return northTrees;
        }

        private List<Integer> computeSouthTrees(int x, int y) {
            List<Integer> southTrees = new ArrayList<>();
            for (int i = width - 1; i > x; i--) {
                southTrees.add(trees[i][y]);
            }
            return southTrees;
        }

        private List<Integer> computeEastTrees(int x, int y) {
            List<Integer> eastTrees = new ArrayList<>();
            for (int j = height - 1; j > y; j--) {
                eastTrees.add(trees[x][j]);
            }
            return eastTrees;
        }
        private List<Integer> computeWestTrees(int x, int y) {
            List<Integer> westTrees = new ArrayList<>();
            for (int j = 0; j < y; j++) {
                westTrees.add(trees[x][j]);
            }
            return westTrees;
        }
    }

    public record Visible(int x, int y, int currentValue, boolean north, boolean south, boolean east, boolean west) {
        public int totalVisible() {
            return Stream.of(north, south, east, west).map(x -> x ? 1 : 0).reduce(0, Integer::sum);
        }
    }

    public record ScenicScore(int x, int y, int currentValue, int north, int south, int east, int west) {
        public int totalScore() {
            return north * south * east * west;
        }
    }
}
