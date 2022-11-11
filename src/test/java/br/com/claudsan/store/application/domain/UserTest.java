package br.com.claudsan.store.application.domain;

import br.com.claudsan.store.adapter.dto.ItemViewDTO;
import br.com.claudsan.store.adapter.dto.UserViewDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static br.com.claudsan.store.moks.DomainMocks.*;

public class UserTest {

    @Test
    void testProperties(){
        User user = getUser();
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getUserId(), USER_ID);
        Assertions.assertEquals(user.getName(), USER_NAME);
        Assertions.assertEquals(user.getEmail(), EMAIL);
        Assertions.assertEquals(user.toString(), STRING_USER);
    }

    @Test
    void testEqualTo(){
        User user = getUser();
        User user2 = new User(USER_ID, USER_NAME, EMAIL);
        Assertions.assertEquals(user, user2);
        Assertions.assertEquals(user.hashCode(), user2.hashCode());
        Assertions.assertTrue(user.equals(user2));

        User user3 = new User();
        user3.setUserId(USER_ID);
        user3.setEmail(EMAIL);
        user3.setName(USER_NAME);
        Assertions.assertEquals(user, user3);
        Assertions.assertTrue(user.equals(user3));
        Assertions.assertEquals(user.hashCode(), user3.hashCode());
    }

    @Test
    void testNotEqualTo(){
        User user = getUser();
        User user2 = new User(2L, USER_NAME, EMAIL);
        Assertions.assertNotEquals(user, user2);
        Assertions.assertNotEquals(user.hashCode(), user2.hashCode());
        Assertions.assertFalse(user.equals(user2));
    }

    @Test
    void testToDTO(){
        UserViewDTO dto = getUser().toDTO();
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(dto.getUserId(), USER_ID);
        Assertions.assertEquals(dto.getEmail(), EMAIL);
        Assertions.assertEquals(dto.getName(), USER_NAME);
    }
}

