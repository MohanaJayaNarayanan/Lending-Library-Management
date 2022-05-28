
package com.library.model;

public  class Customer
{   
    protected int id;
    protected String password;
    protected String name;
    protected String address;
    protected int phoneNo;
    static int currentIdNumber = 0;     //This will be unique for every person, since it will be incremented when everytime
                                       //when a person is created

    public Customer(int id,String name, String address, int phoneNo) {
        currentIdNumber++;

        if(id==-1)
            id = currentIdNumber;
        else
            id = id;
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
    }

    public void printInfo()
    {
        System.out.println("-----------------------------------------");
        System.out.println("\nThe details are: \n");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone No: " + phoneNo + "\n");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public static int getCurrentIdNumber() {
        return currentIdNumber;
    }

    public static void setCurrentIdNumber(int currentIdNumber) {
        Customer.currentIdNumber = currentIdNumber;
    }

    public static void setIDCount(int n)
    {
        currentIdNumber=n;
    }
   
}
