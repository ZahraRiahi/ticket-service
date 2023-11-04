package com.ticketsystem.mapper;

import com.ticketsystem.model.domain.Ticket;
import com.ticketsystem.model.dto.TicketDTO;
import com.ticketsystem.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    private final UserService userService;

    public TicketMapper(UserService userService) {
        this.userService = userService;
    }

    public Ticket toEntity(TicketDTO dto) {
        Ticket entity = new Ticket();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setCustomer(userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        entity.setSupporter(userService.getOne(dto.getSupporterId()));
        return entity;
    }

    public TicketDTO toDTO(Ticket entity) {
        TicketDTO dto = new TicketDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setCustomerId(entity.getCustomer().getId());
        dto.setSupporterId(entity.getSupporter().getId());
        return dto;
    }
}
