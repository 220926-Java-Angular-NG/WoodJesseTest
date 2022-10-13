package com.revature.controllers;

import com.revature.Driver;
import com.revature.models.Ticket;
import com.revature.models.User;
import com.revature.services.TicketService;
import io.javalin.http.Handler;

public class TicketController {
    private TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }
    public TicketController() {
        this(new TicketService());
    }
    public Handler createTicket = context -> {
        if (Driver.getLogin() != null && !Driver.getLogin().isFinanceManager()) {
            Ticket ticket = context.bodyAsClass(Ticket.class);
            ticket.setStatus(Ticket.Status.Pending);
            ticket.setCreatorID(Driver.getLogin().getId());
            int id = service.createTicket(ticket);
            if (id > 0) {
                ticket.setId(id);
                context.json(ticket).status(200);
            } else {
                context.result("Could not create ticket.").status(500);
            }
        } else {
            context.result("Invalid account.").status(400);
        }
    };
    public Handler mainScreen = context -> {
        User session = Driver.getLogin();
        if (session != null) {
            if (session.isFinanceManager()) {
                context.json(service.getTicketsByStatus(Ticket.Status.Pending)).status(200);
            } else {
                context.json(session).status(200);
            }
        } else {
            context.result("Please login first.").status(400);
        }
    };
    public Handler processTicket = context -> {
        User session = Driver.getLogin();
        if (session != null && session.isFinanceManager()) {
            Ticket toUpdate = context.bodyAsClass(Ticket.class);
            Ticket currentState = service.getTicketByID(toUpdate.getId());
            if (
                    currentState.getStatus() == Ticket.Status.Pending
                    && currentState.getAmount() == toUpdate.getAmount()
                    && currentState.getDescription().equals(toUpdate.getDescription())
                    && currentState.getCreatorID() == toUpdate.getCreatorID()
                    && (toUpdate.getStatus() == Ticket.Status.Approved || toUpdate.getStatus() == Ticket.Status.Denied)
            ) {
                service.updateTicket(toUpdate);
                context.json(toUpdate).status(200);
            } else {
                context.result("Cannot update this ticket.").status(400);
            }
        } else {
            context.result("Invalid credentials.").status(400);
        }
    };
    public Handler myTickets = context -> {
        User session = Driver.getLogin();
        if (session != null && !session.isFinanceManager()) {
            context.json(service.getTicketsByCreator(session.getId())).status(200);
        } else if (session != null) {
            User targetUser = context.bodyAsClass(User.class);
            context.json(service.getTicketsByCreator(targetUser.getId())).status(200);
        } else {
            context.result("Invalid credentials.").status(400);
        }
    };
}
