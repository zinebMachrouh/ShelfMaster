package UI;

import Business.Book;
import Business.Magazine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String MAGENTA = "\033[0;35m";
    public static final String PINK = "\033[38;5;13m";
    public static final String GREEN = "\u001b[92m";
    Scanner scanner = new Scanner(System.in);
    Book book = new Book();
    Magazine magazine = new Magazine();

    public void menu() {
        boolean running = true;

        while (running) {
            System.out.println(MAGENTA + "++++++++++" + RESET + " Welcome to the Library " + MAGENTA + "++++++++++" + RESET);
            System.out.println(MAGENTA + "+ 1. " + RESET + "Add a document                        " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 2. " + RESET + "Borrow a document                     " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 3. " + RESET + "Return a document                     " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 4. " + RESET + "Display documents                     " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 5. " + RESET + "Search for a document                 " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "+ 6. " + RESET + "Quit                                  " + MAGENTA + "+" + RESET);
            System.out.println(MAGENTA + "++++++++++++++++++++++++++++++++++++++++++++" + RESET);

            System.out.print(PINK + "Please select an option (1-6): " + RESET);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleAddDocument();
                    break;
                case 2:
                    handleDocumentStatusChange("BORROWED", "This document is already borrowed!", "Borrow a Document selected.");
                    break;
                case 3:
                    handleDocumentStatusChange("AVAILABLE", "This document is already available!", "Return a Document selected.");
                    break;
                case 4:
                    handleDisplayDocuments();
                    break;
                case 5:
                    handleSearchDocument();
                    break;
                case 6:
                    System.out.println(BLUE + "See you soon..." + RESET);
                    running = false;
                    break;
                default:
                    System.out.println(RED + "+ Error: Invalid choice. Please select a number between 1 and 6 +" + RESET);
            }
        }

        scanner.close();
    }

    private void handleAddDocument() {
        System.out.println("Add a Document selected.");
        System.out.println(BLUE + "+ " + RESET + "Please select an option (1-2): ");
        System.out.println(BLUE + "1. " + RESET + "Add a Book");
        System.out.println(BLUE + "2. " + RESET + "Add a Magazine");
        int addChoice = scanner.nextInt();
        scanner.nextLine();

        if (addChoice == 1) {
            System.out.println(BLUE + "+ Add a Book selected +" + RESET);
            book.addDocument(scanner);
            System.out.println(GREEN + "+ Book added successfully +" + RESET);
        } else if (addChoice == 2) {
            System.out.println(BLUE + "+ Add a Magazine selected +" + RESET);
            magazine.addDocument(scanner);
            System.out.println(GREEN + "+ Magazine added successfully +" + RESET);
        } else {
            System.out.println(RED + "+ Error: Invalid choice. Please select a number between 1 and 2 +" + RESET);
        }

        handleMiniMenu(scanner);
    }

    private void handleDocumentStatusChange(String newStatus, String alreadyStatusMessage, String operationMessage) {
        System.out.println(operationMessage);
        System.out.print(BLUE + "+ " + RESET + "Enter the ID of the document: ");
        String id = scanner.nextLine();

        List<String> documents = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("documents.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                documents.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        boolean documentFound = false;
        boolean statusChanged = false;

        for (int i = 0; i < documents.size(); i++) {
            String document = documents.get(i);
            if (document.contains("id: " + id)) {
                documentFound = true;

                if (document.contains("status: " + newStatus)) {
                    System.out.println(alreadyStatusMessage);
                    break;
                }

                String updatedDocument = document.replaceFirst("status: [A-Z]+", "status: " + newStatus);
                documents.set(i, updatedDocument);
                statusChanged = true;
                break;
            }
        }

        if (documentFound && statusChanged) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("documents.txt"))) {
                for (String document : documents) {
                    writer.write(document);
                    writer.newLine();
                }
                System.out.println("Document status updated successfully.");
            } catch (IOException e) {
                System.out.println("Error writing to the file: " + e.getMessage());
            }
        } else if (!documentFound) {
            System.out.println("No document found with the provided ID.");
        }

        handleMiniMenu(scanner);
    }

    private void handleDisplayDocuments() {
        System.out.println("Display Documents selected.");
        System.out.println(BLUE + "+ " + RESET + "Please select an option (1-2): ");
        System.out.println(BLUE + "1. " + RESET + "All documents");
        System.out.println(BLUE + "2. " + RESET + "Session documents");
        int disChoice = scanner.nextInt();
        scanner.nextLine();

        if (disChoice == 1) {
            System.out.println(BLUE + "+ All documents selected +" + RESET);
            try (BufferedReader reader = new BufferedReader(new FileReader("documents.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(BLUE + "+ " + RESET + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (disChoice == 2) {
            System.out.println("+ Session documents selected +");
            System.out.println(BLUE + "+ Books :" + RESET);
            book.displayDocuments();
            System.out.println(BLUE + "+ Magazines :" + RESET);
            magazine.displayDocuments();
        } else {
            System.out.println(RED + "+ Error: Invalid choice. Please select a number between 1 and 2 +" + RESET);
        }

        handleMiniMenu(scanner);
    }

    private void handleSearchDocument() {
        System.out.println("Search for a Document selected.");
        System.out.print(BLUE + "+ " + RESET + "Enter search term: ");
        boolean found = false;
        String search = scanner.nextLine();

        try (Scanner scan = new Scanner(new File("documents.txt"))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().toLowerCase();
                if (line.contains(search)) {
                    System.out.println(BLUE+"+"+RESET+line);
                    found = true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + e.getMessage());
        }
        if (!found) {
            System.out.println(RED + "+" + RESET + " No matching documents found.");
        }

        handleMiniMenu(scanner);
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
                    System.out.println(BLUE + "See you soon..." + RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED + "+ Error: Invalid choice. Please select 1 or 2 +" + RESET);
            }
        }
    }
}
