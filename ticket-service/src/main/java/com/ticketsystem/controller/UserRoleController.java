package com.ticketsystem.controller;

import com.ticketsystem.jwtUtill.JwtTokenUtil;
import com.ticketsystem.model.domain.User;
import com.ticketsystem.model.dto.ListDTO;
import com.ticketsystem.model.dto.TicketDTO;
import com.ticketsystem.model.dto.UserDTO;
import com.ticketsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = UserRoleController.PATH)
@Tag(name = "UserRoleController", description = "مجموعه وب سرویسهای احرازهویت و ایجاد کاربر جدید و تعویض رولهای کاربران")
public class UserRoleController {
    public static final String PATH = "/user";
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public UserRoleController(UserService userService,
                              UserDetailsService userDetailsService,
                              JwtTokenUtil jwtTokenUtil,
                              AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/authenticate")
    @Operation(summary = "احراز هویت", description = "این متد برای احراز هویت  استفاده می شود")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody UserDTO userDTO) {

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());

        try {
            authenticate(userDTO.getUsername(), userDTO.getPassword());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.OK).body("Login Failed");
        }
        final String token = jwtTokenUtil.generateToken(userDetails);
        userService.saveToken(userDTO.getUsername(), token);

        return ResponseEntity.ok(token);

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "ایجاد کاربر جدید", description = "این متد برای ایجاد کاربر جدید استفاده می شود")
    public ResponseEntity<TicketDTO> save(@RequestBody @Valid UserDTO request) {
        User user = userService.save(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/changeRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "تعویض رول کاربر", description = "این متد برای تعویض رولهای کاربران استفاده می شود")
    public ResponseEntity<String> changeRole(@RequestParam("userId") Long userId, @RequestParam("role") String role) {
        userService.changeRole(userId, role);
        return ResponseEntity.ok("user role updated");
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "واکشی همه کاربران", description = "این متد برای واکشی همه کاربران استفاده می شود")
    public ResponseEntity<ListDTO> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        ListDTO responseList = userService.getAll(pageNumber, pageSize);
        return ResponseEntity.ok().body(responseList);
    }

}
