package com.ticketsystem;

import com.ticketsystem.model.domain.Ticket;
import com.ticketsystem.model.dto.ListDTO;
import com.ticketsystem.model.dto.TicketDTO;
import com.ticketsystem.model.enumeration.Role;
import com.ticketsystem.service.TicketService;
import com.ticketsystem.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("unit_test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketServiceTest {
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;

    public void setup() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.SUPPORTER.getValue()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "supporter",
                "supporter123",
                authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @Order(1)
    public void saveOrUpdate() {
        setup();
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTitle("test");
        ticketDTO.setDescription("description");
        ticketDTO.setStatus(true);
        ticketDTO.setSupporterId(1L);
        ticketDTO.setCustomerId(1L);

        Ticket ticket = ticketService.saveOrUpdate(ticketDTO);

        assertNotNull(ticket);
        assertEquals("test", ticket.getTitle());
        assertTrue(ticket.getStatus());
    }

    @Test
    @Order(2)
    public void getOne() {
        Ticket ticket = ticketService.getOne(1L);
        assertNotNull(ticket);
    }

    @Test
    @Order(3)
    public void getAll() {
        int page = 0;
        int size = 10;
        ListDTO all = ticketService.getAll(page, size);

        assertFalse(all.getData().isEmpty());
        assertEquals(2, all.getData().size());
    }

    @Test
    @Order(4)
    public void getAllBySupporterId() {
        int page = 0;
        int size = 10;
        ListDTO allBySupporterId = ticketService.getAllBySupporterId(1L, page, size);

        assertNotEquals(allBySupporterId.getData().size(), 0);
        assertNotNull(allBySupporterId);

    }

    @Test
    @Order(5)
    public void getAllByCustomerId() {
        int page = 0;
        int size = 10;
        ListDTO allByCustomerId = ticketService.getAllByCustomerId(1L, page, size);

        assertNotEquals(allByCustomerId.getData().size(), 0);
        assertNotNull(allByCustomerId);

    }


}
