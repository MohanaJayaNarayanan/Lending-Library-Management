package com.library.model;

import java.util.Date;

public class DVD {

    private int dvdID;           // ID given by a library to a book to make it distinguishable from other books
    private String title;         // Title of a book
    private String subject;       // Subject to which a book is related!
    private String author;        // Author of book!
    private boolean isIssued;        // this will be true if the book is currently issued to some borrower.
    // private HoldRequestOperations holdRequestsOperations =new HoldRequestOperations();
    static int currentIdNumber = 0;     //This will be unique for every book, since it will be incremented when everytime
    //when a book is created

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

    // printing book's Info
    public void printInfo()
    {
        System.out.println(title + "\t\t\t" + author + "\t\t\t" + subject);
    }



    /*------------Getter FUNCs.---------*/

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



    // Setter Static Func.
    public static void setIDCount(int n)
    {
        currentIdNumber = n;
    }

    // Returning a Book
    public void returnDVD(Borrower borrower, LoanedBooks loanedBooks)
    {
        loanedBooks.getBook().setIssuedStatus(false);
        loanedBooks.setDateReturned(new Date());
        borrower.removeBorrowedBook(loanedBooks);
        loanedBooks.payFine();

        System.out.println("\nThe book " + loanedBooks.getBook().getTitle() + " is successfully returned by " + borrower.getName() + ".");
    }
}
