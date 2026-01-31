package com.revhire.util;

import java.util.Scanner;

public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    private InputUtil() {
        // prevent object creation
    }

    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static int readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public static double readDouble(String message) {
        System.out.print(message);
        return Double.parseDouble(scanner.nextLine());
    }
}
