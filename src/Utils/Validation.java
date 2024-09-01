package Utils;

public class Validation {
    public static boolean handleDate(String date) {
        String regex = "^(0[1-9]|1[0-2])/(0[1-9]|1\\d|2\\d|3[01])/\\d{4}$";
        if (date.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean handleIsbn(String isbn) {
        String regex = "^(97(8|9))?[0-9]{9}([0-9]|X)$";
        if (isbn.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean handleNumber(String number) {
        String regex = "^[0-9]*$";
        if (number.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean handleTitle(String title) {
        String regex = "^[a-zA-Z0-9 ]*$";
        if (title.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean handleAuthor(String author) {
        String regex = "^[a-zA-Z ]*$";
        if (author.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean handlePages(String pages) {
        String regex = "^[0-9]*$";
        if (pages.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

}
