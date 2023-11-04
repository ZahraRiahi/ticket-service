package com.ticketsystem.controller;

import com.ticketsystem.model.domain.Ticket;
import com.ticketsystem.model.dto.ListDTO;
import com.ticketsystem.model.dto.TicketDTO;
import com.ticketsystem.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = TicketController.PATH)
@Tag(name = "TicketController", description = "مجموعه وب سرویسهای ایجاد و ویرایش و واکشی تیکتها")
public class TicketController {
    public static final String PATH = "/ticket";
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "ایجاد یا ویرایش وضعیت تیکت", description = "این متد برای ایجاد یا ویرایش تیکت استفاده می شود")
    public ResponseEntity<String> saveOrUpdate(@RequestBody @Valid TicketDTO request) {
        Ticket ticket = ticketService.saveOrUpdate(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticket.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "واکشی همه تیکت ها", description = "این متد برای واکشی همه تیکت ها استفاده می شود")
    public ResponseEntity<ListDTO> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        ListDTO responseList = ticketService.getAll(pageNumber, pageSize);
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/list/byCustomer/{customerId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "واکشی تیکت های یک مشتری خاص", description = "این متد برای واکشی تیکت های یک مشتری خاص استفاده می شود")
    public ResponseEntity<ListDTO> getAllByCustomerId(@PathVariable Long customerId,
                                                      @RequestParam(defaultValue = "0") int pageNumber,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        ListDTO responseList = ticketService.getAllByCustomerId(customerId, pageNumber, pageSize);
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/list/bySupporter/{supporterId}")
    @PreAuthorize("hasAuthority('SUPPORTER')")
    @Operation(summary = "واکشی تیکت های یک پشتیبان خاص", description = "این متد برای واکشی تیکت های یک پشتیبان خاص استفاده می شود")
    public ResponseEntity<ListDTO> getAllBySupporterId(@PathVariable Long supporterId,
                                                       @RequestParam(defaultValue = "0") int pageNumber,
                                                       @RequestParam(defaultValue = "10") int pageSize) {
        ListDTO responseList = ticketService.getAllBySupporterId(supporterId, pageNumber, pageSize);
        return ResponseEntity.ok().body(responseList);
    }


}
