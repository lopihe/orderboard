package com.creditsuisse.orderboard;

public final class Order {
    private final String userId;
    private final OrderDetails details;

    public static Order createOrder(String userId, OrderDetails orderDetails) {
        return new Order(userId, orderDetails);
    }

    private Order(String userId, OrderDetails orderDetails) {
        this.userId = userId;
        this.details = orderDetails;
    }

    public OrderDetails getDetails() {
        return details;
    }
}
