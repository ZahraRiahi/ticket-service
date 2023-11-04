package com.ticketsystem.service;

import com.ticketsystem.exceptionHandler.RuleException;
import com.ticketsystem.mapper.TicketMapper;
import com.ticketsystem.model.domain.Ticket;
import com.ticketsystem.model.dto.ListDTO;
import com.ticketsystem.model.dto.TicketDTO;
import com.ticketsystem.model.projection.SupporterProjection;
import com.ticketsystem.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final TicketMapper ticketMapper;


    public TicketService(TicketRepository ticketRepository,
                         UserService userService,
                         TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.ticketMapper = ticketMapper;
    }

    public Ticket saveOrUpdate(TicketDTO ticketDTO) {
        Ticket ticket;

        if (ticketDTO.getId() != null) {
            ticket = getOne(ticketDTO.getId());
            ticket.setStatus(ticketDTO.getStatus());
        } else {
            SupporterProjection supporterWithLeastTickets = getSupporterWithLessTickets();
            ticketDTO.setSupporterId(supporterWithLeastTickets.getSupporterId());
            ticket = ticketMapper.toEntity(ticketDTO);
        }

        return ticketRepository.save(ticket);
    }


    private SupporterProjection getSupporterWithLessTickets() {
        List<SupporterProjection> supporterList = userService.getSupporterWithTotalTickets();

        return supporterList.stream()
                .min(Comparator.comparing(SupporterProjection::getTotal))
                .orElseThrow(() -> new RuleException("supporter not found"));
    }

    public Ticket getOne(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }


    public ListDTO getAll(int page, int size) {
        Page<Ticket> tickets = ticketRepository.findAll(PageRequest.of(page, size));
        ListDTO listDTO = new ListDTO();
        List<TicketDTO> data = tickets.stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
        listDTO.setData(data);
        listDTO.setTotal(tickets.getTotalElements());
        return listDTO;

    }

    public ListDTO getAllByCustomerId(Long customerId, int page, int size) {
        Page<Ticket> userTickets = ticketRepository.findAllByCustomerId(customerId, PageRequest.of(page, size));
        ListDTO listDTO = new ListDTO();
        List<TicketDTO> data = userTickets.stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
        listDTO.setData(data);
        listDTO.setTotal(userTickets.getTotalElements());
        return listDTO;

    }

    public ListDTO getAllBySupporterId(Long supporterId, int page, int size) {
        Page<Ticket> userTickets = ticketRepository.findAllBySupporterId(supporterId, PageRequest.of(page, size));
        ListDTO listDTO = new ListDTO();

        List<TicketDTO> data = userTickets.stream()
                .map(ticketMapper::toDTO)
                .collect(Collectors.toList());
        listDTO.setData(data);
        listDTO.setTotal(userTickets.getTotalElements());
        return listDTO;

    }

}
