package com.ticketsystem.repository;

import com.ticketsystem.model.domain.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findAllByCustomerId(Long customerId, Pageable pageable);

    Page<Ticket> findAllBySupporterId(Long supporterId, Pageable pageable);

}
