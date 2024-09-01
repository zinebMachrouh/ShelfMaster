package Business;
import Utils.Validation;

import java.util.ArrayList;

import java.util.Scanner;

public class Book extends Document{
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String MAGENTA = "\033[0;35m";
    public static final String PINK = "\033[38;5;13m";
    public static final String GREEN = "\u001b[92m";

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

        System.out.print("Book : {");
        System.out.print(book.toString());
        System.out.println("}");
    }

    @Override
    public String toString() {
        return "id='" + getId() + '\'' +
                ",isbn='" + isbn + '\'' +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", releaseDate='" + getReleaseDate() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", pages=" + getPages() ;
    }

    @Override
    public void displayDocuments() {
        final int[] counter = {1};
        if(books.isEmpty()){
            System.out.println("No books available.");
        }else{
            books.forEach(book -> {
                System.out.println("Book " + counter[0] + ": " + book.toString());
                counter[0]++;
            });
        }
    }
}
