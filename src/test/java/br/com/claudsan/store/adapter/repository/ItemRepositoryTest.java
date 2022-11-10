package br.com.claudsan.store.adapter.repository;

import br.com.claudsan.store.application.domain.Item;
import br.com.claudsan.store.application.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;

    @Test
    void testCreatItem(){
        Item item = Item.builder().quantityAvaiable(200L).name("test item").build();
        repository.save(item);
        Item newItem = repository.findAll().iterator().next();
        assertNotNull(newItem);
        repository.delete(item);
    }

    @Test
    void errorOnFindItemNotExist(){
        assertThrows(NoSuchElementException.class, () -> repository.findById(9999L).get());
    }

    @Test
    void testFindAllPageable(){
        for (int i = 0; i < 5; i++) {
            repository.save(Item.builder().quantityAvaiable(200L).name(i+" test item").build());
        }

        Page<Item> result = repository.findAll(PageRequest.of(0, 5));
        assertEquals(result.getTotalElements(),5);
        assertEquals(result.getTotalPages(), 1);
        assertEquals(result.getSize(), 5);

        repository.findAll().forEach(item -> repository.delete(item));
    }
}
