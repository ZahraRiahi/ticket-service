package com.ticketsystem;


import com.ticketsystem.model.domain.User;
import com.ticketsystem.model.dto.ListDTO;
import com.ticketsystem.model.dto.UserDTO;
import com.ticketsystem.model.enumeration.Role;
import com.ticketsystem.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit_test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    @Order(1)
    public void save() {
        String username = "admin";
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRole(Role.ADMIN.getValue());
        userDTO.setPassword("admin123");

        User response = userService.save(userDTO);

        assertNotNull(response);
        assertEquals(username, response.getUsername());
        assertEquals(Role.ADMIN.getValue(), response.getRole());
    }

    @Test
    @Order(2)
    public void saveToken() {
        String token = "test";
        User user = userService.saveToken("admin", token);
        assertEquals(token, user.getToken());
        assertNotNull(user.getToken());
    }

    @Test
    @Order(3)
    public void getOne() {
        User response = userService.getOne(2L);

        assertNotNull(response);
        assertEquals(Role.ADMIN.getValue(), response.getRole());
        assertEquals("admin", response.getUsername());
    }

    @Test
    @Order(4)
    public void changeRole() {
        User user = userService.changeRole(1L, Role.SUPPORTER.getValue());

        assertEquals(Role.SUPPORTER.getValue(), user.getRole());
    }

    @Test
    @Order(5)
    public void getAll() {
        int page = 0;
        int size = 10;
        ListDTO list = userService.getAll(page, size);

        assertEquals(list.getData().size(), 2);
        assertFalse(list.getData().isEmpty());
        assertNotNull(list);

    }

    @Test
    @Order(6)
    public void getByUsername() {
        User user = userService.getByUsername("admin");

        assertNotNull(user);
        assertEquals(Role.ADMIN.getValue(), user.getRole());
    }


}
