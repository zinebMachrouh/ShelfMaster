package Business;
import Utils.Validation;

import java.util.ArrayList;

import java.util.Scanner;

public class Book extends Document{
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";


    private String isbn;
    public ArrayList<Book> books = new ArrayList<Book>();
    Validation validation = new Validation();

    public Book(String isbn, String title, String author, String releaseDate, int pages){
        super(title, author, releaseDate, pages);
        this.isbn = isbn;
    }
    public Book() {
        super("", "", "", 0);
        this.isbn = "";
    }
    public void addBook(Scanner scanner){
        System.out.println(BLUE+"+"+RESET+"Enter the ISBN of the book (10 or 13 characters): ");
        String isbn = scanner.nextLine();
        while(!validation.handleIsbn(isbn)){
            System.out.println(RED+"+ Invalid ISBN."+RESET+" Please enter a valid ISBN: ");
            isbn = scanner.nextLine();
        }

        System.out.println(BLUE+"+"+RESET+"Enter the title of the book: ");
        String title = scanner.nextLine();
        while(!validation.handleTitle(title)){
            System.out.println(RED+"+ Invalid title."+RESET+" Please enter a valid title: ");
            title = scanner.nextLine();
        }

        System.out.println(BLUE+"+"+RESET+"Enter the author of the book: ");
        String author = scanner.nextLine();
        while (!validation.handleAuthor(author)){
            System.out.println(RED+"+ Invalid author."+RESET+" Please enter a valid author: ");
            author = scanner.nextLine();
        }

        System.out.println(BLUE+"+"+RESET+"Enter the release date of the book (MM/DD/YYYY) :");
        String releaseDate = scanner.nextLine();
        while (!validation.handleDate(releaseDate)){
            System.out.println(RED+"+ Invalid date."+RESET+" Please enter a valid date (MM/DD/YYYY): ");
            releaseDate = scanner.nextLine();
        }

        System.out.println(BLUE+"+"+RESET+"Enter the number of pages of the book: ");
        while (!scanner.hasNextInt()) {
            System.out.println(RED+"+ Invalid input."+RESET+" Please enter a valid number: ");
            scanner.next();
        }
        int pages = scanner.nextInt();
        while (!validation.handleNumber(String.valueOf(pages))){
            System.out.println(RED+"+ Invalid number of pages."+RESET+" Please enter a valid number of pages: ");
            pages = scanner.nextInt();
        }
        scanner.nextLine();
        Book book = new Book(isbn, title, author, releaseDate, pages);
        books.add(book);

        System.out.print(BLUE+"+"+RESET+" Book : ");
        System.out.print(book.toString(true));
        System.out.println(BLUE+"+"+RESET);

        book.saveToFile(book.toString(false));
    }

    public String toString(boolean includeColors) {
        if (includeColors){
            return BLUE + "id: " + RESET + getId() +
                    BLUE + ", isbn: " + RESET + isbn +
                    BLUE + ", title: " + RESET + getTitle() +
                    BLUE + ", author: " + RESET + getAuthor() +
                    BLUE + ", releaseDate: " + RESET + getReleaseDate() +
                    BLUE + ", status: " + RESET + getStatus() +
                    BLUE + ", pages: " + RESET + getPages();
        }else{
            return "Book : { id: " + getId() +
                    ", isbn: " + isbn +
                    ", title: " + getTitle() +
                    ", author: " + getAuthor() +
                    ", releaseDate: " + getReleaseDate() +
                    ", status: " + getStatus() +
                    ", pages: " + getPages()+" }";
        }
    }

    @Override
    public void displayDocuments() {
        final int[] counter = {1};
        if(books.isEmpty()){
            System.out.println(RED+"+"+RESET+" No books available.");
        }else{
            books.forEach(book -> {
                System.out.println(BLUE+"+"+RESET+" Book " + counter[0] + ": " + book.toString(true));
                counter[0]++;
            });
        }
    }

   @Override
   public void searchForDocument(String search) {
        final int[] counter = {1};
        if(books.isEmpty()){
            System.out.println(RED+"+"+RESET+" No books available.");
        }else{
            books.forEach(book -> {
                if(book.getId().contains(search) || book.isbn.equals(search) || book.getTitle().contains(search) || book.getAuthor().contains(search) || book.getReleaseDate().contains(search)){
                    System.out.println(BLUE+"+"+RESET+" Book " + counter[0] + ": " + book.toString(true));
                    counter[0]++;
                }
            });
        }
    }
}
