package br.com.claudsan.store.moks;

import br.com.claudsan.store.application.domain.Item;
import br.com.claudsan.store.application.domain.Order;
import br.com.claudsan.store.application.domain.OrderStatus;
import br.com.claudsan.store.application.domain.User;

import java.util.ArrayList;
import java.util.Date;

public class DomainMocks {

    public static final String ITEM_NAME = "TEST ITEM";
    public static final Long ITEM_ID = 1L;
    public static final Long QUANTITY = 10L;

    public static final String STRING_ITEM = "Item(itemId=1, name=TEST ITEM, quantityAvaiable=10)";

    public static String USER_NAME = "TEST USER";
    public static Long USER_ID = 1L;
    public static String EMAIL = "test@test.com";

    public static String STRING_USER = "User(userId=1, name=TEST USER, email=test@test.com)";

    public static final Long ORDER_ID = 1L;
    public static final Long QUANTITY_ORDER = 10L;
    public static final Long QUANTITY_SERVED = 0L;

    public static final Long OTHER_ID = 1L;

    public static final Date DATE_CREATE = new Date();

    public static final String STRING_ORDER = "Order(orderId=1, creationDate=" + DATE_CREATE + ", quantity=10," +
            " quantityServed=0, item=Item(itemId=1, name=TEST ITEM, quantityAvaiable=10)," +
            " user=User(userId=1, name=TEST USER, email=test@test.com), orderStatus=PENDING, stockMovements=[])";


    public static Order getOrder() {
        return new Order(ORDER_ID, DATE_CREATE, QUANTITY, QUANTITY_SERVED, getItem(), getUser(), OrderStatus.PENDING, new ArrayList<>());
    }

    public static User getUser(){
        return new User(USER_ID, USER_NAME, EMAIL);
    }

    public static Item getItem(){
        return new Item(ITEM_ID, ITEM_NAME, QUANTITY);
    }

}
