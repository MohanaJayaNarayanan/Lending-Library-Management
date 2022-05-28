
package com.library.model;

import java.util.Date;

public class Book {

    private int bookID;
    private String title;
    private String subject;
    private String author;
    private boolean isIssued;
    static int currentIdNumber = 0;

    public Book(int bookID, String title, String subject, String author, boolean isIssued) {
        currentIdNumber++;

        if (bookID == -1)
            bookID = currentIdNumber;
        else
            bookID = bookID;

        this.bookID = bookID;
        this.title = title;
        this.subject = subject;
        this.author = author;
        this.isIssued = isIssued;
    }


    public void printInfo() {
        System.out.println(title + "\t\t\t" + author + "\t\t\t" + subject);
    }


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public static void setIDCount(int n)
    {
        currentIdNumber=n;
    }

    public void returnBook(Borrower borrower, LoanedBooks loanedBooks) {
        loanedBooks.getBook().setIssued(false);
        loanedBooks.setDateReturned(new Date());
        borrower.removeBorrowedBook(loanedBooks);
        loanedBooks.payFine();

        System.out.println("\nThe book " + loanedBooks.getBook().getTitle() + " is successfully returned by " + borrower.getName() + ".");
    }

}