package com.library.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Library {

    private String name;
    public static ArrayList<Customer> customers;
    private ArrayList<Book> booksInLibrary;
    private ArrayList<DVD> dvdsInLibrary;
    private ArrayList<LoanedBooks> loans;
    private ArrayList<LoanedDVDs> loaneddvds;
    public int bookReturnDeadline;
    public double perDayFineForBook;
    public double perDayFineForDVD;



    public int holdRequestExpiry;

    private static Library obj;

    public static Library getInstance() {
        if (obj == null) {
            obj = new Library();
        }

        return obj;
    }

    private Library()
    {
        name = null;
        customers = new ArrayList();
        booksInLibrary = new ArrayList();
        loans = new ArrayList();
        dvdsInLibrary = new ArrayList();
        loaneddvds = new ArrayList();
    }

    public void setReturnDeadline(int deadline) {
        bookReturnDeadline = deadline;
    }

    public void setFine(double perDayFine) {
        this.perDayFineForBook = perDayFine;
    }

    public void setRequestExpiry(int hrExpiry) {
        holdRequestExpiry = hrExpiry;
    }

    public void setName(String n) {
        name = n;
    }

    public ArrayList<Customer> getcustomers() {
        return customers;
    }


    public String getLibraryName() {
        return name;
    }

    public ArrayList<Book> getBooks() {
        return booksInLibrary;
    }

    public ArrayList<DVD> getDvds() {
        return dvdsInLibrary;
    }

    public void addBorrower(Borrower b) {
        customers.add(b);
    }


    public void addLoan(LoanedBooks l) {
        loans.add(l);
    }

    public void listCustomerLoanedItems()
    {

    }

    public Borrower findBorrower() {
        System.out.println("\nEnter Borrower's ID: ");
        int id = 0;
        Scanner scanner = new Scanner(System.in);

        try {
            id = scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("\nInvalid Input");
        }

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id && customers.get(i).getClass().getSimpleName().equals("Borrower"))
                return (Borrower) (customers.get(i));
        }

        System.out.println("\nSorry this ID didn't match any Borrower's ID.");
        return null;
    }


    public void addBookinLibrary(Book b) {
        booksInLibrary.add(b);
    }

    public void addDVDinLibrary(DVD b) {
        dvdsInLibrary.add(b);
    }


    // Searching Books on basis of title, Subject or Author
    public int searchForBooks() throws IOException {
        String choice;
        String title = "", subject = "", author = "";

        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\nEnter either '1' or '2' or '3' for search by Title, Subject or Author of Book respectively: ");
            choice = sc.next();

            if (choice.equals("1") || choice.equals("2") || choice.equals("3"))
                break;
            else
                System.out.println("\nWrong Input!");
        }

        if (choice.equals("1")) {
            System.out.println("\nEnter the Title of the Book: ");
            title = reader.readLine();
        } else if (choice.equals("2")) {
            System.out.println("\nEnter the Subject of the Book: ");
            subject = reader.readLine();
        } else {
            System.out.println("\nEnter the Author of the Book: ");
            author = reader.readLine();
        }

        ArrayList<Book> matchedBooks = new ArrayList();

        //Retrieving all the books which matched the user's search query
        for (int i = 0; i < booksInLibrary.size(); i++) {
            Book b = booksInLibrary.get(i);

            if (choice.equals("1")) {
                if (b.getTitle().equals(title))
                    matchedBooks.add(b);
            } else if (choice.equals("2")) {
                if (b.getSubject().equals(subject))
                    matchedBooks.add(b);
            } else {
                if (b.getAuthor().equals(author))
                    matchedBooks.add(b);
            }
        }

        //Printing all the matched Books
        if (!matchedBooks.isEmpty()) {
            return matchedBooks.size();
        } else {
            System.out.println("\nSorry. No Books were found related to your query.");
            return 0;
        }
    }


    // View Info of all Books in Library
    public void viewAllBooks() {
        if (!booksInLibrary.isEmpty()) {
            System.out.println("\nBooks are: ");

            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < booksInLibrary.size(); i++) {
                System.out.print(i + "-" + "\t\t");
                booksInLibrary.get(i).printInfo();
                System.out.print("\n");
            }
        } else
            System.out.println("\nCurrently, Library has no books.");
    }


    //Computes total fine for all loans of a borrower
    public double computeFine2(Borrower borrower) {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("No.\t\tBook's Title\t\tBorrower's Name\t\t\tIssued Date\t\t\tReturned Date\t\t\t\tFine(Rs)");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        double totalFine = 0;
        double per_loan_fine = 0;

        for (int i = 0; i < loans.size(); i++) {
            LoanedBooks l = loans.get(i);

            if ((l.getBorrower() == borrower)) {
                per_loan_fine = l.computeFine1();
                System.out.print(i + "-" + "\t\t" + loans.get(i).getBook().getTitle() + "\t\t\t" + loans.get(i).getBorrower().getName() + "\t\t" + loans.get(i).getIssuedDate() + "\t\t\t" + loans.get(i).getDateReturned() + "\t\t\t\t" + per_loan_fine + "\n");

                totalFine += per_loan_fine;
            }
        }

        return totalFine;
    }


    public void createCUSTOMER(char inputChar) {
        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\nEnter Name: ");
        String n = "";
        try {
            n = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Enter Address: ");

        String address = "";
        try {
            address = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }

        String phone = "";

        try {
            System.out.println("Enter Phone Number: ");
            phone = sc.next();
        } catch (java.util.InputMismatchException e) {
            System.out.println("\nInvalid Input.");
        }
        //If borrower is to be created
        if (inputChar == 'B') {
            Borrower b = new Borrower(-1, n, address, phone);
            addBorrower(b);
            System.out.println("\nBorrower with name " + n + " created successfully.");

            System.out.println("\nYour ID is : " + b.getId());
            System.out.println("Your Password is : " + b.getPassword());
        }
    }


    public void createBook(String title, String subject, String author) {
        Book b = new Book(-1, title, subject, author, false);

        addBookinLibrary(b);

        System.out.println("\nBook with Title " + b.getTitle() + " is successfully created.");
    }


    // Called when want an access to Portal
    public Customer login() {
        Scanner input = new Scanner(System.in);

        int id = 0;
        String password = "";

        System.out.println("\nEnter ID: ");

        try {
            id = input.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("\nInvalid Input");
        }

        System.out.println("Enter Password: ");
        password = input.next();

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id && customers.get(i).getPassword().equals(password)) {
                System.out.println("\nLogin Successful");
                return customers.get(i);
            }
        }

        System.out.println("\nSorry! Wrong ID or Password");
        return null;
    }



    // Making Connection With Database
    public Connection makeConnection() {
        String scriptFilePath = "D:\\Lending-Library-Management\\src\\resources\\DB.sql";
        BufferedReader reader = null;
        Statement statement = null;
        try {
            Class.forName("org.h2.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/myDatabase", "root", "root");
            statement = con.createStatement();
            reader = new BufferedReader(new FileReader(scriptFilePath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                statement.execute(line);
            }
            System.out.println("created");
            return con;
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }


    }

    // Loading all info in code via Database.
    public void populateLibrary(Connection con) throws SQLException, IOException {
        Library lib = this;
        Statement stmt = con.createStatement();

        /* --- Populating Book ----*/
        String SQL = "SELECT * FROM BOOK";
        ResultSet rs = stmt.executeQuery(SQL);

        if (!rs.next()) {
            System.out.println("\nNo Books Found in Library");
        } else {
            int maxID = 0;

            do {
                if (rs.getString("TITLE") != null && rs.getString("AUTHOR") != null && rs.getString("SUBJECT") != null && rs.getInt("ID") != 0) {
                    String title = rs.getString("TITLE");
                    String author = rs.getString("AUTHOR");
                    String subject = rs.getString("SUBJECT");
                    int id = rs.getInt("ID");
                    boolean issue = rs.getBoolean("IS_ISSUED");
                    Book b = new Book(id, title, subject, author, issue);
                    addBookinLibrary(b);

                    if (maxID < id)
                        maxID = id;
                }
            } while (rs.next());

            // setting Book Count
            Book.setIDCount(maxID);
        }

        /* --- Populating DVD ----*/
         SQL = "SELECT * FROM DVD";
         rs = stmt.executeQuery(SQL);

        if (!rs.next()) {
            System.out.println("\nNo DVD Found in Library");
        } else {
            int maxID = 0;

            do {
                if (rs.getString("DVDNAME") != null && rs.getString("AUTHOR") != null && rs.getString("SUBJECT") != null && rs.getInt("ID") != 0) {
                    String dvdName = rs.getString("DVDNAME");
                    String author = rs.getString("AUTHOR");
                    String subject = rs.getString("SUBJECT");
                    int id = rs.getInt("ID");
                    boolean issue = rs.getBoolean("IS_ISSUED");
                    DVD dvd = new DVD(id, dvdName, subject, author, issue);
                    addDVDinLibrary(dvd);

                    if (maxID < id)
                        maxID = id;
                }
            } while (rs.next());

            // setting DVD Count
            DVD.setIDCount(maxID);
        }

        /*---Populating Borrowers (partially)!!!!!!--------*/

        SQL = "SELECT ID,PNAME,ADDRESS,PASSWORD,PHONE_NO FROM CUSTOMER INNER JOIN BORROWER ON ID=B_ID";

        rs = stmt.executeQuery(SQL);

        if (!rs.next()) {
            System.out.println("No Borrower Found in Library");
        } else {
            do {
                int id = rs.getInt("ID");
                String name = rs.getString("PNAME");
                String adrs = rs.getString("ADDRESS");
                String phn = rs.getString("PHONE_NO");

                Borrower b = new Borrower(id, name, adrs, phn);
                addBorrower(b);

            } while (rs.next());

        }

        /*----Populating Loan----*/

        SQL = "SELECT * FROM LOAN";

        rs = stmt.executeQuery(SQL);
        if (!rs.next()) {
            System.out.println("No Books Issued Yet!");
        } else {
            do {
                int borid = rs.getInt("BORROWER");
                int bokid = rs.getInt("BOOK");
                int dvdid = rs.getInt("DVD");
                int rd = 0;

                Date rdate;
                Date idate = new Date(rs.getTimestamp("ISS_DATE").getTime());

                rdate = new Date(rs.getTimestamp("RET_DATE").getTime());
                boolean fineStatus = rs.getBoolean("FINE_PAID");
                boolean set = true;
                Borrower bb = null;

                for (int i = 0; i < getcustomers().size() && set; i++) {
                    if (getcustomers().get(i).getId() == borid) {
                        set = false;
                        bb = (Borrower) (getcustomers().get(i));
                    }
                }
                set = true;

                ArrayList<Book> books = getBooks();
                for (int k = 0; k < books.size() && set; k++) {
                    if (books.get(k).getBookID() == bokid) {
                        set = false;
                        LoanedBooks l = new LoanedBooks(bb, books.get(k), idate, rdate, fineStatus);
                        loans.add(l);
                    }
                }

                ArrayList<DVD> dvds = getDvds();
                for (int k = 0; k < dvds.size() ; k++) {
                    if (dvds.get(k).getDvdID() == dvdid) {
                        set = false;
                        LoanedDVDs l = new LoanedDVDs(bb, dvds.get(k), idate, rdate, fineStatus);
                        loaneddvds.add(l);
                    }
                }

            } while (rs.next());
        }


        // Borrowed Books
        SQL = "SELECT ID,BOOK FROM CUSTOMER INNER JOIN BORROWER ON ID=B_ID INNER JOIN BORROWED_BOOK ON B_ID=BORROWER ";

        rs = stmt.executeQuery(SQL);

        if (!rs.next()) {
            System.out.println("No Borrower has borrowed yet from Library");
        } else {

            do {
                int id = rs.getInt("ID");
                int bid = rs.getInt("BOOK");

                Borrower bb = null;
                boolean set = true;
                boolean okay = true;

                for (int i = 0; i < lib.getcustomers().size() && set; i++) {
                    if (lib.getcustomers().get(i).getClass().getSimpleName().equals("Borrower")) {
                        if (lib.getcustomers().get(i).getId() == id) {
                            set = false;
                            bb = (Borrower) (lib.getcustomers().get(i));
                        }
                    }
                }

                set = true;

                ArrayList<LoanedBooks> books = loans;

                for (int i = 0; i < books.size() && set; i++) {
                    if (books.get(i).getBook().getBookID() == bid) {
                        set = false;
                        LoanedBooks bBook = new LoanedBooks(bb, books.get(i).getBook(), null, books.get(i).getIssuedDate(), false);
                        bb.addBorrowedBook(bBook);
                    }
                }

            } while (rs.next());
        }

        // Borrowed DVD
        SQL = "SELECT ID,DVD FROM CUSTOMER INNER JOIN BORROWER ON ID=B_ID INNER JOIN BORROWED_DVD ON B_ID=BORROWER ";

        rs = stmt.executeQuery(SQL);

        if (!rs.next()) {
            System.out.println("No Borrower has borrowed yet from Library");
        } else {

            do {
                int id = rs.getInt("ID");
                int did = rs.getInt("DVD");

                Borrower bb = null;
                boolean set = true;
                boolean okay = true;

                for (int i = 0; i < lib.getcustomers().size() && set; i++) {
                    if (lib.getcustomers().get(i).getClass().getSimpleName().equals("Borrower")) {
                        if (lib.getcustomers().get(i).getId() == id) {
                            set = false;
                            bb = (Borrower) (lib.getcustomers().get(i));
                        }
                    }
                }

                set = true;

                ArrayList<LoanedDVDs> dvds = loaneddvds;

                for (int i = 0; i < dvds.size() && set; i++) {
                    if (dvds.get(i).getDvd().getDvdID() == did) {
                        set = false;
                        LoanedDVDs loanedDVD = new LoanedDVDs(bb, dvds.get(i).getDvd(), null, dvds.get(i).getIssuedDate(), false);
                        bb.addBorrowedDVD(loanedDVD);
                    }
                }

            } while (rs.next());
        }

        ArrayList<Customer> customers = lib.getcustomers();

        int max = 0;

        for (int i = 0; i < customers.size(); i++) {
            if (max < customers.get(i).getId())
                max = customers.get(i).getId();
        }

        Customer.setIDCount(max);
    }

}
