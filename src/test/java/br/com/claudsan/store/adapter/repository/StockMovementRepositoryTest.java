package br.com.claudsan.store.adapter.repository;

import br.com.claudsan.store.application.domain.Item;
import br.com.claudsan.store.application.domain.StockMovement;
import br.com.claudsan.store.application.domain.TypeMovementStock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class StockMovementRepositoryTest {

    @Autowired
    StockMovmentRepository repository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void testCreatItem(){
        Item item = Item.builder().quantityAvaiable(200L).name("test item").build();
        itemRepository.save(item);

        StockMovement stockMovement = StockMovement.builder().creationDate(new Date())
                .quantity(10L)
                .item(item)
                .typeMovement(TypeMovementStock.OUT)
                .build();
        repository.save(stockMovement);
        StockMovement newstockMovement = repository.findAll().iterator().next();
        assertNotNull(newstockMovement);
        repository.delete(newstockMovement);
        itemRepository.delete(item);
    }

    @Test
    void testFindAllPageable(){
        Item item = Item.builder().quantityAvaiable(200L).name("test item").build();
        itemRepository.save(item);

        StockMovement stockMovement = StockMovement.builder().creationDate(new Date())
                .quantity(10L)
                .item(item)
                .typeMovement(TypeMovementStock.OUT)
                .build();
        repository.save(stockMovement);

        Page<StockMovement> result = repository.findAll(PageRequest.of(0, 5));
        assertEquals(result.getTotalElements(),1);
        assertEquals(result.getTotalPages(), 1);
        assertEquals(result.getSize(), 5);

        repository.findAll().forEach(stock -> repository.delete(stock));
        itemRepository.delete(item);

    }
}
