package Models;

public class Customer {
    //Private Fields
    private int customerId;
    private String firstName, lastName;

    public Customer(){}

    public Customer(String firstName, String lastName){
        setFirstName(firstName);
        setLastName(lastName);
    }
    //Setters
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //Getters
    public int getCustomerId() {
        return this.customerId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    //Methods

    //Display Customer info
    public void displayCustomerInfo(){
        System.out.println("\nCustomer ID: " + this.getCustomerId());
        System.out.println("First Name: " + this.getFirstName());
        System.out.println("Last Name: " + this.getLastName());
    }

}
