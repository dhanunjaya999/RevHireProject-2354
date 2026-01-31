package com.revhire.util;

public class ValidationUtil {

    private ValidationUtil() {
        // prevent object creation
    }

    public static boolean isValidGmail(String email) {
        return email != null &&
               email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$");
    }


    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }

    public static boolean isValidExperience(int years) {
        return years >= 0 && years <= 50;
    }
}
