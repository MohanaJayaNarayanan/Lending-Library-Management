package com.library.utils;

import java.util.Scanner;

public class InputUtils {

    public static void clrscr() {
        for (int i = 0; i < 10; i++)
            System.out.println();
    }


    public static int takeInput(int min, int max) {
        String choice;
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter Choice: ");
            choice = input.next();

            if ((!choice.matches(".*[a-zA-Z]+.*")) && (Integer.parseInt(choice) > min && Integer.parseInt(choice) < max)) {
                return Integer.parseInt(choice);
            } else
                System.out.println("\nInvalid Input.");
        }

    }
}
