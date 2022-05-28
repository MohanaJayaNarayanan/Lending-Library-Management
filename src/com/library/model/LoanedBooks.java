
package com.library.model;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

public class LoanedBooks {
    private Borrower borrower;
    private Book book;
    private Date issuedDate;
    private Date dateReturned;
    private boolean finePaid;

    public LoanedBooks(Borrower borrower, Book book, Date issuedDate, Date dateReturned, boolean finePaid) {
        this.borrower = borrower;
        this.book = book;
        this.issuedDate = issuedDate;
        this.dateReturned = dateReturned;
        this.finePaid = finePaid;
    }


    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public Date getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
        this.dateReturned = dateReturned;
    }

    public boolean isFinePaid() {
        return finePaid;
    }

    public void setFinePaid(boolean finePaid) {
        this.finePaid = finePaid;
    }

    //Computes fine for a particular loan only
    public double computeFine1() {

        //-----------Computing Fine-----------        
        double totalFine = 0;

        if (!finePaid) {
            Date iDate = issuedDate;
            Date rDate = new Date();

            long days = ChronoUnit.DAYS.between(rDate.toInstant(), iDate.toInstant());
            days = 0 - days;

            days = days - Library.getInstance().book_return_deadline;

            if (days > 0)
                totalFine = days * Library.getInstance().per_day_fine;
            else
                totalFine = 0;
        }
        return totalFine;
    }


    public void payFine() {
        //-----------Computing Fine-----------//

        double totalFine = computeFine1();

        if (totalFine > 0) {
            System.out.println("\nTotal Fine generated: Rs " + totalFine);

            System.out.println("Do you want to pay? (y/n)");

            Scanner input = new Scanner(System.in);

            String choice = input.next();

            if (choice.equals("y") || choice.equals("Y"))
                finePaid = true;

            if (choice.equals("n") || choice.equals("N"))
                finePaid = false;
        } else {
            System.out.println("\nNo fine is generated.");
            finePaid = true;
        }
    }

}
