package Models;

public class Product {
    //Private Fields
    private int product_id, product_price, product_qty;
    private String product_name;

    //Constructors
    public Product(){}

    public Product(String product_name, int product_price, int product_qty){
        setProduct_name(product_name);
        setProduct_price(product_price);
        setProduct_qty(product_qty);
    }

    //Setters
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public void setProduct_qty(int product_qty) {
        this.product_qty = product_qty;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    //Getters
    public int getProduct_id() {
        return this.product_id;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public int getProduct_price() {
        return this.product_price;
    }

    public int getProduct_qty() {
        return this.product_qty;
    }

    public void displayProductInfo(){
        System.out.println("\nID: " + this.getProduct_id());
        System.out.println("PRODUCT NAME: " + this.getProduct_name());
        System.out.println("PRODUCT PRICE: " + this.getProduct_price());
        System.out.println("STOCKS LEFT: " + this.getProduct_qty());
    }

}//end of Product Class
