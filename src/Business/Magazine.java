package Business;

import Utils.Validation;

import java.util.ArrayList;
import java.util.Scanner;

public class Magazine extends Document{
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";

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
        System.out.println(BLUE + "+" + RESET + "Enter the number of the magazine: ");
        while (!scanner.hasNextInt()) {
            System.out.println(RED + "+ Invalid input." + RESET + " Please enter a valid number: ");
            scanner.next();
        }
        int number = scanner.nextInt();
        while (!validation.handleNumber(String.valueOf(number))) {
            System.out.println(RED + "+ Invalid number." + RESET + " Please enter a valid number: ");
            number = scanner.nextInt();
        }
        scanner.nextLine();

        System.out.println(BLUE + "+" + RESET + "Enter the title of the magazine: ");
        String title = scanner.nextLine();
        while (!validation.handleTitle(title)) {
            System.out.println(RED + "+ Invalid title." + RESET + " Please enter a valid title: ");
            title = scanner.nextLine();
        }

        System.out.println(BLUE + "+" + RESET + "Enter the author of the magazine: ");
        String author = scanner.nextLine();
        while (!validation.handleAuthor(author)) {
            System.out.println(RED + "+ Invalid author." + RESET + " Please enter a valid author: ");
            author = scanner.nextLine();
        }

        System.out.println(BLUE + "+" + RESET + "Enter the release date of the magazine (MM/DD/YYYY) :");
        String releaseDate = scanner.nextLine();
        while (!validation.handleDate(releaseDate)) {
            System.out.println(RED + "+ Invalid date." + RESET + " Please enter a valid date (MM/DD/YYYY): ");
            releaseDate = scanner.nextLine();
        }

        System.out.println(BLUE + "+" + RESET + "Enter the number of pages of the magazine: ");
        while (!scanner.hasNextInt()) {
            System.out.println(RED + "+ Invalid input." + RESET + " Please enter a valid number: ");
            scanner.next();
        }
        int pages = scanner.nextInt();
        while (!validation.handleNumber(String.valueOf(pages))) {
            System.out.println(RED + "+ Invalid number of pages." + RESET + " Please enter a valid number of pages: ");
            pages = scanner.nextInt();
        }
        scanner.nextLine();

        Magazine magazine = new Magazine(number, title, author, releaseDate, pages);
        magazines.add(magazine);

        System.out.print(BLUE+"+"+RESET+" Magazine : ");
        System.out.print(magazine.toString(true));
        System.out.println(BLUE+"+"+RESET);

        magazine.saveToFile(magazine.toString(false));
    }


    public String toString(boolean includeColors) {
        if (includeColors){
            return BLUE + "id: " + RESET + getId() +
                    BLUE + ", number: " + RESET + number +
                    BLUE + ", title: " + RESET + getTitle() +
                    BLUE + ", author: " + RESET + getAuthor() +
                    BLUE + ", releaseDate: " + RESET + getReleaseDate() +
                    BLUE + ", status: " + RESET + getStatus() +
                    BLUE + ", pages: " + RESET + getPages();
        }else{
            return "Magazine : { id: " + getId() +
                    ", number: " + number +
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

        if(magazines.isEmpty()){
            System.out.println(RED+"+"+RESET+" No magazines available.");
        }else {
            magazines.forEach(magazine -> {
                System.out.println(BLUE+"+"+RESET+" Magazine " + counter[0] + ": " + magazine.toString(true));
                counter[0]++;
            });
        }
    }

    @Override
    public void searchForDocument(String search) {
        final int[] counter = {1};
        magazines.forEach(magazine -> {
            if (magazine.getId().contains(search) || magazine.getTitle().contains(search) || magazine.getAuthor().contains(search) || magazine.getReleaseDate().contains(search)) {
                System.out.println(BLUE + "+" + RESET + " Magazine " + counter[0] + ": " + magazine.toString(true));
                counter[0]++;
            }
        });
    }
}
