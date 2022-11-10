package br.com.claudsan.store.adapter.repository;

import br.com.claudsan.store.application.domain.Item;
import br.com.claudsan.store.application.domain.Order;
import br.com.claudsan.store.application.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository repository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    void testCreateUser(){
        User user = User.builder().email("test@test.com").name("test user").build();
        userRepository.save(user);

        Item item = Item.builder().quantityAvaiable(200L).name("test item").build();
        itemRepository.save(item);

        Order order = Order.builder().creationDate(new Date())
                .item(item)
                .user(user)
                .quantity(10L)
                .quantityServed(0L)
                .build();

        repository.save(order);

        Order orderFinded = repository.findAll().iterator().next();

        assertEquals(orderFinded.getUser().getUserId(), user.getUserId());
        assertEquals(orderFinded.getItem().getItemId(), item.getItemId());

        repository.delete(order);
        itemRepository.delete(item);
        userRepository.delete(user);
    }

    @Test
    void errorOnFindOrderNotExist(){
        assertThrows(NoSuchElementException.class, () -> repository.findById(9999L).get());
    }

    @Test
    void testFindAllPageable(){
        User user = User.builder().userId(1L).email("test@test.com").name("test user").build();
        userRepository.save(user);

        Item item = Item.builder().itemId(1L).quantityAvaiable(200L).name("test item").build();
        itemRepository.save(item);

        for (int i = 0; i < 10; i++) {
            repository.save(Order.builder().creationDate(new Date())
                    .item(Item.builder().itemId(item.getItemId()).build())
                    .user(User.builder().userId(user.getUserId()).build())
                    .quantity(10L)
                    .quantityServed(0L)
                    .build());
        }

        Page<Order> result = repository.findAll(PageRequest.of(0, 5));
        assertEquals(result.getTotalElements(),10);
        assertEquals(result.getTotalPages(), 2);
        assertEquals(result.getSize(), 5);

        repository.findAll().forEach(order -> repository.delete(order));
        itemRepository.delete(item);
        userRepository.delete(user);
    }
}
