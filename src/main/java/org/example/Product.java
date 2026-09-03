package org.example;

public class Product implements Comparable<Product>{
    private final int id;
    private final String name;
    private Double price;
    private String category;
    private int stockQuantity;
    private static int countId =1000;
    public Product(String name, double price , String category, int stockQuantity) {
        this.id = countId++;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public  double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


    public void detailsProduct(){
        System.out.println("ID: "+id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price);
        System.out.println("Category: " + category);
        System.out.println("Stock Quantity: " + stockQuantity);
    }


    @Override
    public int compareTo(Product o) {
        return Double.compare(this.price, o.price);
    }
}
