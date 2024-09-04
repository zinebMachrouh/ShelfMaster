package Business;

import Utils.Validation;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class Book extends Document {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";

    private String isbn;
    private static final String DOCUMENT_FILE = "documents.txt";
    private final List<Book> books = new ArrayList<>();
    private final Validation validation = new Validation();

    public Book(String isbn, String title, String author, String releaseDate, int pages) {
        super(title, author, releaseDate, pages);
        this.isbn = isbn;
    }

    public Book() {
        super("", "", "", 0);
        this.isbn = "";
    }

    public void addDocument(Scanner scanner) {
        System.out.println(BLUE + "+" + RESET + "Enter the ISBN of the book (10 or 13 characters): ");
        String isbn = getValidInput(scanner, Validation::handleIsbn, "ISBN");

        System.out.println(BLUE + "+" + RESET + "Enter the title of the book: ");
        String title = getValidInput(scanner, Validation::handleTitle, "title");

        System.out.println(BLUE + "+" + RESET + "Enter the author of the book: ");
        String author = getValidInput(scanner, Validation::handleAuthor, "author");

        System.out.println(BLUE + "+" + RESET + "Enter the release date of the book (MM/DD/YYYY):");
        String releaseDate = getValidInput(scanner, Validation::handleDate, "date");

        System.out.println(BLUE + "+" + RESET + "Enter the number of pages of the book: ");
        int pages = getValidNumber(scanner);

        Book book = new Book(isbn, title, author, releaseDate, pages);
        books.add(book);

        System.out.print(BLUE + "+" + RESET + " Book : ");
        System.out.print(book.toString(true));
        System.out.println(BLUE + "+" + RESET);

        book.saveToFile(book.toString(false));
    }

    @Override
    public void modifyDocument(Scanner scanner) {

        System.out.println(BLUE + "+" + RESET + "Enter the ID of the book you want to modify: ");
        String id = scanner.nextLine();

        List<String> documents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DOCUMENT_FILE))) {
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
            if (document.contains("id: " + id)) {
                documentFound = true;

                System.out.println(BLUE + "+" + RESET + "Enter the new ISBN of the book: ");
                String isbn = getValidInput(scanner, Validation::handleIsbn, "ISBN");

                System.out.println(BLUE + "+" + RESET + "Enter the new title of the book: ");
                String title = getValidInput(scanner, Validation::handleTitle, "title");

                System.out.println(BLUE + "+" + RESET + "Enter the new author of the book: ");
                String author = getValidInput(scanner, Validation::handleAuthor, "author");

                System.out.println(BLUE + "+" + RESET + "Enter the new release date of the book (MM/DD/YYYY):");
                String releaseDate = getValidInput(scanner, Validation::handleDate, "date");

                System.out.println(BLUE + "+" + RESET + "Enter the new number of pages of the book: ");
                int pages = getValidNumber(scanner);

                String updatedDocument = updateBookFields(document, isbn, title, author, releaseDate, pages);
                documents.set(i, updatedDocument);
                break;
            }
        }

        if (!documentFound) {
            System.out.println(RED + "+" + RESET + "No book found with the ID: " + id);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCUMENT_FILE))) {
            for (String document : documents) {
                writer.write(document);
                writer.newLine();
            }
            System.out.println(BLUE + "+" + RESET + "Document updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    @Override
    public void deleteDocument(Scanner scanner) {

        System.out.println(BLUE + "+" + RESET + "Enter the ID of the book you want to delete: ");
        String idToDelete = scanner.nextLine();

        List<String> documents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DOCUMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                documents.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        boolean bookDeleted = false;
        for (int i = 0; i < documents.size(); i++) {
            String document = documents.get(i);
            if (document.contains("id: " + idToDelete)) {
                documents.remove(i);
                bookDeleted = true;
                break;
            }
        }

        if (bookDeleted) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCUMENT_FILE))) {
                for (String document : documents) {
                    writer.write(document);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to the file: " + e.getMessage());
            }
            System.out.println(BLUE + "+" + RESET + "Book with ID '" + idToDelete + "' has been deleted.");
        } else {
            System.out.println(RED + "+" + RESET + "No book found with the ID: " + idToDelete);
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
            scanner.nextLine(); // Consume newline
            if (Validation.handleNumber(String.valueOf(number))) {
                break;
            }
            System.out.println(RED + "+ Invalid number." + RESET + " Please enter a valid number: ");
        }
        return number;
    }

    private String getValidInput(Scanner scanner, Predicate<String> validator, String inputType) {
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

    private String updateBookFields(String document, String isbn, String title, String author, String releaseDate, int pages) {
        document = document.replaceFirst("isbn: [^,]+", "isbn: " + isbn);
        document = document.replaceFirst("title: [^,]+", "title: " + title);
        document = document.replaceFirst("author: [^,]+", "author: " + author);
        document = document.replaceFirst("releaseDate: [^,]+", "releaseDate: " + releaseDate);
        document = document.replaceFirst("pages: [0-9]+", "pages: " + pages);
        return document;
    }

    @Override
    public void displayDocuments() {
        final int[] counter = {1};
        if (books.isEmpty()) {
            System.out.println(RED + "+" + RESET + " No books available.");
        } else {
            books.forEach(book -> {
                System.out.println(BLUE + "+" + RESET + " Book " + counter[0] + ": " + book.toString(true));
                counter[0]++;
            });
        }
    }

    public String toString(boolean includeColors) {
        if (includeColors) {
            return BLUE + "id: " + RESET + getId() +
                    BLUE + ", isbn: " + RESET + isbn +
                    BLUE + ", title: " + RESET + getTitle() +
                    BLUE + ", author: " + RESET + getAuthor() +
                    BLUE + ", releaseDate: " + RESET + getReleaseDate() +
                    BLUE + ", status: " + RESET + getStatus() +
                    BLUE + ", pages: " + RESET + getPages();
        } else {
            return "Book : { id: " + getId() +
                    ", isbn: " + isbn +
                    ", title: " + getTitle() +
                    ", author: " + getAuthor() +
                    ", releaseDate: " + getReleaseDate() +
                    ", status: " + getStatus() +
                    ", pages: " + getPages() + " }";
        }
    }

    public void saveToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOCUMENT_FILE, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
}
