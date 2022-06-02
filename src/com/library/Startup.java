package com.library;

import com.library.model.Library;
import com.library.utils.InputUtils;

import java.sql.Connection;
import java.util.Scanner;

public class Startup {

    public static void main(String[] args) {
        Scanner admin = new Scanner(System.in);
        Library lib = Library.getInstance();

        lib.setFine(20);
        lib.setRequestExpiry(7);
        lib.setReturnDeadline(5);
        lib.setName("Mohan's Library");

        // Making connection with Database.
        Connection con = lib.makeConnection();

        if (con == null)
        {
            System.out.println("\nError connecting to Database. Exiting.");
            return;
        }

        try {
            // Populating Library with all Records
            lib.populateLibrary(con);

            boolean stop = false;
            while (!stop) {
                InputUtils.clrscr();

                System.out.println("--------------------------------------------------------");
                System.out.println("\tWelcome to Library Management System");
                System.out.println("--------------------------------------------------------");

                System.out.println("Following Functionalities are available: \n");
                System.out.println("1- Create Customer");
                System.out.println("2- Customer Functions");
                System.out.println("3- Exit"); // Administration has access only

                System.out.println("-----------------------------------------\n");

                int choice = 0;

                choice = InputUtils.takeInput(0, 4);

                if (choice == 1) {
                    System.out.println("\nEnter Password: ");
                    String aPass = admin.next();

                    if (aPass.equals("root")) {
                        while (true)    // Way to Admin Portal
                        {
                            InputUtils.clrscr();
                                lib.createCUSTOMER('B');

                            System.out.println("\nPress any key to continue..\n");
                        }
                    } else
                        System.out.println("\nSorry! Wrong Password.");
                } else if (choice == 2) {
                    while (true) {
                        InputUtils.clrscr();

                        System.out.println("--------------------------------------------------------");
                        System.out.println("\tWelcome to Library's Function Portal");
                        System.out.println("--------------------------------------------------------");
                        System.out.println("Following Functionalities are available: \n");
                        System.out.println("1- Track the customer");
                        System.out.println("2- Display list of items customer has rented so far");
                        System.out.println("3- Display list of overdue items for a customer and total amount due to be paid by the customer");
                        System.out.println("4- Check no. of items available in the library for a given item using item id");
                        System.out.println("5- Option for taking a new item from the library by the customer");
                        System.out.println("6- Option for returning an item back to the library");
                        System.out.println("7- Logout");
                        System.out.println("--------------------------------------------------------");

                        choice = InputUtils.takeInput(0, 8);

                        if (choice == 7)
                            break;

                        CustomerFunctions.allFunctionalities(choice);
                    }
                } else
                    stop = true;

                System.out.println("\nPress any key to continue..\n");
                Scanner scanner = new Scanner(System.in);
                scanner.next();
            }

            //Loading back all the records in database
            // lib.fillItBack(con);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nExiting...\n");
        }

    }

}
