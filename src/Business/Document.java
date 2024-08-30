package Business;
import java.util.UUID;

abstract class Document {
    private String id;
    private String title;
    private String author;
    private String releaseDate;
    private Status status;
    private int pages;

    public enum Status {
        AVAILABLE,
        BORROWED
    }

    Document(String title, String author, String releaseDate, int pages) {
        this.id = generateUuid();;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.pages = pages;
        this.status = Status.AVAILABLE;
    }
    private String generateUuid() {
        return UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public int getPages() {
        return pages;
    }
    public Status getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    //public abstract void addDocument();
    //public abstract void borrowDocument();
    //public abstract void returnDocument();
    //public abstract void displayDocuments();
    //public abstract void searchForDocument();
}
