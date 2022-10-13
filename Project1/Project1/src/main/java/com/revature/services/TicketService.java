package com.revature.services;

import com.revature.models.Ticket;
import com.revature.repos.TicketRepo;

import java.util.List;

public class TicketService {
    private final TicketRepo repo;

    public TicketService(TicketRepo repo) { this.repo = repo; }
    public TicketService() { this(new TicketRepo()); }
    public int createTicket(Ticket ticket) { return repo.create(ticket); }
    public List<Ticket> getAllTickets() { return repo.getAll(); }
    public List<Ticket> getTicketsByStatus(Ticket.Status status) { return repo.getTicketsByStatus(status); }
    public List<Ticket> getTicketsByCreator(int creatorID) { return repo.getByCreator(creatorID); }
    public Ticket getTicketByID(int id) { return repo.getByID(id); }
    public Ticket updateTicket(Ticket ticket) { return repo.update(ticket); }
    public boolean deleteTicket(Ticket ticket) { return repo.delete(ticket); }
}
