package UI;

import java.util.Scanner;

public class ConsoleUI {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String MAGENTA = "\033[0;35m";
    public static final String PINK = "\033[38;5;13m";


    public void menu(){
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println(MAGENTA + "++++++++++" + RESET + " Welcome to the Library " + MAGENTA + "++++++++++" + RESET);
            System.out.println(MAGENTA + "+ 1. " + RESET + "Add a document                        " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 2. " + RESET + "Borrow a document                     " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 3. " + RESET + "Return a document                     " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 4. " + RESET + "Display all documents                 " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 5. " + RESET + "Search for a document                 " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 6. " + RESET + "Quit                                  " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "++++++++++++++++++++++++++++++++++++++++++++" + RESET);

            System.out.print(PINK+"Please select an option (1-6): "+RESET);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Add a Document selected.");
                    System.out.print(PINK+"Please select an option (1-2): "+RESET);
                    handleMiniMenu(scanner);
                    break;
                case 2:
                    System.out.println("Borrow a Document selected.");
                    handleMiniMenu(scanner);
                    break;
                case 3:
                    System.out.println("Return a Document selected.");
                    handleMiniMenu(scanner);
                    break;
                case 4:
                    System.out.println("Display All Documents selected.");
                    handleMiniMenu(scanner);
                    break;
                case 5:
                    System.out.println("Search for a Document selected.");
                    handleMiniMenu(scanner);
                    break;
                case 6:
                    System.out.println(BLUE+"See you soon...");
                    running = false;
                    break;
                default:
                    System.out.println(RED+"+ Error: Invalid choice. Please select a number between 1 and 6 +"+RESET);
            }
        }

        scanner.close();
    }

    private static void handleMiniMenu(Scanner scanner) {
        boolean backToMainMenu = true;

        while (backToMainMenu) {
            System.out.println(MAGENTA + "++++++++++++++++++++++++++++" + RESET);
            System.out.println(MAGENTA + "+ 1. " + RESET + "Back to Main Menu     " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 2. " + RESET + "Exit                  " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "++++++++++++++++++++++++++++" + RESET);

            System.out.print("Please select an option (1-2): ");
            int miniChoice = scanner.nextInt();
            scanner.nextLine();

            switch (miniChoice) {
                case 1:
                    backToMainMenu = false;
                    break;
                case 2:
                    System.out.println(BLUE+"See you soon..."+RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED+"+ Error: Invalid choice. Please select 1 or 2 +"+RESET);
            }
        }
    }
}
