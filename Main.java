import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandProcessor processor = new CommandProcessor();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }
            try {
                processor.processCommand(line);
            } catch (LibraryException e) {
                System.out.println("ERROR " + e.getMessage());
            }
        }
        scanner.close();
    }
}