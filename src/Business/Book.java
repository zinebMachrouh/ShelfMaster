package Business;
import Utils.Validation;

import java.util.ArrayList;

import java.util.Scanner;

public class Book extends Document{
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
        System.out.println("Enter the ISBN of the book: ");
        String isbn = scanner.nextLine();
        while(!validation.handleIsbn(isbn)){
            System.out.println("Invalid ISBN. Please enter a valid ISBN: ");
            isbn = scanner.nextLine();
        }

        System.out.println("Enter the title of the book: ");
        String title = scanner.nextLine();
        while(!validation.handleTitle(title)){
            System.out.println("Invalid title. Please enter a valid title: ");
            title = scanner.nextLine();
        }

        System.out.println("Enter the author of the book: ");
        String author = scanner.nextLine();
        while (!validation.handleAuthor(author)){
            System.out.println("Invalid author. Please enter a valid author: ");
            author = scanner.nextLine();
        }

        System.out.println("Enter the release date of the book (MM/DD/YYYY) :");
        String releaseDate = scanner.nextLine();
        while (!validation.handleDate(releaseDate)){
            System.out.println("Invalid date. Please enter a valid date (MM/DD/YYYY): ");
            releaseDate = scanner.nextLine();
        }

        System.out.println("Enter the number of pages of the book: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number: ");
            scanner.next();
        }
        int pages = scanner.nextInt();
        while (!validation.handlePages(String.valueOf(pages))){
            System.out.println("Invalid number of pages. Please enter a valid number of pages: ");
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
