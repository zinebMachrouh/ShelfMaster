package Business;

public class Book extends Document{
    private String isbn;

    Book(String isbn, String title, String author, String releaseDate, int pages){
        super(title, author, releaseDate, pages);
        this.isbn = isbn;
    }

    public void addBook(){
        
    }
}
