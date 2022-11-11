package br.com.claudsan.store.application.domain;

import br.com.claudsan.store.adapter.dto.ItemViewDTO;
import br.com.claudsan.store.moks.DomainMocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static br.com.claudsan.store.moks.DomainMocks.getItem;

public class ItemTest {



    @Test
    void testProperties(){
        Item item = getItem();
        Assertions.assertNotNull(item);
        Assertions.assertEquals(item.getItemId(), DomainMocks.ITEM_ID);
        Assertions.assertEquals(item.getName(), DomainMocks.ITEM_NAME);
        Assertions.assertEquals(item.getQuantityAvaiable(), DomainMocks.QUANTITY);
        Assertions.assertEquals(item.toString(), DomainMocks.STRING_ITEM);
    }

    @Test
    void testEqualTo(){
        Item item1 = getItem();
        Item item2 = new Item(DomainMocks.ITEM_ID, DomainMocks.ITEM_NAME, DomainMocks.QUANTITY);
        Assertions.assertEquals(item1, item2);
        Assertions.assertEquals(item1.hashCode(), item2.hashCode());
        Assertions.assertTrue(item1.equals(item2));

        Item item3 = new Item();
        item3.setItemId(DomainMocks.ITEM_ID);
        item3.setQuantityAvaiable(DomainMocks.QUANTITY);
        item3.setName(DomainMocks.ITEM_NAME);
        Assertions.assertEquals(item1, item3);
        Assertions.assertTrue(item1.equals(item3));
        Assertions.assertEquals(item1.hashCode(), item3.hashCode());
    }

    @Test
    void testNotEqualTo(){
        Item item1 = getItem();
        Item item2 = new Item(2L, DomainMocks.ITEM_NAME, DomainMocks.QUANTITY);
        Assertions.assertNotEquals(item1, item2);
        Assertions.assertNotEquals(item1.hashCode(), item2.hashCode());
        Assertions.assertFalse(item1.equals(item2));
    }

    @Test
    void testToDTO(){
        ItemViewDTO dto = getItem().toDTO();
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getId(), DomainMocks.ITEM_ID);
        Assertions.assertEquals(dto.getQuantity(), DomainMocks.QUANTITY);
        Assertions.assertEquals(dto.getName(), DomainMocks.ITEM_NAME);
    }
}

