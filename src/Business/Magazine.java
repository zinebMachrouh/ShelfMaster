package Business;

import Utils.Validation;

import java.io.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;


public class Magazine extends Document {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";

    private int number;
    public ArrayList<Magazine> magazines = new ArrayList<Magazine>();
    Validation validation = new Validation();

    public Magazine(int number, String title, String author, String releaseDate, int pages) {
        super(title, author, releaseDate, pages);
        this.number = number;
    }

    public Magazine() {
        super("", "", "", 0);
        this.number = 0;
    }

    public void addDocument(Scanner scanner) {
        System.out.println(BLUE + "+" + RESET + "Enter the number of the magazine: ");
        int number = getValidNumber(scanner);

        System.out.println(BLUE + "+" + RESET + "Enter the title of the magazine: ");
        String title = getValidInput(scanner, Validation::handleTitle, "title");

        System.out.println(BLUE + "+" + RESET + "Enter the author of the magazine: ");
        String author = getValidInput(scanner, Validation::handleAuthor, "author");

        System.out.println(BLUE + "+" + RESET + "Enter the release date of the magazine (MM/DD/YYYY): ");
        String releaseDate = getValidInput(scanner, Validation::handleDate, "date");

        System.out.println(BLUE + "+" + RESET + "Enter the number of pages of the magazine: ");
        int pages = getValidNumber(scanner);


        Magazine magazine = new Magazine(number, title, author, releaseDate, pages);
        magazines.add(magazine);

        System.out.print(BLUE + "+" + RESET + " Magazine : ");
        System.out.print(magazine.toString(true));
        System.out.println(BLUE + "+" + RESET);
        magazine.saveToFile(magazine.toString(false));
    }

    @Override
    public void modifyDocument(Scanner scanner) {
        System.out.println(BLUE + "+" + RESET + "Enter the ID of the magazine you want to modify: ");
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
        for (int i = 0; i < documents.size(); i++) {
            String document = documents.get(i);
            if (document.contains("id: " + id) && document.contains("Magazine")) {
                documentFound = true;


                System.out.println(BLUE + "+" + RESET + "Enter the new number of the magazine: ");
                while (!scanner.hasNextInt()) {
                    System.out.println(RED + "+ Invalid input." + RESET + " Please enter a valid number: ");
                    scanner.next();
                }
                int number = scanner.nextInt();
                while (!validation.handleNumber(String.valueOf(number))) {
                    System.out.println(RED + "+ Invalid number." + RESET + " Please enter a valid number: ");
                    number = scanner.nextInt();
                }
                scanner.nextLine();


                System.out.println(BLUE + "+" + RESET + "Enter the new title of the magazine: ");
                String title = getValidInput(scanner, Validation::handleTitle, "title");

                System.out.println(BLUE + "+" + RESET + "Enter the new author of the magazine: ");
                String author = getValidInput(scanner, Validation::handleAuthor, "author");

                System.out.println(BLUE + "+" + RESET + "Enter the new release date of the magazine (MM/DD/YYYY):");
                String releaseDate = getValidInput(scanner, Validation::handleDate, "date");

                System.out.println(BLUE + "+" + RESET + "Enter the new number of pages of the magazine: ");
                int pages = getValidNumber(scanner);


                String updatedDocument = updateMagazineFields(document, number, title, author, releaseDate, pages);
                documents.set(i, updatedDocument);
                break;
            }
        }

        if (!documentFound) {
            System.out.println(RED + "+" + RESET + "No magazine found with the ID: " + id);
            return;
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("documents.txt"))) {
            for (String document : documents) {
                writer.write(document);
                writer.newLine();
            }
            System.out.println(BLUE + "+" + RESET + "Document updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
    private int getValidNumber(Scanner scanner) {
        int number = 0;
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println(RED + "+ Invalid input." + RESET + " Please enter a valid number: ");
                scanner.next();
            }
            number = scanner.nextInt();
            scanner.nextLine();
            if (Validation.handleNumber(String.valueOf(number))) {
                break;
            }
            System.out.println(RED + "+ Invalid number." + RESET + " Please enter a valid number: ");
        }
        return number;
    }

    private String  getValidInput(Scanner scanner, Predicate<String> validator, String inputType) {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (validator.test(input)) {
                break;
            }
            System.out.println(RED + "+ Invalid " + inputType + "." + RESET + " Please enter a valid " + inputType + ": ");
        }
        return input;
    }

    private String updateMagazineFields(String document, int number, String title, String author, String releaseDate, int pages) {
        if (number != 0) {
            document = document.replaceFirst("number: [0-9]+", "number: " + number);
        }
        if (!title.isEmpty()) {
            document = document.replaceFirst("title: [^,]+", "title: " + title);
        }
        if (!author.isEmpty()) {
            document = document.replaceFirst("author: [^,]+", "author: " + author);
        }
        if (!releaseDate.isEmpty()) {
            document = document.replaceFirst("releaseDate: [^,]+", "releaseDate: " + releaseDate);
        }
        if (pages != 0) {
            document = document.replaceFirst("pages: [0-9]+", "pages: " + pages);
        }
        return document;
    }

    @Override
    public void deleteDocument(Scanner scanner) {
        System.out.println(BLUE + "+" + RESET + "Enter the ID of the magazine you want to delete: ");
        String idToDelete = getValidInput(scanner, Validation::handleTitle, "id");

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

        boolean magazineDeleted = false;

        for (int i = 0; i < documents.size(); i++) {
            String document = documents.get(i);
            if (document.contains("id: " + idToDelete) && document.contains("Magazine")) {
                documents.remove(i);
                magazineDeleted = true;
                break;
            }
        }

        /*while (iterator.hasNext()) {
            String document = iterator.next();
            System.out.println("Magazine with ID .");
            if (document.matches("^id: " + idToDelete + ",.*")) {
                iterator.remove();
                magazineDeleted = true;
                break;
            }
        }*/

        if (magazineDeleted) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("documents.txt"))) {
                for (String document : documents) {
                    writer.write(document);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to the file: " + e.getMessage());
            }
            System.out.println(BLUE + "+" + RESET + " Magazine with ID '" + idToDelete + "' has been deleted.");
        } else {
            System.out.println(RED + "+" + RESET + " No magazine found with the ID: " + idToDelete);
        }
    }


    public String toString(boolean includeColors) {
        if (includeColors) {
            return BLUE + "id: " + RESET + getId() +
                    BLUE + ", number: " + RESET + number +
                    BLUE + ", title: " + RESET + getTitle() +
                    BLUE + ", author: " + RESET + getAuthor() +
                    BLUE + ", releaseDate: " + RESET + getReleaseDate() +
                    BLUE + ", status: " + RESET + getStatus() +
                    BLUE + ", pages: " + RESET + getPages();
        } else {
            return "Magazine : { id: " + getId() +
                    ", number: " + number +
                    ", title: " + getTitle() +
                    ", author: " + getAuthor() +
                    ", releaseDate: " + getReleaseDate() +
                    ", status: " + getStatus() +
                    ", pages: " + getPages() + " }";
        }
    }


    @Override
    public void displayDocuments() {
        final int[] counter = {1};

        if (magazines.isEmpty()) {
            System.out.println(RED + "+" + RESET + " No magazines available.");
        } else {
            magazines.forEach(magazine -> {
                System.out.println(BLUE + "+" + RESET + " Magazine " + counter[0] + ": " + magazine.toString(true));
                counter[0]++;
            });
        }
    }

}