import java.util.Scanner;

public class Input {
    private static final Scanner scanner = new Scanner(System.in);

    public static int readInt(String message, int minimum, int maximum) {
        while (true) {
            System.out.print(message);
            String answer = scanner.nextLine();
            try {
                int value = Integer.parseInt(answer.trim());
                if (value >= minimum && value <= maximum) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Ask again if the user did not enter a whole number.
            }
            System.out.println("Enter a whole number from " + minimum + " to " + maximum + ".");
        }
    }
}
