package com.creditsuisse.orderboard

import spock.lang.Specification
import spock.lang.Subject

import static Order.createOrder
import static com.creditsuisse.orderboard.OrderDetails.Type.BUY
import static com.creditsuisse.orderboard.OrderDetails.Type.SELL
import static OrderDetails.createOrderDetails
import static java.math.BigDecimal.ONE
import static java.math.BigDecimal.TEN

class OrderBoardSpec extends Specification {
    @Subject
    def orderBoard = new OrderBoard()

    def "should register an order" () {
        given:
        def order = createOrder("test", createOrderDetails(ONE, TEN, BUY))

        when:
        orderBoard.registerOrder(order)

        then:
        orderBoard.summary == [order.getDetails()]
    }

    def "should cancel a registered order" () {
        given:
        def order = createOrder("test", createOrderDetails(ONE, TEN, BUY))
        orderBoard.registerOrder(order)

        when:
        orderBoard.cancelOrder(order)

        then:
        orderBoard.summary == []
    }

    def "should combine two orders at the same price for the summary" () {
        given:
        orderBoard.registerOrder(createOrder("user1", createOrderDetails(ONE, TEN, BUY)))
        orderBoard.registerOrder(createOrder("user2", createOrderDetails(TEN, TEN, BUY)))

        expect:
        orderBoard.summary == [createOrderDetails(BigDecimal.valueOf(11), TEN, BUY)]
    }

    def "should order BUY orders descending by unit price" () {
        given:
        orderBoard.registerOrder(createOrder("user1", createOrderDetails(ONE, ONE, BUY)))
        orderBoard.registerOrder(createOrder("user2", createOrderDetails(TEN, BigDecimal.valueOf(2), BUY)))
        orderBoard.registerOrder(createOrder("user2", createOrderDetails(ONE, TEN, BUY)))

        expect:
        orderBoard.summary == [
                createOrderDetails(ONE, BigDecimal.valueOf(10), BUY),
                createOrderDetails(TEN, BigDecimal.valueOf(2), BUY),
                createOrderDetails(ONE, BigDecimal.valueOf(1), BUY)
        ]
    }

    def "should order SELL orders ascending by unit price " () {
        given:
        orderBoard.registerOrder(createOrder("user1", createOrderDetails(ONE, ONE, SELL)))
        orderBoard.registerOrder(createOrder("user2", createOrderDetails(TEN, BigDecimal.valueOf(2), SELL)))
        orderBoard.registerOrder(createOrder("user2", createOrderDetails(ONE, TEN, SELL)))

        expect:
        orderBoard.summary == [
                createOrderDetails(ONE, BigDecimal.valueOf(1), SELL),
                createOrderDetails(TEN, BigDecimal.valueOf(2), SELL),
                createOrderDetails(ONE, BigDecimal.valueOf(10), SELL)
        ]
    }
}
