
public class AsciiTreeApp {

    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    static class TooManyArgumentsException extends Exception {
        public TooManyArgumentsException(String message) {
            super(message);
        }
    }

    static class InvalidFormatException extends Exception {
        public InvalidFormatException(String message) {
            super(message);
        }
    }

    static class NegativeLengthException extends Exception {
        public NegativeLengthException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new InvalidFormatException("Missing length argument.");
            }

            if (args.length > 1) {
                throw new TooManyArgumentsException("Too many arguments. Only one number is expected.");
            }

            int length;
            try {
                length = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                throw new InvalidFormatException("Wrong format: length must be an integer number. Given: " + args[0]);
            }

            if (length < 0) {
                throw new NegativeLengthException("Invalid value: length cannot be negative. Given: " + length);
            }

            drawTree(length);
        } catch (TooManyArgumentsException e) {
            printUsage(e.getMessage());
        } catch (InvalidFormatException e) {
            printUsage(e.getMessage());
        } catch (NegativeLengthException e) {
            System.out.println("Input error: " + e.getMessage());
        }
    }

    private static void printUsage(String reason) {
        System.out.println("Input error: " + reason);
        System.out.println("Usage: java AsciiTreeApp <non-negative integer length>");
    }

    private static void drawTree(int length) {
        if (length <= 1) {
            printCenteredColored("*", 1, YELLOW);
            return;
        }

        int bodyHeight = length - 2;
        int maxWidth = calculateMaxWidth(bodyHeight);

        printCenteredColored("*", maxWidth, YELLOW);

        for (int i = 0; i < bodyHeight; i++) {
            int segmentIndex = i / 2;
            int width = 3 + segmentIndex * 2;
            String base = repeat('*', width);
            String withBaubles = addBaubles(base, i);
            printCenteredColored(withBaubles, maxWidth, GREEN);
        }

        printCenteredColored("*", maxWidth, YELLOW);
    }

    private static int calculateMaxWidth(int bodyHeight) {
        if (bodyHeight <= 0) {
            return 1;
        }
        int segments = (bodyHeight + 1) / 2;
        return 3 + (segments - 1) * 2;
    }

    private static void printCenteredColored(String text, int maxWidth, String color) {
        int padding = (maxWidth - text.length()) / 2;
        if (padding < 0) {
            padding = 0;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(' ');
        }
        sb.append(color);
        sb.append(text);
        sb.append(RESET);
        System.out.println(sb);
    }

    private static String addBaubles(String line, int lineIndex) {
        char[] chars = line.toCharArray();
        if (chars.length >= 3) {
            int left = lineIndex % chars.length;
            int right = chars.length - 1 - left;
            if (left >= 0 && left < chars.length) {
                chars[left] = 'o';
            }
            if (right >= 0 && right < chars.length) {
                chars[right] = 'o';
            }
        }
        return new String(chars);
    }

    private static String repeat(char c, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
