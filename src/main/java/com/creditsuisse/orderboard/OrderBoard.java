package com.creditsuisse.orderboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OrderBoard {
    private final List<Order> orders = new ArrayList<>();

    public void registerOrder(Order order) {
        orders.add(order);
    }

    public void cancelOrder(Order order) {
        orders.remove(order);
    }

    public List<OrderDetails> getSummary() {
        return groupByUnitPrice(orders.stream())
                .values().stream()
                .map(this::aggregateQuantities)
                .sorted(this::compareUnitPrice)
                .collect(Collectors.toUnmodifiableList());
    }

    private Map<BigDecimal, List<OrderDetails>> groupByUnitPrice(Stream<Order> orderStream) {
        return orderStream
                .map(Order::getDetails)
                .collect(Collectors.groupingBy(OrderDetails::getUnitPrice));
    }

    @SuppressWarnings({"OptionalGetWithoutIsPresent"})
    private OrderDetails aggregateQuantities(List<OrderDetails> orderDetails) {
        return orderDetails.stream()
                .reduce((o1, o2) -> OrderDetails.createOrderDetails(o1.getQuantity().add(o2.getQuantity()), o1.getUnitPrice(), o1.getType())).get();
    }

    private int compareUnitPrice(OrderDetails o1, OrderDetails o2) {
        int result = o1.getType().compareTo(o2.getType());
        if (result == 0) {
            switch (o1.getType()) {
                case SELL:
                    result = o1.getUnitPrice().compareTo(o2.getUnitPrice());
                    break;
                case BUY:
                    result = o2.getUnitPrice().compareTo(o1.getUnitPrice());
                    break;
            }
        }
        return result;
    }
}
