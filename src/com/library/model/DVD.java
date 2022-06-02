package com.library.model;

import java.util.Date;

public class DVD {

    private int dvdID;
    private String dvdName;
    private String subject;
    private String author;
    private boolean isIssued;
    static int currentIdNumber = 0;


    public DVD(int dvdID, String dvdName, String subject, String author, boolean isIssued) {
        currentIdNumber++;

        if(dvdID==-1)
            dvdID = currentIdNumber;
        else
            dvdID=dvdID;

        this.dvdID = dvdID;
        this.dvdName = dvdName;
        this.subject = subject;
        this.author = author;
        this.isIssued = isIssued;
    }


    public void printInfo()
    {
        System.out.println(dvdName + "\t\t\t" + author + "\t\t\t" + subject);
    }

    public int getDvdID() {
        return dvdID;
    }

    public void setDvdID(int dvdID) {
        this.dvdID = dvdID;
    }

    public String getDvdName()
    {
        return dvdName;
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

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public static void setIDCount(int n)
    {
        currentIdNumber=n;
    }


    public void returnDVD(Borrower borrower, LoanedDVDs loanedDVDs)
    {
        loanedDVDs.getDvd().setIssued(false);
        loanedDVDs.setDateReturned(new Date());
        borrower.removeBorrowedDVD(loanedDVDs);
        loanedDVDs.payFine();

        System.out.println("\nThe DVD name " + loanedDVDs.getDvd() + " is successfully returned by " + borrower.getName() + ".");
    }
}
