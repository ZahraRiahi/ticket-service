package com.ticketsystem.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentDTO {
    @NotNull(message = "ticketId can't be null")
    private Long id;

    @NotNull(message = "comment body can't be null")
    private String body;

    private Long ticketId;

    private Long creatorId;

}
