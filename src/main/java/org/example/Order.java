package org.example;

import java.util.ArrayList;
import java.util.List;


public class Order {
    private final int orderId;
    private static int counter = 0;
    private String customerName;
    private double total;
    private OrderStatus status;
    private final List<CartItem> cartItems = new ArrayList<>();


    public Order(String customerName) {
        this.orderId = counter++;
        this.customerName = customerName;
        this.total = 0;
        this.status = OrderStatus.PENDING;
    }


    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }


    public void addItem(CartItem item) {
        cartItems.add(item);
        calculateTotal();

    }

    public  void calculateTotal() {
        total = 0;
        for (CartItem item : cartItems) {
            total += item.calculateSubtotal();
        }
    }


    public boolean removeItemByProductId(int id){
        boolean removed = cartItems.removeIf(item->item.getProduct().getId() == id);
        if(removed){
            calculateTotal();
        }
        return removed;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void displayOrder() {
        System.out.println("Order: ");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Status: " + status);
        System.out.println("Items: ");

        if (cartItems.isEmpty()) {
            System.out.println("No items in this order");
        } else {
            for (CartItem item : cartItems) {
                System.out.println("- " + item.getProduct().getName() +
                        " x" + item.getQuantity() +
                        " = " + item.calculateSubtotal());
            }
        }

        System.out.println("Total Price: " + total);
        System.out.println("------------------------------------");
    }

    public void recalculateTotal() {
        calculateTotal();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
