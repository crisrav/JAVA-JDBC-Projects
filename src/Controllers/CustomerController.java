package Controllers;

import Models.Customer;
import Utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerController {
    private final Scanner scanner;

    //Constructor
    public CustomerController(Scanner scanner){
        this.scanner = scanner;
    }

    //Insert customer into Database
    public void insertCustomer(){
        System.out.println("***INPUT CUSTOMER DETAILS***");

        System.out.print("\nEnter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();


        String query = "INSERT INTO tblCustomer (customer_first_name, customer_last_name) VALUES (?, ?)";

        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, firstName);
            ps.setString(2, lastName);

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("CUSTOMER INSERTED SUCCESSFULLY");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }//end of insert customer method

    //Display All Customers
    public void displayAllCustomers(){
        List<Customer> customers = new ArrayList<>();

        String query = "SELECT * FROM tblCustomer";

        try(Connection conn = MySQLConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            while(rs.next()){
                Customer customer = new Customer();

                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("customer_first_name"));
                customer.setLastName(rs.getString("customer_last_name"));

                customers.add(customer);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        System.out.println("\n***CUSTOMERS***");
        for(Customer customer : customers){
            customer.displayCustomerInfo();
        }

    }//end of display all customer method

    //Get a Customer
    public Customer getCustomer(){
        Customer customer = null;
        int custId = -1;

        displayAllCustomers();

        System.out.print("\nSelect Customer by ID: ");
        custId = scanner.nextInt();

        scanner.nextLine();

        String query = "SELECT * FROM tblCustomer WHERE customer_id = ?";

        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, custId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                customer=  new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("customer_first_name"));
                customer.setLastName(rs.getString("customer_last_name"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return customer;

    }//end of get customer method

    //Edit Customer Info
    public void editCustomerInfo(Customer customer){
        if(customer == null){
            System.out.println("Customer Not Found");
            return;
        }

        System.out.println("\nEDITING CUSTOMER");
        System.out.println("\n[1]Edit First Name \n[2]Edit Last Name \n[3]Exit");
        System.out.print("Enter Choice: ");
        int choice  = scanner.nextInt();

        scanner.nextLine();

        switch(choice){

            case 1 ->{
                System.out.println("\nEditing First Name For " + customer.getFirstName());
                System.out.print("Enter New First Name: ");
                String newFName = scanner.nextLine();

                String query = "UPDATE tblCustomer SET customer_first_name = ? WHERE customer_id = ?";

                try(Connection conn = MySQLConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)){

                    ps.setString(1, newFName);
                    ps.setInt(2, customer.getCustomerId());

                    int rowsAffected = ps.executeUpdate();
                    if(rowsAffected > 0 ){
                        System.out.println("First Name Edited");
                    }

                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            case 2 ->{
                System.out.println("\nEditing Last Name For " + customer.getLastName());
                System.out.print("Enter New Last Name: ");
                String newLName = scanner.nextLine();

                String query = "UPDATE tblCustomer SET customer_last_name = ? WHERE customer_id = ?";

                try(Connection conn = MySQLConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(query)){

                    ps.setString(1, newLName);
                    ps.setInt(2, customer.getCustomerId());

                    int rowsAffected = ps.executeUpdate();
                    if(rowsAffected > 0 ){
                        System.out.println("Last Name Edited");
                    }

                }catch(SQLException e){
                    e.printStackTrace();
                }
            }

            case 3 -> System.out.println("Saving Changes. . .");
            default -> System.out.println("Invalid Choice");


        }//end of switch

    }//end of edit customer info

    //Delete Customer
    public void deleteCustomer(Customer customer){
        if(customer == null){
            System.out.println("Customer Not Found");
            return;
        }
        System.out.println("\n***DELETING USER***");

        String query = "DELETE FROM tblCustomer WHERE customer_id = ?";

        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, customer.getCustomerId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found or already deleted.");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
