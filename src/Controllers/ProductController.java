package Controllers;

import Utils.MySQLConnection;
import Models.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductController {
    private Product product;
    private final Scanner scanner;

    public ProductController(Scanner sharedScanner){
        this.scanner = sharedScanner;
    }

    //Insert product
    public void insertProduct(){
        System.out.println("***ADDING A PRODUCT***");

        System.out.print("\nEnter Product Name: ");
        String prodName = scanner.nextLine();

        System.out.print("Enter Product Price: ");
        int prodPrice = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter Product Quantity: ");
        int prodQty = scanner.nextInt();

        scanner.nextLine();

        String query  = "INSERT INTO tblProducts (product_name, product_price, product_qty) VALUES (?, ?, ?);";

        try (Connection conn = MySQLConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, prodName);
            ps.setInt(2, prodPrice);
            ps.setInt(3, prodQty);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product inserted successfully.");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }//end of insert product method

    //Display Product
    public void displayAllProducts(){
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM tblProducts";

        try(Connection conn = MySQLConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            while(rs.next()){
                Product prod = new Product();
                prod.setProduct_id(rs.getInt("product_id"));
                prod.setProduct_name(rs.getString("product_name"));
                prod.setProduct_price(rs.getInt("product_price"));
                prod.setProduct_qty(rs.getInt("product_qty"));
                products.add(prod);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("***PRODUCTS***");
        for(Product prod : products){
            prod.displayProductInfo();
        }

    }//end of display all products method

    //Get Specific Product
    public Product getProduct(){
        Product prod = null;
        int prodId = -1;

        displayAllProducts();

        System.out.print("\nSelect Product Id: ");
        prodId = scanner.nextInt();

        scanner.nextLine();

        String query = "SELECT * FROM tblProducts WHERE product_id = ?";

        try(Connection conn = MySQLConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)){

            ps.setInt(1, prodId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                prod = new Product();
                prod.setProduct_id(rs.getInt("product_id"));
                prod.setProduct_name(rs.getString("product_name"));
                prod.setProduct_price(rs.getInt("product_price"));
                prod.setProduct_qty(rs.getInt("product_qty"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return prod;
    }//end of get product method

    //update product
    public void updateProduct(Product product){

        if(product == null){
            System.out.println("No Product Found");
            return;
        }

        String query = "";

        System.out.println("\n[1]Product Name \n[2]Product Price \n[3]Product Quantity");
        System.out.print("Select Details to Update: ");
        int choice = scanner.nextInt();

        scanner.nextLine();

        switch(choice){
            case 1 -> {
                System.out.print("\nEnter New Product Name: ");
                String newName = scanner.nextLine();

                if(newName.trim().isEmpty()){
                    System.out.println("Product Name Cannot Be Emtpy");
                    break;
                }

                query = "UPDATE tblProducts SET product_name = ? WHERE product_id = ?";

                try (Connection conn = MySQLConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(query)) {

                    ps.setString(1, newName);
                    ps.setInt(2, product.getProduct_id());
                    ps.executeUpdate();  // Execute the update
                    System.out.println("Product name updated successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            case 2 -> {
                System.out.print("\nEnter New Product Price: ");
                int newPrice = scanner.nextInt();

                scanner.nextLine();

                if(newPrice <= 0){
                    System.out.println("Price Must Be More Than 0");
                    break;
                }

                query = "UPDATE tblProducts SET product_price = ? WHERE product_id = ?";
                try (Connection conn = MySQLConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setInt(1, newPrice);
                    ps.setInt(2, product.getProduct_id());
                    ps.executeUpdate();
                    System.out.println("Product price updated successfully.");

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            case 3 -> {
                System.out.print("\nEnter New Product Quantity: ");
                int newQty = scanner.nextInt();

                scanner.nextLine();

                if(newQty <= 0){
                    System.out.println("Quantity Must Be More Than 0");
                    break;
                }

                query = "UPDATE tblProducts SET product_qty = ? WHERE product_id = ?";
                try (Connection conn = MySQLConnection.getConnection();
                     PreparedStatement ps = conn.prepareStatement(query)) {

                    ps.setInt(1, newQty);
                    ps.setInt(2, product.getProduct_id());
                    ps.executeUpdate();
                    System.out.println("Product quantity updated successfully.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            case 4 -> System.out.println("Saving Changes. . .");
            default -> System.out.println("Invalid Choice");
        }//end of switch

    }//end of update method


    // Delete Product
    public void deleteProduct(Product product) {
        if (product == null) {
            System.out.println("No Product Found to Delete");
            return;
        }

        String query = "DELETE FROM tblProducts WHERE product_id = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, product.getProduct_id());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Product not found or already deleted.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }// end of delete product method


}
