package com.library.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Borrower extends Customer {

    private ArrayList<LoanedBooks> borrowedBooks;
    private ArrayList<LoanedDVDs> borrowedDVDs;

    public Borrower(int id, String name, String address, String phoneNum)
    {
        super(id, name, address, phoneNum);
        borrowedBooks = new ArrayList<>();
        borrowedDVDs = new ArrayList<>();
    }

    @Override
    public void printInfo() {
        super.printInfo();
        printBorrowedBooks();
        printBorrowedDVDs();

    }

    public void printBorrowedBooks() {
        if (!borrowedBooks.isEmpty()) {
            System.out.println("\nBorrowed Books are: ");
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < borrowedBooks.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                borrowedBooks.get(i).getBook().printInfo();
                System.out.print("\n");
            }
        } else
            System.out.println("\nNo borrowed books.");
    }

    public void printBorrowedDVDs() {
        if (!borrowedDVDs.isEmpty()) {
            System.out.println("\nBorrowed DVDs are: ");
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < borrowedDVDs.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                borrowedDVDs.get(i).getDvd().printInfo();
                System.out.print("\n");
            }
        } else
            System.out.println("\nNo borrowed DVDS.");
    }


    // Updating Borrower's Info
    public void updateBorrowerInfo() throws IOException {
        String choice;

        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("\nDo you want to update " + getName() + "'s Name ? (y/n)");
        choice = sc.next();

        updateBorrowerName(choice, reader);


        System.out.println("\nDo you want to update " + getName() + "'s Address ? (y/n)");
        choice = sc.next();

        updateBorrowerAddress(choice, reader);

        System.out.println("\nDo you want to update " + getName() + "'s Phone Number ? (y/n)");
        choice = sc.next();

        updateBorrowerPhoneNumber(choice, sc);

        System.out.println("\nBorrower is successfully updated.");

    }

    private void updateBorrowerPhoneNumber(String choice, Scanner sc) {
        if (choice.equals("y")) {
            System.out.println("\nType New Phone Number: ");
            setPhoneNo(sc.next());
            System.out.println("\nThe phone number is successfully updated.");
        }
    }

    private void updateBorrowerAddress(String choice, BufferedReader reader) throws IOException {
        if (choice.equals("y")) {
            System.out.println("\nType New Address: ");
            setAddress(reader.readLine());
            System.out.println("\nThe address is successfully updated.");
        }
    }

    private void updateBorrowerName(String choice, BufferedReader reader) throws IOException {
        if (choice.equals("y")) {
            System.out.println("\nType New Name: ");
            setName(reader.readLine());
            System.out.println("\nThe name is successfully updated.");
        }
    }


    public void addBorrowedBook(LoanedBooks iBook) {
        borrowedBooks.add(iBook);
    }

    public void addBorrowedDVD(LoanedDVDs loanedDVD) {
        borrowedDVDs.add(loanedDVD);
    }

    public void removeBorrowedBook(LoanedBooks iBook) {
        borrowedBooks.remove(iBook);
    }

    public void removeBorrowedDVD(LoanedDVDs loanedDVD) {
        borrowedBooks.remove(loanedDVD);
    }

    public ArrayList<LoanedBooks> getBorrowedBooks() {
        return borrowedBooks;
    }

    public ArrayList<LoanedDVDs> getBorrowedDVDs() {
        return borrowedDVDs;
    }

}
