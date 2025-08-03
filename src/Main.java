import Controllers.ProductController;

import java.util.Scanner;

public class Main {
    public static Scanner sharedScanner = new Scanner(System.in);
    public static void main(String[] args){

        ProductController productController = new ProductController(sharedScanner);

        productController.insertProduct();
        productController.updateProduct(productController.getProduct());
        productController.deleteProduct(productController.getProduct());
        productController.displayAllProducts();

    }
}
