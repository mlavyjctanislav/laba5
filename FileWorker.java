import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class FileWorker {
    private final Path inputPath;
    private final Path outputPath;

    public FileWorker(Path inputPath, Path outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public String read() throws IOException {
        return Files.readString(inputPath, StandardCharsets.UTF_8);
    }

    public void write(String content) throws IOException {
        Files.writeString(outputPath, content, StandardCharsets.UTF_8);
    }
}