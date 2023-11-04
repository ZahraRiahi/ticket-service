package com.ticketsystem.mapper;

import com.ticketsystem.model.domain.Comment;
import com.ticketsystem.model.dto.CommentDTO;
import com.ticketsystem.service.TicketService;
import com.ticketsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    private final TicketService ticketService;
    private final UserService userService;

    public CommentMapper(TicketService ticketService,
                         UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public Comment toEntity(CommentDTO dto) {
        Comment entity = new Comment();
        entity.setBody(dto.getBody());
        entity.setTicket(ticketService.getOne(dto.getTicketId()));
        entity.setCreator(userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return entity;
    }

    public CommentDTO toDTO(Comment entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setBody(entity.getBody());
        dto.setTicketId(entity.getTicket().getId());
        dto.setCreatorId(entity.getCreator().getId());
        return dto;
    }
}
