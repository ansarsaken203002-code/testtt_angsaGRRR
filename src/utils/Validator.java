package utils;

public class Validator {

    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 50) {
            return false;
        }
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }

    public static boolean isValidPrice(double price) {
        return price > 0;
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    public static boolean isValidItemName(String name) {
        return name != null && name.length() >= 2 && name.length() <= 100;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}