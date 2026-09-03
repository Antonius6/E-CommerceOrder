package org.example;

import java.util.*;

public class Store {


    private final Map<Integer, Product> products = new LinkedHashMap<>();



    private final Queue<Order> waitingForShippingOrders = new  LinkedList<>() ;
    private final List<Order> deliveredOrder = new ArrayList<>();


    private final Set<String> categories = new HashSet<>();
    private final List<Review> reviews = new ArrayList<>();

    private final Map<Integer, Order> permanentOrder = new HashMap<>();


    public boolean addProduct(String name, double price, String category, int quantity) {

        if (name.isBlank()) {
            System.out.println("Name cannot be empty or blank");
            return false;
        }

        if (price <= 0) {
            System.out.println("Price cannot be negative or zero");
            return false;
        }

        if (category.isBlank()) {
            System.out.println("Category cannot be empty or blank");
            return false;
        }
        if (quantity <= 0) {
            System.out.println("Quantity cannot be negative or zero");
            return false;
        }

        Product product = new Product(name, price, category, quantity);

        products.put(product.getId(), product);
        categories.add(category);
        System.out.println("Product with ID " + product.getId() + " added successfully");
        return true;
    }


    public boolean deleteProductEverywhere(int id) {
        Product product = searchProduct(id);
        if (product == null) {
            System.out.println("Product with ID " + id + " not found");
            return false;
        }

        products.remove(id);
        return true;

    }

    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("No products found");
        } else {

            for (Product product : products.values()) {
                product.detailsProduct();
                System.out.println("------------------------");
            }
        }


    }


    public Product searchProduct(int id) {
        return products.get(id);
    }


    public void searchProductsById(int id) {
        Product product = searchProduct(id);
        if (product == null) {
            System.out.println("Product with ID " + id + " not found");
        } else {
            product.detailsProduct();
            System.out.println("-------------------------");
        }

    }


    public void showCategories() {
        if (categories.isEmpty()) {
            System.out.println("No categories found");
        } else {
            System.out.println("Categories in the shop : ");
            for (String category : categories) {
                System.out.println(category);

            }
            System.out.println("-------------------------");
        }
    }


    public void displayProductsOrderedByPrice() {
        if (products.isEmpty()) {
            System.out.println("No Products found");
        } else {

            List<Product> copyProducts = new ArrayList<>(products.values());
            ;
            Collections.sort(copyProducts);
            for (Product product : copyProducts) {
                product.detailsProduct();
                System.out.println("-----------------------");
            }
        }
    }


    public boolean createOrder(String customerName) {
        if (customerName.isBlank()) {
            System.out.println("Name cannot be empty or blank");
            return false;
        }
        Order order = new Order(customerName);;
        permanentOrder.put(order.getOrderId(), order);
        return true;

    }

    public boolean addItem(int orderId, int productId, int quantity) {

        Order order = permanentOrder.get(orderId);
        Product product = products.get(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found");
            return false;
        }
        if (order == null) {
            System.out.println("Order with ID " + orderId + " not found");
            return false;
        }

        if (quantity <= 0) {
            System.out.println("Quantity cannot be negative or zero");
            return false;
        }

        if (order.getStatus() == OrderStatus.SHIPPED ||
                order.getStatus() == OrderStatus.DELIVERED ||
                order.getStatus() == OrderStatus.CANCELLED) {
            System.out.println("Cannot add items to an order with status " + order.getStatus());
            return false;
        }

        if (product.getStockQuantity() < quantity) {
            System.out.println("Not enough stock for product " + product.getName());
            return false;
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);

        for (CartItem item : order.getCartItems()) {
            if (item.getProduct().getId() == productId) {
                item.increaseQuantity(quantity);
                order.recalculateTotal();
                return true;

            }
        }
        CartItem newItem = new CartItem(product, quantity);
        order.addItem(newItem);
        return true;
    }

    public boolean removeItem(int orderId, int productId){

        Order order =  permanentOrder.get(orderId);
        Product product = products.get(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found");
            return false;
        }
        if (order == null) {
            System.out.println("Order with ID " + orderId + " not found");
            return false;
        }
        if(order.getStatus() == OrderStatus.CANCELLED
                || order.getStatus() == OrderStatus.SHIPPED
                ||  order.getStatus() == OrderStatus.DELIVERED){
            System.out.println("Cannot remove items from an order with status " + order.getStatus());
            return false;
        }
        if (!order.removeItemByProductId(productId)) {
            System.out.println("Product with ID " + productId + " not found in this order");
            return false;
        }
        return true;

    }

    public boolean addToShipping(int orderId){
        Order order = permanentOrder.get(orderId);

        if (order == null) {
            System.out.println("Order with ID " + orderId + " not found");
            return false;
        }

        if(order.getStatus() == OrderStatus.CANCELLED
                ||  order.getStatus() == OrderStatus.DELIVERED){
            System.out.println("Cannot add an order with status " + order.getStatus() + " to shipping list");
            return false;
        }
        if (order.getCartItems().isEmpty()) {
            System.out.println("Cannot add empty order to shipping list");
            return false;
        }

        if (waitingForShippingOrders.contains(order)) {
            System.out.println("Order " + orderId + " is already in the shipping list");
            return false;
        }


        waitingForShippingOrders.offer(order);
        order.updateStatus(OrderStatus.SHIPPED);
        return true;
    }

    public void nextOrder(){

        if (waitingForShippingOrders.isEmpty()) {
            System.out.println("No shipped orders found");
            return ;
        }
        Order order = waitingForShippingOrders.peek();

        if (order.getCartItems().isEmpty()) {
            System.out.println("This Order " + order.getOrderId() + " has no items and cannot be shipped");
            return ;
        }

        if (order.getStatus() == OrderStatus.DELIVERED) {
            System.out.println("Order " + order.getOrderId() + " is already in the delivery list");
            return ;
        }

        waitingForShippingOrders.poll();
        order.updateStatus(OrderStatus.DELIVERED);
        deliveredOrder.add(order);

    }

    public boolean cancelOrder(int orderId){
        if(permanentOrder.isEmpty()){
            System.out.println("No permanent orders found");
            return false;
        }
        Order order = permanentOrder.get(orderId);
        if (order == null) {
            System.out.println("Order with ID " + orderId + " not found");
            return false;
        }

        if(order.getStatus() ==OrderStatus.CANCELLED ||
                order.getStatus() == OrderStatus.DELIVERED){
            System.out.println("Order is already "+order.getStatus() );
            return false;
        }

        if (order.getStatus() == OrderStatus.SHIPPED) {
            waitingForShippingOrders.remove(order);

        }

        order.updateStatus(OrderStatus.CANCELLED);

        return true;



    }

    public boolean searchOrderById(int id) {
        Order order = permanentOrder.get(id);
        if( permanentOrder.isEmpty()){
            System.out.println("No permanent orders found");
            return false;
        }
        if (order == null) {
            System.out.println("Order with ID " + id + " not found");
            return false;

        }
        order.displayOrder();;
        return true;
    }

    public  boolean addReview(int productId,String name, String review){

        Product product = products.get(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found");
            return false;
        }
        if (name.isBlank()) {
            System.out.println("Name is blank");
            return false;
        }
        if (review.isBlank()) {
            System.out.println("Review is blank");
            return false;
        }

        Review review1 = new Review(productId,name,review);
        reviews.add(review1);
        return true;

    }
    public void showReviews(int ProductId){
        boolean found = false;

        for (Review review : reviews) {
            if(review.getProductId() == ProductId){
                System.out.println("Review by " + review.getCustomerName() + ":");
                System.out.println(review.getComment());
                System.out.println("---------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No reviews found for product ID " + ProductId );
        }


    }





    public void removeOutOfStock(){

        if(products.isEmpty()){
            System.out.println("No products in order");
            return;
        }
        Iterator<Map.Entry<Integer,Product>> iterator = products.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<Integer,Product> entry = iterator.next();
            Product product = entry.getValue();

            if(product.getStockQuantity() ==0){
                System.out.println("Product with ID " + entry.getKey() + " has been deleted");
                    iterator.remove();
            }
        }



    }


    public void displayOrdersByTotal(){
        if(permanentOrder.isEmpty()){
            System.out.println("No orders found");
            return;
        }
        List<Order>ordersList=  new ArrayList<>(permanentOrder.values());
        Comparator<Order>comparator=Comparator.comparing(Order::getTotal);
        ordersList.sort(comparator);
        for (Order order : ordersList) {
            order.displayOrder();
            System.out.println("----------------------------");
        }
    }

    public void displayOrderById(int id){
        if(permanentOrder.isEmpty()){
            System.out.println("No orders found");
            return;
        }
        Order order = permanentOrder.get(id);
        if(order == null){
            System.out.println("Order with ID " + id + " not found");
            return;
        }
        order.displayOrder();
    }




}
