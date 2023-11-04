package com.ticketsystem.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "supporter_id")
    private User supporter;

    @OneToMany(mappedBy = "ticket")
    private Collection<Comment> comments;

}
