package br.com.claudsan.store.adapter.repository;

import br.com.claudsan.store.application.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    void testCreateUser(){
        User user = User.builder().email("test@test.com").name("test user").build();
        repository.save(user);

        User userFinded = repository.findAll().iterator().next();

        assertEquals(userFinded.getUserId(), user.getUserId());
        assertEquals(userFinded.getEmail(),user.getEmail());
        assertEquals(userFinded.getName(),user.getName());

        repository.delete(user);
    }

    @Test
    void errorOnFindUserNotExist(){
        assertThrows(NoSuchElementException.class, () -> repository.findById(999L).get());
    }

    @Test
    void testFindAllPageable(){
        for (int i = 0; i < 10; i++) {
            repository.save(User.builder().email("test"+i+"@test.com").name(i+" test user").build());
        }

        Page<User> result = repository.findAll(PageRequest.of(0, 5));
        assertEquals(result.getTotalElements(),10);
        assertEquals(result.getTotalPages(), 2);
        assertEquals(result.getSize(), 5);

        repository.findAll().forEach(user -> repository.delete(user));
    }
}
