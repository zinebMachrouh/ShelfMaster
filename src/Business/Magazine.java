package Business;

public class Magazine extends Document{
    private int number;

    Magazine(int number, String title, String author, String releaseDate, int pages){
        super(title, author, releaseDate, pages);
        this.number = number;
    }
}
