package com.library.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Library {

    private String name;
    public static ArrayList<Customer> customers;
    private ArrayList <Book> booksInLibrary;
    private ArrayList <LoanedBooks> loans;
    public int book_return_deadline;
    public double per_day_fine;
    public int hold_request_expiry;
    //Created object of the hold request operations
    //private HoldRequestOperations holdRequestsOperations =new HoldRequestOperations();


    /*----Following Singleton Design Pattern (Lazy Instantiation)------------*/
    private static Library obj;

    public static Library getInstance()
    {
        if(obj==null)
        {
            obj = new Library();
        }

        return obj;
    }
    /*---------------------------------------------------------------------*/

    private Library()   // default cons.
    {
        name = null;
        customers = new ArrayList();

        booksInLibrary = new ArrayList();
        loans = new ArrayList();
    }


    /*------------Setter FUNCs.------------*/

    public void setReturnDeadline(int deadline)
    {
        book_return_deadline = deadline;
    }

    public void setFine(double perDayFine)
    {
        per_day_fine = perDayFine;
    }

    public void setRequestExpiry(int hrExpiry)
    {
        hold_request_expiry = hrExpiry;
    }
    /*--------------------------------------*/



    // Setter Func.
    public void setName(String n)
    {
        name = n;
    }

    public ArrayList<Customer> getcustomers()
    {
        return customers;
    }



    public String getLibraryName()
    {
        return name;
    }

    public ArrayList<Book> getBooks()
    {
        return booksInLibrary;
    }



    public void addBorrower(Borrower b)
    {
        customers.add(b);
    }


    public void addLoan(LoanedBooks l)
    {
        loans.add(l);
    }

    /*----------------------------------------------*/

    /*-----------Finding People in Library--------------*/
    public Borrower findBorrower()
    {
        System.out.println("\nEnter Borrower's ID: ");

        int id = 0;

        Scanner scanner = new Scanner(System.in);

        try{
            id = scanner.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input");
        }

        for (int i = 0; i < customers.size(); i++)
        {
            if (customers.get(i).getId() == id && customers.get(i).getClass().getSimpleName().equals("Borrower"))
                return (Borrower)(customers.get(i));
        }

        System.out.println("\nSorry this ID didn't match any Borrower's ID.");
        return null;
    }


    /*------- FUNCS. on Books In Library--------------*/
    public void addBookinLibrary(Book b)
    {
        booksInLibrary.add(b);
    }


    // Searching Books on basis of title, Subject or Author
    public ArrayList<Book> searchForBooks() throws IOException
    {
        String choice;
        String title = "", subject = "", author = "";

        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            System.out.println("\nEnter either '1' or '2' or '3' for search by Title, Subject or Author of Book respectively: ");
            choice = sc.next();

            if (choice.equals("1") || choice.equals("2") || choice.equals("3"))
                break;
            else
                System.out.println("\nWrong Input!");
        }

        if (choice.equals("1"))
        {
            System.out.println("\nEnter the Title of the Book: ");
            title = reader.readLine();
        }

        else if (choice.equals("2"))
        {
            System.out.println("\nEnter the Subject of the Book: ");
            subject = reader.readLine();
        }

        else
        {
            System.out.println("\nEnter the Author of the Book: ");
            author = reader.readLine();
        }

        ArrayList<Book> matchedBooks = new ArrayList();

        //Retrieving all the books which matched the user's search query
        for(int i = 0; i < booksInLibrary.size(); i++)
        {
            Book b = booksInLibrary.get(i);

            if (choice.equals("1"))
            {
                if (b.getTitle().equals(title))
                    matchedBooks.add(b);
            }
            else if (choice.equals("2"))
            {
                if (b.getSubject().equals(subject))
                    matchedBooks.add(b);
            }
            else
            {
                if (b.getAuthor().equals(author))
                    matchedBooks.add(b);
            }
        }

        //Printing all the matched Books
        if (!matchedBooks.isEmpty())
        {
            System.out.println("\nThese books are found: \n");

            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < matchedBooks.size(); i++)
            {
                System.out.print(i + "-" + "\t\t");
                matchedBooks.get(i).printInfo();
                System.out.print("\n");
            }

            return matchedBooks;
        }
        else
        {
            System.out.println("\nSorry. No Books were found related to your query.");
            return null;
        }
    }



    // View Info of all Books in Library
    public void viewAllBooks()
    {
        if (!booksInLibrary.isEmpty())
        {
            System.out.println("\nBooks are: ");

            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < booksInLibrary.size(); i++)
            {
                System.out.print(i + "-" + "\t\t");
                booksInLibrary.get(i).printInfo();
                System.out.print("\n");
            }
        }
        else
            System.out.println("\nCurrently, Library has no books.");
    }


    //Computes total fine for all loans of a borrower
    public double computeFine2(Borrower borrower)
    {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("No.\t\tBook's Title\t\tBorrower's Name\t\t\tIssued Date\t\t\tReturned Date\t\t\t\tFine(Rs)");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        double totalFine = 0;
        double per_loan_fine = 0;

        for (int i = 0; i < loans.size(); i++)
        {
            LoanedBooks l = loans.get(i);

            if ((l.getBorrower() == borrower))
            {
                per_loan_fine = l.computeFine1();
                System.out.print(i + "-" + "\t\t" + loans.get(i).getBook().getTitle() + "\t\t\t" + loans.get(i).getBorrower().getName() + "\t\t" + loans.get(i).getIssuedDate() +  "\t\t\t" + loans.get(i).getDateReturned() + "\t\t\t\t" + per_loan_fine  + "\n");

                totalFine += per_loan_fine;
            }
        }

        return totalFine;
    }


    public void createCUSTOMER(char inputChar)
    {
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

        int phone = 0;

        try{
            System.out.println("Enter Phone Number: ");
            phone = sc.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input.");
        }
        //If borrower is to be created
        if(inputChar=='B')
        {
            Borrower b = new Borrower(-1,n,address,phone);
            addBorrower(b);
            System.out.println("\nBorrower with name " + n + " created successfully.");

            System.out.println("\nYour ID is : " + b.getId());
            System.out.println("Your Password is : " + b.getPassword());
        }
    }



    public void createBook(String title, String subject, String author)
    {
        Book b = new Book(-1,title,subject,author,false);

        addBookinLibrary(b);

        System.out.println("\nBook with Title " + b.getTitle() + " is successfully created.");
    }



    // Called when want an access to Portal
    public Customer login()
    {
        Scanner input = new Scanner(System.in);

        int id = 0;
        String password = "";

        System.out.println("\nEnter ID: ");

        try{
            id = input.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input");
        }

        System.out.println("Enter Password: ");
        password = input.next();

        for (int i = 0; i < customers.size(); i++)
        {
            if (customers.get(i).getId() == id && customers.get(i).getPassword().equals(password))
            {
                System.out.println("\nLogin Successful");
                return customers.get(i);
            }
        }

        System.out.println("\nSorry! Wrong ID or Password");
        return null;
    }


    //---------------------------------------------------------------------------------------//
    /*--------------------------------IN- COLLABORATION WITH DATA BASE------------------------------------------*/

    // Making Connection With Database
    public Connection makeConnection()
    {
        String scriptFilePath = "D:\\Lending-Library-Management\\src\\resources\\DB.sql";
        BufferedReader reader = null;
        Statement statement = null;
        try
        {
            Class.forName("org.h2.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:h2:"+"./Database/myDatabase","root","root");
            statement = con.createStatement();
            reader = new BufferedReader(new FileReader(scriptFilePath));
            String line = null;
            while ((line = reader.readLine()) != null) {
                statement.execute(line);
            }
            System.out.println("created");
            return con;
        }
        catch ( Exception err )
        {
            err.printStackTrace();
            return null;
        }


    }


    // Loading all info in code via Database.
    public void populateLibrary(Connection con) throws SQLException, IOException
    {
        Library lib = this;
        Statement stmt = con.createStatement( );

        /* --- Populating Book ----*/
        String SQL = "SELECT * FROM BOOK";
        ResultSet rs = stmt.executeQuery( SQL );

        if(!rs.next())
        {
            System.out.println("\nNo Books Found in Library");
        }
        else
        {
            int maxID = 0;

            do
            {
                if(rs.getString("TITLE") !=null && rs.getString("AUTHOR")!=null && rs.getString("SUBJECT")!=null && rs.getInt("ID")!=0)
                {
                    String title=rs.getString("TITLE");
                    String author=rs.getString("AUTHOR");
                    String subject=rs.getString("SUBJECT");
                    int id= rs.getInt("ID");
                    boolean issue=rs.getBoolean("IS_ISSUED");
                    Book b = new Book(id,title,subject,author,issue);
                    addBookinLibrary(b);

                    if (maxID < id)
                        maxID = id;
                }
            }while(rs.next());

            // setting Book Count
            Book.setIDCount(maxID);
        }

        /*---Populating Borrowers (partially)!!!!!!--------*/

        SQL="SELECT ID,PNAME,ADDRESS,PASSWORD,PHONE_NO FROM CUSTOMER INNER JOIN BORROWER ON ID=B_ID";

        rs=stmt.executeQuery(SQL);

        if(!rs.next())
        {
            System.out.println("No Borrower Found in Library");
        }
        else
        {
            do
            {
                int id=rs.getInt("ID");
                String name=rs.getString("PNAME");
                String adrs=rs.getString("ADDRESS");
                int phn=rs.getInt("PHONE_NO");

                Borrower b= new Borrower(id,name,adrs,phn);
                addBorrower(b);

            }while(rs.next());

        }

        /*----Populating Loan----*/

        SQL="SELECT * FROM LOAN";

        rs=stmt.executeQuery(SQL);
        if(!rs.next())
        {
            System.out.println("No Books Issued Yet!");
        }
        else
        {
            do
            {
                int borid=rs.getInt("BORROWER");
                int bokid=rs.getInt("BOOK");
                int iid=rs.getInt("ISSUER");
                Integer rid=(Integer)rs.getObject("RECEIVER");
                int rd=0;
                Date rdate;

                Date idate=new Date (rs.getTimestamp("ISS_DATE").getTime());

                if(rid!=null)    // if there is a receiver
                {
                    rdate=new Date (rs.getTimestamp("RET_DATE").getTime());
                    rd=(int)rid;
                }
                else
                {
                    rdate=null;
                }

                boolean fineStatus = rs.getBoolean("FINE_PAID");

                boolean set=true;

                Borrower bb = null;


                for(int i=0;i<getcustomers().size() && set;i++)
                {
                    if(getcustomers().get(i).getId()==borid)
                    {
                        set=false;
                        bb=(Borrower)(getcustomers().get(i));
                    }
                }

                set=true;



                ArrayList<Book> books = getBooks();

                for(int k=0;k<books.size() && set;k++)
                {
                    if(books.get(k).getID()==bokid)
                    {
                        set=false;
                        LoanedBooks l = new LoanedBooks(bb,books.get(k),idate,rdate,fineStatus);
                        loans.add(l);
                    }
                }

            }while(rs.next());
        }

        /* --- Populating Borrower's Remaining Info----*/

        // Borrowed Books
        SQL="SELECT ID,BOOK FROM CUSTOMER INNER JOIN BORROWER ON ID=B_ID INNER JOIN BORROWED_BOOK ON B_ID=BORROWER ";

        rs=stmt.executeQuery(SQL);

        if(!rs.next())
        {
            System.out.println("No Borrower has borrowed yet from Library");
        }
        else
        {

            do
            {
                int id=rs.getInt("ID");      // borrower
                int bid=rs.getInt("BOOK");   // book

                Borrower bb=null;
                boolean set=true;
                boolean okay=true;

                for(int i=0;i<lib.getcustomers().size() && set;i++)
                {
                    if(lib.getcustomers().get(i).getClass().getSimpleName().equals("Borrower"))
                    {
                        if(lib.getcustomers().get(i).getId()==id)
                        {
                            set =false;
                            bb=(Borrower)(lib.getcustomers().get(i));
                        }
                    }
                }

                set=true;

                ArrayList<LoanedBooks> books = loans;

                for(int i=0;i<books.size() && set;i++)
                {
                    if(books.get(i).getBook().getID()==bid )
                    {
                        set=false;
                        LoanedBooks bBook= new LoanedBooks(bb,books.get(i).getBook(),null,books.get(i).getIssuedDate(),false);
                        bb.addBorrowedBook(bBook);
                    }
                }

            }while(rs.next());
        }

        ArrayList<Customer> customers = lib.getcustomers();

        /* Setting CUSTOMER ID Count */
        int max=0;

        for(int i=0;i<customers.size();i++)
        {
            if (max < customers.get(i).getId())
                max=customers.get(i).getId();
        }

        Customer.setIDCount(max);
    }


    // Filling Changes back to Database
    public void fillItBack(Connection con) throws SQLException, SQLIntegrityConstraintViolationException
    {
        /*-----------Loan Table Cleared------------*/

        String template = "DELETE FROM LIBRARY.LOAN";
        PreparedStatement stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        /*-----------Borrowed Books Table Cleared------------*/

        template = "DELETE FROM LIBRARY.BORROWED_BOOK";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        /*-----------OnHoldBooks Table Cleared------------*/

        template = "DELETE FROM LIBRARY.ON_HOLD_BOOK";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        /*-----------Books Table Cleared------------*/

        template = "DELETE FROM LIBRARY.BOOK";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        /*-----------Clerk Table Cleared------------*/

        template = "DELETE FROM LIBRARY.CLERK";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        /*-----------Librarian Table Cleared------------*/

        template = "DELETE FROM LIBRARY.LIBRARIAN";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        /*-----------Borrower Table Cleared------------*/

        template = "DELETE FROM LIBRARY.BORROWER";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        /*-----------Staff Table Cleared------------*/

        template = "DELETE FROM LIBRARY.STAFF";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        /*-----------CUSTOMER Table Cleared------------*/

        template = "DELETE FROM LIBRARY.CUSTOMER";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        Library lib = this;

        /* Filling CUSTOMER's Table*/
        for(int i=0;i<lib.getcustomers().size();i++)
        {
            template = "INSERT INTO LIBRARY.CUSTOMER (ID,PNAME,PASSWORD,ADDRESS,PHONE_NO) values (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);

            stmt.setInt(1, lib.getcustomers().get(i).getId());
            stmt.setString(2, lib.getcustomers().get(i).getName());
            stmt.setString(3,  lib.getcustomers().get(i).getPassword());
            stmt.setString(4, lib.getcustomers().get(i).getAddress());
            stmt.setInt(5, lib.getcustomers().get(i).getPhoneNo());

            stmt.executeUpdate();
        }


        /* Filling Borrower's Table*/
        for(int i=0;i<lib.getcustomers().size();i++)
        {
            if (lib.getcustomers().get(i).getClass().getSimpleName().equals("Borrower"))
            {
                template = "INSERT INTO LIBRARY.BORROWER(B_ID) values (?)";
                PreparedStatement stmt = con.prepareStatement(template);

                stmt.setInt(1, lib.getcustomers().get(i).getId());

                stmt.executeUpdate();
            }
        }

        ArrayList<Book> books = lib.getBooks();

        /*Filling Book's Table*/
        for(int i=0;i<books.size();i++)
        {
            template = "INSERT INTO LIBRARY.BOOK (ID,TITLE,AUTHOR,SUBJECT,IS_ISSUED) values (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);

            stmt.setInt(1,books.get(i).getID());
            stmt.setString(2,books.get(i).getTitle());
            stmt.setString(3, books.get(i).getAuthor());
            stmt.setString(4, books.get(i).getSubject());
            stmt.setBoolean(5, books.get(i).getIssuedStatus());
            stmt.executeUpdate();

        }

/*
        */
/* Filling Loan Book's Table*//*

        for(int i=0;i<loans.size();i++)
        {
            template = "INSERT INTO LIBRARY.LOAN(L_ID,BORROWER,BOOK,ISSUER,ISS_DATE,RECEIVER,RET_DATE,FINE_PAID) values (?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);

            stmt.setInt(1,i+1);
            stmt.setInt(2,loans.get(i).getBorrower().getID());
            stmt.setInt(3,loans.get(i).getBook().getID());
            stmt.setInt(4,loans.get(i).getIssuer().getID());
            stmt.setTimestamp(5,new java.sql.Timestamp(loans.get(i).getIssuedDate().getTime()));
            stmt.setBoolean(8,loans.get(i).ge());

            stmt.executeUpdate();

        }
*/


        /*for(int i=0;i<lib.getBooks().size();i++)
        {
            for(int j=0;j<lib.getBooks().get(i).getHoldRequests().size();j++)
            {
            template = "INSERT INTO LIBRARY.ON_HOLD_BOOK(REQ_ID,BOOK,BORROWER,REQ_DATE) values (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);

            stmt.setInt(1,i+1);
            stmt.setInt(3,lib.getBooks().get(i).getHoldRequests().get(j).getBorrower().getID());
            stmt.setInt(2,lib.getBooks().get(i).getHoldRequests().get(j).getBook().getID());
            stmt.setDate(4,new java.sql.Date(lib.getBooks().get(i).getHoldRequests().get(j).getRequestDate().getTime()));

            stmt.executeUpdate();
            }
        }*/

        /* Filling Borrowed Book Table*/
        for(int i=0;i<lib.getBooks().size();i++)
        {
            if(lib.getBooks().get(i).getIssuedStatus()==true)
            {
                boolean set=true;
                for(int j=0;j<loans.size() && set ;j++)
                {
                    if(lib.getBooks().get(i).getID()==loans.get(j).getBook().getID())
                    {

                            template = "INSERT INTO LIBRARY.BORROWED_BOOK(BOOK,BORROWER) values (?,?)";
                            PreparedStatement stmt = con.prepareStatement(template);
                            stmt.setInt(1,loans.get(j).getBook().getID());
                            stmt.setInt(2,loans.get(j).getBorrower().getId());

                            stmt.executeUpdate();
                            set=false;
                    }

                }

            }
        }
    } // Filling Done!


}
