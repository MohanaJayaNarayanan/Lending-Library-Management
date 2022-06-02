
package com.library.model;

public  class Customer
{   
    protected int id;
    protected String password;
    protected String name;
    protected String address;
    protected String phoneNo;
    protected double depositAmount;
    static int currentIdNumber = 0;


    public Customer(int id,String name, String address, String phoneNo) {
        currentIdNumber++;

        if(id==-1)
            id = currentIdNumber;
        else
            id = id;
        password = Integer.toString(id);
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.depositAmount = 1000.00;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public static int getCurrentIdNumber() {
        return currentIdNumber;
    }

    public static void setCurrentIdNumber(int currentIdNumber) {
        Customer.currentIdNumber = currentIdNumber;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public static void setIDCount(int n)
    {
        currentIdNumber=n;
    }
   
}
