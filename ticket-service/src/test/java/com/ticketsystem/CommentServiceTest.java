package com.ticketsystem;

import com.ticketsystem.model.domain.Comment;
import com.ticketsystem.model.dto.CommentDTO;
import com.ticketsystem.model.enumeration.Role;
import com.ticketsystem.service.CommentService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("unit_test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    TicketService ticketService;
    @Autowired
    UserService userService;

    private void setup() {
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
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBody("test");
        commentDTO.setTicketId(1L);
        commentDTO.setCreatorId(1L);
        Comment comment = commentService.saveOrUpdate(commentDTO);

        assertNotNull(comment);
        assertEquals("test", comment.getBody());
    }

    @Test
    @Order(2)
    public void getOne() {
        Comment comment = commentService.getOne(1L);
        assertNotNull(comment);
    }


}
