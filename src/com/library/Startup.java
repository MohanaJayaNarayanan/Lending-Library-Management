package com.library;

import com.library.model.Book;
import com.library.model.Borrower;
import com.library.model.Customer;
import com.library.model.Library;
import com.library.model.LoanedBooks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Startup {

    public static void clrscr()
    {
        for (int i = 0; i < 20; i++)
            System.out.println();
    }

    public static int takeInput(int min, int max)
    {
        String choice;
        Scanner input = new Scanner(System.in);

        while(true)
        {
            System.out.println("\nEnter Choice: ");
            choice = input.next();

            if((!choice.matches(".*[a-zA-Z]+.*")) && (Integer.parseInt(choice) > min && Integer.parseInt(choice) < max))
            {
                return Integer.parseInt(choice);
            }

            else
                System.out.println("\nInvalid Input.");
        }

    }


    public static void allFunctionalities(Customer customer, int choice) throws IOException
    {
        Library lib = Library.getInstance();

        Scanner scanner = new Scanner(System.in);
        int input = 0;

        //Search Book
        if (choice == 1)
        {
            lib.searchForBooks();
        }

        //Compute Fine of a Borrower
        else if (choice == 2)
        {
            if("Clerk".equals(customer.getClass().getSimpleName()) || "Librarian".equals(customer.getClass().getSimpleName()))
            {
                Borrower bor = lib.findBorrower();

                if(bor!=null)
                {
                    double totalFine = lib.computeFine2(bor);
                    System.out.println("\nYour Total Fine is : Rs " + totalFine );
                }
            }
            else
            {
                double totalFine = lib.computeFine2((Borrower)customer);
                System.out.println("\nYour Total Fine is : Rs " + totalFine );
            }
        }

        //Option for taking a new item from the library by the customer.
      /*  else if (choice == 3)
        {
            ArrayList<Book> books = lib.searchForBooks();

            if (books != null)
            {
                input = takeInput(-1,books.size());
                Book b = books.get(input);

                Borrower bor = lib.findBorrower();

                if(bor!=null)
                {
                    b.issueBook(bor, (Staff)customer);
                }
            }
        }*/

        //Return a Book
        else if (choice == 7)
        {
            Borrower borrower = lib.findBorrower();
            if(borrower!=null)
            {
                borrower.printBorrowedBooks();
                ArrayList<LoanedBooks> loans = borrower.getBorrowedBooks();

                if (!loans.isEmpty())
                {
                    input = takeInput(-1,loans.size());
                    LoanedBooks loanedBooks = loans.get(input);

                    loanedBooks.getBook().returnBook(borrower, loanedBooks);
                }
                else
                    System.out.println("\nThis borrower " + borrower.getName() + " has no book to return.");
            }
        }

        //Update Borrower's customeral Info
        else if (choice == 10)
        {
            Borrower bor = lib.findBorrower();

            if(bor != null)
                bor.updateBorrowerInfo();
        }

        //Add new Book
        else if (choice == 11)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\nEnter Title:");
            String title = reader.readLine();

            System.out.println("\nEnter Subject:");
            String subject = reader.readLine();

            System.out.println("\nEnter Author:");
            String author = reader.readLine();

            lib.createBook(title, subject, author);
        }


        // Functionality Performed.
        System.out.println("\nPress any key to continue..\n");
        scanner.next();
    }




    public static void main(String[] args)
    {
        Scanner admin = new Scanner(System.in);
        Library lib = Library.getInstance();

        // Setting some by default information like name of library ,fine, deadline and limit of hold request
        lib.setFine(20);
        lib.setRequestExpiry(7);
        lib.setReturnDeadline(5);
        lib.setName("Mohan's Library");

        // Making connection with Database.
        Connection con = lib.makeConnection();

        if (con == null)    // Oops can't connnect !
        {
            System.out.println("\nError connecting to Database. Exiting.");
            return;
        }

        try {
            // Populating Library with all Records
            lib.populateLibrary(con);

            boolean stop = false;
            while(!stop)
            {
                clrscr();

                System.out.println("--------------------------------------------------------");
                System.out.println("\tWelcome to Library Management System");
                System.out.println("--------------------------------------------------------");

                System.out.println("Following Functionalities are available: \n");
                System.out.println("1- Login");
                System.out.println("2- Exit");
                System.out.println("3- Admininstrative Functions"); // Administration has access only

                System.out.println("-----------------------------------------\n");

                int choice = 0;

                choice = takeInput(0,4);

                if (choice == 3)
                {
                    System.out.println("\nEnter Password: ");
                    String aPass = admin.next();

                    if(aPass.equals("root"))
                    {
                        while (true)    // Way to Admin Portal
                        {
                            clrscr();

                            System.out.println("--------------------------------------------------------");
                            System.out.println("\tWelcome to Admin's Portal");
                            System.out.println("--------------------------------------------------------");
                            System.out.println("Following Functionalities are available: \n");

                            System.out.println("1- Add Customer of borrower type");
                            System.out.println("2- View Issued Books History");
                            System.out.println("3- View All Books in Library");
                            System.out.println("4- Exit operations");

                            System.out.println("---------------------------------------------");

                            choice = takeInput(0,5);

                            if (choice == 4)
                                break;

                            else if (choice == 1)
                                lib.createCUSTOMER('B');

                            else if (choice == 4)
                                lib.viewAllBooks();

                            System.out.println("\nPress any key to continue..\n");
                            admin.next();
                        }
                    }
                    else
                        System.out.println("\nSorry! Wrong Password.");
                }

                else if (choice == 2)
                {
                    Customer customer = lib.login();

                    if (customer == null){}

                    else if (customer.getClass().getSimpleName().equals("Borrower"))
                    {
                        while (true)    // Way to Borrower's Portal
                        {
                            clrscr();

                            System.out.println("--------------------------------------------------------");
                            System.out.println("\tWelcome to Borrower's Portal");
                            System.out.println("--------------------------------------------------------");
                            System.out.println("Following Functionalities are available: \n");
                            System.out.println("1- Search a Book");
                            System.out.println("2- Place a Book on hold");
                            System.out.println("3- Check customeral Info of Borrower");
                            System.out.println("4- Check Total Fine of Borrower");
                            System.out.println("5- Check Hold Requests Queue of a Book");
                            System.out.println("6- Logout");
                            System.out.println("--------------------------------------------------------");

                            choice = takeInput(0,7);

                            if (choice == 6)
                                break;

                            allFunctionalities(customer,choice);
                        }
                    }

                    else if (customer.getClass().getSimpleName().equals("Clerk"))
                    {
                        while(true) // Way to Clerk's Portal
                        {
                            clrscr();

                            System.out.println("--------------------------------------------------------");
                            System.out.println("\tWelcome to Clerk's Portal");
                            System.out.println("--------------------------------------------------------");
                            System.out.println("Following Functionalities are available: \n");
                            System.out.println("1- Search a Book");
                            System.out.println("2- Place a Book on hold");
                            System.out.println("3- Check customeral Info of Borrower");
                            System.out.println("4- Check Total Fine of Borrower");
                            System.out.println("5- Check Hold Requests Queue of a Book");
                            System.out.println("6- Check out a Book");
                            System.out.println("7- Check in a Book");
                            System.out.println("8- Renew a Book");
                            System.out.println("9- Add a new Borrower");
                            System.out.println("10- Update a Borrower's Info");
                            System.out.println("11- Logout");
                            System.out.println("--------------------------------------------------------");

                            choice = takeInput(0,12);

                            if (choice == 11)
                                break;

                            allFunctionalities(customer,choice);
                        }
                    }

                    else if (customer.getClass().getSimpleName().equals("Librarian"))
                    {
                        while(true) // Way to Librarian Portal
                        {
                            clrscr();

                            System.out.println("--------------------------------------------------------");
                            System.out.println("\tWelcome to Librarian's Portal");
                            System.out.println("--------------------------------------------------------");
                            System.out.println("Following Functionalities are available: \n");
                            System.out.println("1- Search a Book");
                            System.out.println("2- Place a Book on hold");
                            System.out.println("3- Check customeral Info of Borrower");
                            System.out.println("4- Check Total Fine of Borrower");
                            System.out.println("5- Check Hold Requests Queue of a Book");
                            System.out.println("6- Check out a Book");
                            System.out.println("7- Check in a Book");
                            System.out.println("8- Renew a Book");
                            System.out.println("9- Add a new Borrower");
                            System.out.println("10- Update a Borrower's Info");
                            System.out.println("11- Add new Book");
                            System.out.println("12- Remove a Book");
                            System.out.println("13- Change a Book's Info");
                            System.out.println("14- Check customeral Info of Clerk");
                            System.out.println("15- Logout");
                            System.out.println("--------------------------------------------------------");

                            choice = takeInput(0,16);

                            if (choice == 15)
                                break;

                            allFunctionalities(customer,choice);
                        }
                    }

                }

                else
                    stop = true;

                System.out.println("\nPress any key to continue..\n");
                Scanner scanner = new Scanner(System.in);
                scanner.next();
            }

            //Loading back all the records in database
            lib.fillItBack(con);
        }
        catch(Exception e)
        {
            System.out.println("\nExiting...\n");
        }

    }

}
