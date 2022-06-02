package com.library;

import com.library.model.Borrower;
import com.library.model.Library;
import com.library.model.LoanedBooks;
import com.library.model.LoanedDVDs;
import com.library.utils.InputUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerFunctions {

    public static void allFunctionalities(int choice) throws IOException {
        Library lib = Library.getInstance();

        Scanner scanner = new Scanner(System.in);
        int input = 0;

        //TrackCustomer
        if (choice == 1) {
            trackCustomer(lib);
        }

        //Display list of items customer has rented so far
        else if (choice == 2) {
            trackCustomerLoanLists(lib);
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
        else if (choice == 6) {

        }

        //Return a Book
        else if (choice == 7) {
            Borrower borrower = lib.findBorrower();
            if (borrower != null) {
                borrower.printBorrowedBooks();
                ArrayList<LoanedBooks> loans = borrower.getBorrowedBooks();

                if (!loans.isEmpty()) {
                    input = InputUtils.takeInput(-1, loans.size());
                    LoanedBooks loanedBooks = loans.get(input);

                    loanedBooks.getBook().returnBook(borrower, loanedBooks);
                } else
                    System.out.println("\nThis borrower " + borrower.getName() + " has no book to return.");
            }
        }

        //Update Borrower's customeral Info
        else if (choice == 10) {
            Borrower bor = lib.findBorrower();

            if (bor != null)
                bor.updateBorrowerInfo();
        }

        //Add new Book
        else if (choice == 11) {
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

    private static void trackCustomer(Library lib) {

        Borrower borrower = lib.findBorrower();
        int input = 0;

        if (borrower != null) {
            borrower.printBorrowedBooks();
            ArrayList<LoanedBooks> loans = borrower.getBorrowedBooks();

            if (!loans.isEmpty()) {
                input = InputUtils.takeInput(-1, loans.size());
                LoanedBooks loanedBooks = loans.get(input);
                loanedBooks.getBook().returnBook(borrower, loanedBooks);
            } else
                System.out.println("\nThis borrower " + borrower.getName() + " has no book to return.");
        }
    }


    private static void trackCustomerLoanLists(Library lib) {

        Borrower borrower = lib.findBorrower();
        int input = 0;

        if (borrower != null) {
            borrower.printBorrowedBooks();
            borrower.printBorrowedDVDs();
        }
         /*   ArrayList<LoanedBooks> loans = borrower.getBorrowedBooks();
            ArrayList<LoanedDVDs> dvds = borrower.getBorrowedDVDs();

            if (!loans.isEmpty()) {
                input = InputUtils.takeInput(-1, loans.size());
                LoanedBooks loanedBooks = loans.get(input);
                loanedBooks.getBook().returnBook(borrower, loanedBooks);
            } else
                System.out.println("\nThis borrower " + borrower.getName() + " has no book to return.");

            if (!dvds.isEmpty()) {
                input = InputUtils.takeInput(-1, dvds.size());
                LoanedDVDs loanedDVDs = dvds.get(input);
                loanedDVDs.getDvd().returnDVD(borrower, loanedDVDs);
            } else
                System.out.println("\nThis borrower " + borrower.getName() + " has no DVD to return.");
        }*/
    }

}
