package com.library;

import com.library.model.Borrower;
import com.library.model.Library;
import com.library.model.LoanedBooks;
import com.library.utils.InputUtils;
import java.io.IOException;
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

        //Display list of overdue items
        else if (choice == 3) {
            fetchOverDueItems(lib);
        }

        //Display list of items available for the books
        else if (choice == 4) {
           int count = lib.searchForBooks();
            System.out.println("Number of books found for the given book search criteria :: "+count);
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

        // Functionality Performed.
        System.out.println("\nPress any key to continue..\n");
        scanner.next();
    }

    private static void fetchOverDueItems(Library lib) {
        Borrower borrower = lib.findBorrower();
        int input = 0;

        if (borrower != null) {
            borrower.printBorrowedBooks();
            ArrayList<LoanedBooks> loans = borrower.getBorrowedBooks();

            if(!loans.isEmpty())
            {
                LoanedBooks loanedBooks = loans.get(input);
                loanedBooks.setIssuedDate(loanedBooks.getIssuedDate());
               double fine = loanedBooks.computeFine1();
                System.out.println("Fine to be payable for the item "+loanedBooks +" is :: "+fine);
            }

            if (!loans.isEmpty()) {
                input = InputUtils.takeInput(-1, loans.size());
                LoanedBooks loanedBooks = loans.get(input);
                loanedBooks.getBook().returnBook(borrower, loanedBooks);
            } else
                System.out.println("\nThis borrower " + borrower.getName() + " has no book to return.");
        }
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
    }

}
