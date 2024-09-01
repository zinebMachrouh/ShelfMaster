package Utils;

import java.util.Scanner;

public class Validation {
    public static boolean handleDate(String date) {
        String regex = "^(0[1-9]|1[0-2])/(0[1-9]|1\\d|2\\d|3[01])/\\d{4}$";
        if (date.matches(regex)) {
            Scanner scanner = new Scanner(date).useDelimiter("/");

            int month = scanner.nextInt();
            int day = scanner.nextInt();
            int year = scanner.nextInt();

            boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

            if(month == 2 && day > 28 && !isLeapYear){
                return false;
            } else if (month == 2 && day > 29 && isLeapYear) {
               return false;
            } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30){
                return false;
            } else {
                return true;
            }
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
        String regex = "^[a-zA-Z0-9 !,.?-]*$";
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
}
