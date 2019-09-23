package com.creditsuisse.orderboard;

import java.math.BigDecimal;
import java.util.Objects;

public final class OrderDetails {
    private final BigDecimal quantity;
    private final BigDecimal unitPrice;
    private final Type type;

    public enum Type {
        BUY, SELL
    }

    public static OrderDetails createOrderDetails(BigDecimal quantity, BigDecimal unitPrice, Type type) {
        return new OrderDetails(quantity, unitPrice, type);
    }

    private OrderDetails(BigDecimal quantity, BigDecimal unitPrice, Type type) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.type = type;
    }

    BigDecimal getQuantity() {
        return quantity;
    }

    BigDecimal getUnitPrice() {
        return unitPrice;
    }

    Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return quantity.equals(that.quantity) &&
                unitPrice.equals(that.unitPrice) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, unitPrice, type);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", type=" + type +
                '}';
    }
}
