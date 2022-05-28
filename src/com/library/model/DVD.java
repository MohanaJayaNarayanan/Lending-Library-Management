package com.library.model;

import java.util.Date;

public class DVD {

    private int dvdID;
    private String title;
    private String subject;
    private String author;
    private boolean isIssued;
    static int currentIdNumber = 0;


    public DVD(int dvdID, String title, String subject, String author, boolean isIssued) {
        currentIdNumber++;

        if(dvdID==-1)
            dvdID = currentIdNumber;
        else
            dvdID=dvdID;

        this.dvdID = dvdID;
        this.title = title;
        this.subject = subject;
        this.author = author;
        this.isIssued = isIssued;
    }


    public void printInfo()
    {
        System.out.println(title + "\t\t\t" + author + "\t\t\t" + subject);
    }


    public String getTitle()
    {
        return title;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getAuthor()
    {
        return author;
    }

    public boolean getIssuedStatus()
    {
        return isIssued;
    }

    public void setIssuedStatus(boolean s)
    {
        isIssued = s;
    }

    public static void setIDCount(int n)
    {
        currentIdNumber = n;
    }


    public void returnDVD(Borrower borrower, LoanedBooks loanedBooks)
    {
        loanedBooks.getBook().setIssued(false);
        loanedBooks.setDateReturned(new Date());
        borrower.removeBorrowedBook(loanedBooks);
        loanedBooks.payFine();

        System.out.println("\nThe book " + loanedBooks.getBook().getTitle() + " is successfully returned by " + borrower.getName() + ".");
    }
}
