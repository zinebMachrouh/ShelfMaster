package Business;

import Utils.Validation;

import java.util.ArrayList;
import java.util.Scanner;

public class Magazine extends Document{
    private int number;
    public ArrayList<Magazine> magazines = new ArrayList<Magazine>();
    Validation validation = new Validation();

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
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number: ");
            scanner.next();
        }
        int number = scanner.nextInt();
        while (!validation.handleNumber(String.valueOf(number))) {
            System.out.println("Invalid number. Please enter a valid number: ");
            number = scanner.nextInt();
        }

        scanner.nextLine();

        System.out.println("Enter the title of the magazine: ");
        String title = scanner.nextLine();
        while (!validation.handleTitle(title)){
            System.out.println("Invalid title. Please enter a valid title: ");
            title = scanner.nextLine();
        }

        System.out.println("Enter the author of the magazine: ");
        String author = scanner.nextLine();
        while (!validation.handleAuthor(author)){
            System.out.println("Invalid author. Please enter a valid author: ");
            author = scanner.nextLine();
        }

        System.out.println("Enter the release date of the magazine (MM/DD/YYYY) :");
        String releaseDate = scanner.nextLine();
        while (!validation.handleDate(releaseDate)){
            System.out.println("Invalid date. Please enter a valid date (MM/DD/YYYY): ");
            releaseDate = scanner.nextLine();
        }

        System.out.println("Enter the number of pages of the magazine: ");
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

        Magazine magazine = new Magazine(number, title, author, releaseDate, pages);
        magazines.add(magazine);

        System.out.print("Magazine : {");
        System.out.print(magazine.toString());
        System.out.println("}");
    }

    @Override
    public String toString() {
        return "id='" + getId() + '\'' +
                ", number='" + number + '\'' +
                ", title='" + getTitle() + '\'' +
                ", author='" + getAuthor() + '\'' +
                ", releaseDate='" + getReleaseDate() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", pages=" + getPages() ;
    }
    @Override
    public void displayDocuments() {
        final int[] counter = {1};

        if(magazines.isEmpty()){
            System.out.println("No magazines available.");
        }else {
            magazines.forEach(magazine -> {
                System.out.println("Magazine " + counter[0] + ": " + magazine.toString());
                counter[0]++;
            });
        }
    }
}
