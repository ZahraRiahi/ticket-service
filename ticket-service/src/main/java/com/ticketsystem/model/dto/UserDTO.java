package com.ticketsystem.model.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    private Long id;
    @NotNull(message = "username can't be null")
    private String username;
    @NotNull(message = "username can't be null")
    private String password;
    private String role;

}
