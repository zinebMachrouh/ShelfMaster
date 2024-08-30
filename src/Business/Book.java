package Business;
import java.util.ArrayList;

import java.util.Scanner;

public class Book extends Document{
    private String isbn;
    public ArrayList<Book> books = new ArrayList<Book>();

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
        System.out.println("Enter the title of the book: ");
        String title = scanner.nextLine();
        System.out.println("Enter the author of the book: ");
        String author = scanner.nextLine();
        System.out.println("Enter the release date of the book: ");
        String releaseDate = scanner.nextLine();
        System.out.println("Enter the number of pages of the book: ");
        int pages = scanner.nextInt();
        scanner.nextLine();
        Book book = new Book(isbn, title, author, releaseDate, pages);
        books.add(book);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", releaseDate='" + getReleaseDate() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", pages=" + getPages() +
                '}';
    }

    @Override
    public void displayDocuments() {
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }
}
