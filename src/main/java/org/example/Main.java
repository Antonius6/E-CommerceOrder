package org.example;

import javax.swing.*;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner in =   new Scanner(System.in);

    Store store = new Store();

    int option ;

    do {

      menu();
      option = readInt(in);

      switch (option) {
        case 1-> addProduct(in,store);
        case 2 -> removeProduct(in,store);
        case 3 -> store.displayProducts();
        case 4 -> searchProductByID(in,store);
        case 5 -> store.showCategories();
        case 6 -> store.displayProductsOrderedByPrice();
        case 7 -> createOrder(in,store);
        case 8 -> addItemToOrder(in,store);
        case 9 -> removeItemFromOrder(in,store);
        case 10 -> displayOrder(in,store);
        case 11 -> addOrderToTheShippingList(in,store);
        case 12 -> store.nextOrder();
        case 13 -> cancelOrder(in,store);
        case 14 -> searchOrderByID(in,store);
        case 15 -> addReviewToProduct(in,store);
        case 16 -> showAllReviews(in,store);
        case 17 -> store.removeOutOfStock();
        case 18 -> store.displayOrdersByTotal();
        case 19 ->exit();

      }


    }
    while(option != 19);




  }
  static int readInt(Scanner in) {
    while (!in.hasNextInt()) {
      System.out.println("Invalid input. please enter a number ");
      in.next();
    }
    int value = in.nextInt();
    in.nextLine();
    return value;
  }

  static double readDouble(Scanner in) {
    while (!in.hasNextDouble()) {
      System.out.println("Invalid input. please enter a number ");
      in.next();
    }
    double value = in.nextDouble();
    in.nextLine();
    return value;
  }

  static String readString(Scanner in) {
    while (!in.hasNextLine()) {
      System.out.println("Invalid input. please enter a line ");
      in.next();
    }
    return in.nextLine();

  }
  static void menu(){
    System.out.println("=================== Menu ================================");
    System.out.println("1. Add Product ");
    System.out.println("2. Remove Product ");
    System.out.println("3. Display All Products ");
    System.out.println("4. Search Product by ID ");
    System.out.println("5. Show All Categories ");
    System.out.println("6. Display Products Ordered by Price");
    System.out.println("7. Create Order");
    System.out.println("8. Add Item to Order ");
    System.out.println("9. Remove Item from Order");
    System.out.println("10. Display Order ");
    System.out.println("11. Add Order to the Shipping List ");
    System.out.println("12. Ship Next Order ");
    System.out.println("13. Cancel Order ");
    System.out.println("14. Search Order by ID ");
    System.out.println("15. Add Review to a Product ");
    System.out.println("16. Show All Reviews for a Product ");
    System.out.println("17. Remove Out-of-Stock Products ");
    System.out.println("18. Display Orders Ordered by Total ");
    System.out.println("19. Exit ");
    System.out.println("==========================================================");

  }
  static void addProduct(Scanner in, Store store){
    System.out.println("Enter Product Name ");
    String name = readString(in);
    System.out.println("Enter Product Price ");
    double price = readDouble(in);
    System.out.println("Enter Product category ");
    String category = readString(in);
    System.out.println("Enter Product Quantity ");
    int quantity = readInt(in);

   if(store.addProduct(name,price,category,quantity)) {

     System.out.println("Successful operation");
   }
   else{
     System.out.println("Failed operation");
   }
  }
  static void removeProduct(Scanner in, Store store){
    System.out.println("Enter Product ID ");
    int id = readInt(in);
    if(store.deleteProductEverywhere(id)) {
      System.out.println("Successful operation");
    }
    else{
      System.out.println("Failed operation");
    }
  }
  static void searchProductByID(Scanner in, Store store){
    System.out.println("Enter Product ID ");
    int id = readInt(in);
   store.searchProductsById(id);

  }
  static void createOrder(Scanner in, Store store){
    System.out.println("Enter name of customer");
    String name = readString(in);
    if(store.createOrder(name))
      System.out.println("Successful operation");
    else
      System.out.println("Failed operation");

  }
  static void addItemToOrder(Scanner in, Store store){
    System.out.println("Enter Order ID ");
    int orderId = readInt(in);

    System.out.println("Enter Product ID ");
    int productId = readInt(in);

    System.out.println("Enter Quantity ");
    int quantity = readInt(in);

    if(store.addItem(orderId,productId,quantity))
      System.out.println("Successful operation");
    else
      System.out.println("Failed operation");
  }
  static void removeItemFromOrder(Scanner in, Store store){
    System.out.println("Enter Order ID ");
    int orderId = readInt(in);
    System.out.println("Enter Product ID ");
    int productId = readInt(in);

    if(store.removeItem(orderId,productId))
      System.out.println("Successful operation");
    else
      System.out.println("Failed operation");
  }
  static void displayOrder(Scanner in, Store store){
    System.out.println("Enter Order ID ");
    int orderId = readInt(in);
    store.displayOrderById(orderId);

  }
  static void addOrderToTheShippingList(Scanner in, Store store){
    System.out.println("Enter Order ID ");
    int orderId = readInt(in);
    if(store.addToShipping(orderId))
      System.out.println("Successful operation");
    else
      System.out.println("Failed operation");
  }
  static void cancelOrder(Scanner in, Store store){
    System.out.println("Enter Order ID ");
    int orderId = readInt(in);
    if(store.cancelOrder(orderId))
      System.out.println("Successful operation");
    else
      System.out.println("Failed operation");
  }
  static void searchOrderByID(Scanner in, Store store){
    System.out.println("Enter Order ID ");
    int orderId = readInt(in);
    if(store.searchOrderById(orderId))
      System.out.println("Successful operation");
    else
      System.out.println("Failed operation");
  }
  static void addReviewToProduct(Scanner in, Store store){
    System.out.println("Enter Product ID ");
    int productId = readInt(in);
    System.out.println("Enter Customer Name ");
    String name = readString(in);
    System.out.println("Enter Review ");
    String review = readString(in);
    if(store.addReview(productId,name,review))
      System.out.println("Successful operation");
    else
      System.out.println("Failed operation");
  }
  static void showAllReviews(Scanner in, Store store){
    System.out.println("Enter product ID ");
    int productId = readInt(in);
    store.showReviews(productId);
  }
  static void exit(){
    System.out.println("*************************************");
    System.out.println("Thank you for using our application");
    System.out.println("Goodbye!");
    System.out.println("**************************************");

  }









}
