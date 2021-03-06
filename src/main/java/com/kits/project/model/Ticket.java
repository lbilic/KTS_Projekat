package com.kits.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kits.project.DTOs.TicketDTO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Lupus on 10/30/2018.
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="TICKETS")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JsonBackReference
    private User user;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column
    private boolean active;

    @Column
    private double price;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    public Ticket() {
    }

    public Ticket(User user, Date startTime, Date endTime, boolean active) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public Ticket(TicketDTO ticketDTO) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
