package com.ticketsystem.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TicketDTO {
    private Long id;

    @NotNull(message = "title can't be null")
    private String title;

    @NotNull(message = "description can't be null")
    private String description;

    private Boolean status;

    private Long customerId;

    private Long supporterId;

}
