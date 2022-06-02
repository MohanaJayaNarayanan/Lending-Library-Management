
package com.library.model;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

public class LoanedDVDs {
    private Borrower borrower;
    private DVD dvd;
    private Date issuedDate;
    private Date dateReturned;
    private boolean finePaid;

    public LoanedDVDs(Borrower borrower, DVD dvd, Date issuedDate, Date dateReturned, boolean finePaid) {
        this.borrower = borrower;
        this.dvd = dvd;
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

    public DVD getDvd() {
        return dvd;
    }

    public void setDvd(DVD dvd) {
        this.dvd = dvd;
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

    public double computeFine1() {

        double totalFine = 0;

        if (!finePaid) {
            Date iDate = issuedDate;
            Date rDate = new Date();

            long days = ChronoUnit.DAYS.between(rDate.toInstant(), iDate.toInstant());
            days = 0 - days;

            days = days - Library.getInstance().bookReturnDeadline;

            if (days > 0)
                totalFine = days * Library.getInstance().perDayFineForDVD;
            else
                totalFine = 0;
        }
        return totalFine;
    }


    public void payFine() {

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
