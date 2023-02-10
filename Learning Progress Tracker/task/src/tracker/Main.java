package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Learning Progress Tracker");
        String input;
        do {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            } else if (input.isBlank()) {
                System.out.println("No input");
            } else {
                System.out.println("Unknown command!");
            }

        } while (scanner.hasNextLine());
    }
}
