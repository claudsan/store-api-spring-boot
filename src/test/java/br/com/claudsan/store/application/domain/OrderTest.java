package br.com.claudsan.store.application.domain;

import br.com.claudsan.store.adapter.dto.ItemViewDTO;
import br.com.claudsan.store.adapter.dto.OrderViewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static br.com.claudsan.store.moks.DomainMocks.*;

public class OrderTest {

    @Test
    void testProperties() {
        Order order = getOrder();
        Assertions.assertNotNull(order);
        Assertions.assertEquals(order.getOrderId(), ORDER_ID);
        Assertions.assertEquals(order.getQuantity(), QUANTITY);
        Assertions.assertEquals(order.getQuantityServed(), QUANTITY_SERVED);
        Assertions.assertEquals(order.toString(), STRING_ORDER);
    }

    @Test
    void testEqualTo() {
        Order order = getOrder();
        Order order2 = new Order(ORDER_ID, DATE_CREATE, QUANTITY, QUANTITY_SERVED, getItem(), getUser(), OrderStatus.PENDING, new ArrayList<>());
        Assertions.assertEquals(order, order2);
        Assertions.assertEquals(order.hashCode(), order2.hashCode());
        Assertions.assertTrue(order.equals(order2));
    }

    @Test
    void testNotEqualTo() {
        Order order = getOrder();
        Order order2 = new Order(2L, DATE_CREATE, QUANTITY, QUANTITY_SERVED, getItem(), getUser(), OrderStatus.PENDING, new ArrayList<>());
        Assertions.assertNotEquals(order, order2);
        Assertions.assertNotEquals(order.hashCode(), order2.hashCode());
        Assertions.assertFalse(order.equals(order2));
    }


    @Test
    void tesChangeOrderStatusToFinish() {
        Order order = getOrder();
        Assertions.assertEquals(order.getOrderStatus(), OrderStatus.PENDING);
        order.setQuantity(QUANTITY_SERVED);
        Assertions.assertEquals(order.getOrderStatus(), OrderStatus.FINISHED);

        order = getOrder();
        Assertions.assertEquals(order.getOrderStatus(), OrderStatus.PENDING);
        order.setQuantityServed(QUANTITY);
        Assertions.assertEquals(order.getOrderStatus(), OrderStatus.FINISHED);

    }


    @Test
    void testToDTO() {
        OrderViewDTO dto = getOrder().toDTO();
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getOrderId(), ORDER_ID);
        Assertions.assertEquals(dto.getQuantity(), QUANTITY);
        Assertions.assertEquals(dto.getUser().getUserId(), OTHER_ID);
        Assertions.assertEquals(dto.getItem().getId(), OTHER_ID);
    }

}

