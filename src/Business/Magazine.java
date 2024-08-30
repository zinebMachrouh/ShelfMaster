package Business;

import java.util.ArrayList;
import java.util.Scanner;

public class Magazine extends Document{
    private int number;
    public ArrayList<Magazine> magazines = new ArrayList<Magazine>();

    public Magazine(int number, String title, String author, String releaseDate, int pages){
        super(title, author, releaseDate, pages);
        this.number = number;
    }
    public Magazine() {
        super("", "", "", 0);
        this.number = 0;
    }

    public void addMagazine(Scanner scanner){
        System.out.println("Enter the number of the magazine: ");
        int number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the title of the magazine: ");
        String title = scanner.nextLine();
        System.out.println("Enter the author of the magazine: ");
        String author = scanner.nextLine();
        System.out.println("Enter the release date of the magazine: ");
        String releaseDate = scanner.nextLine();
        System.out.println("Enter the number of pages of the magazine: ");
        int pages = scanner.nextInt();
        scanner.nextLine();
        Magazine magazine = new Magazine(number, title, author, releaseDate, pages);
        magazines.add(magazine);
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "number='" + number + '\'' +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", releaseDate='" + getReleaseDate() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", pages=" + getPages() +
                '}';
    }
    @Override
    public void displayDocuments() {
        for (Magazine magazine : magazines) {
            System.out.println(magazine.toString());
        }
    }
}
