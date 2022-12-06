import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class AocChallenge {
    public abstract void run();

    public List<String> getLinesForPart1() {
        return getResource(String.format("day%d-1.txt", getDay()));
    }

    public String getPart1AsString() {
        return String.join("\n", getLinesForPart1());
    }

    public List<String> getLinesForPart2() {
        return getResource(String.format("day%d-2.txt", getDay()));
    }

    private Integer getDay() {
        return Integer.parseInt(this.getClass().getName().split("Day")[1]);
    }

    private List<String> getResource(String path) {
        var resource = this.getClass().getClassLoader().getResource(path);
        assert resource != null;
        try {
            return Files.readAllLines(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
