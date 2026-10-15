import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static final int RED_LINE = 4;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ширину: ");
        int width = Integer.parseInt(scanner.nextLine().trim());

        Path input = Path.of("input.txt");
        Path output = Path.of("output.txt");

        FileWorker fw = new FileWorker(input, output);
        String text = fw.read();
        String result = alignment(text, width);
        fw.write(result);
    }

    static String alignment(String text, int width) {
        String[] paragraphs = text.split("\n", -1);
        StringBuilder out = new StringBuilder();
        for (String paragraph : paragraphs) {
            String para = paragraph.trim();
            if (para.isEmpty()) {
                out.append('\n');
                continue;
            }
            String[] words = para.split("\\s+");
            List<String> lines = buildLines(words, width, RED_LINE);
            for (int i = 0; i < lines.size(); i++) {
                boolean isLast = (i == lines.size() - 1);
                int indent = (i == 0) ? RED_LINE : 0;
                String rendered;
                if (isLast) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < indent; j++) sb.append(' ');
                    sb.append(lines.get(i));
                    rendered = sb.toString();
                } else {
                    rendered = distribute(lines.get(i), width, indent);
                }
                out.append(rendered).append('\n');
            }
        }
        if (out.length() > 0 && out.charAt(out.length() - 1) == '\n') {
            out.setLength(out.length() - 1);
        }
        return out.toString();
    }

    static List<String> buildLines(String[] words, int width, int redLine) {
        List<String> lines = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        int lineIndex = 0;
        for (String w : words) {
            int offset = (lineIndex == 0) ? redLine : 0;
            if (cur.length() == 0) {
                cur.append(w);
            } else if (cur.length() + 1 + w.length() + offset > width) {
                lines.add(cur.toString());
                lineIndex++;
                cur.setLength(0);
                cur.append(w);
            } else {
                cur.append(' ').append(w);
            }
        }
        if (cur.length() > 0) lines.add(cur.toString());
        return lines;
    }

    static String distribute(String line, int width, int indent) {
        String[] words = line.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) sb.append(' ');

        if (words.length == 1) {
            sb.append(words[0]);
            while (sb.length() < width) sb.append(' ');
            return sb.toString();
        }

        int wordsLen = 0;
        for (String w : words) wordsLen += w.length();

        int totalSpaces = width - indent - wordsLen;
        if (totalSpaces < words.length - 1) totalSpaces = words.length - 1;

        int gaps = words.length - 1;
        int base = totalSpaces / gaps;
        int extra = totalSpaces % gaps;

        for (int i = 0; i < words.length; i++) {
            sb.append(words[i]);
            if (i < words.length - 1) {
                int sp = base + (i < extra ? 1 : 0);
                for (int j = 0; j < sp; j++) sb.append(' ');
            }
        }
        return sb.toString();
    }
}