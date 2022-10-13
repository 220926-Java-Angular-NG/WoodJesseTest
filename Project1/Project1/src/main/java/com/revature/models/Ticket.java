package com.revature.models;

import java.util.Objects;

public class Ticket {

    public Ticket() {

    }

    public enum Status {
        Pending("pending"),
        Approved("approved"),
        Denied("denied");

        Status(String data) {
            DATA = data;
        }
        public final String DATA;
    }
    int id;
    double amount;
    String description;
    Status status;
    int creatorID;

    public Ticket(double amount, String description, Status status) {
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public Ticket(double amount, String description) {
        this.amount = amount;
        this.description = description;
        status = Status.Pending;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCreatorID() { return creatorID; }

    public void setCreatorID(int creatorID) { this.creatorID = creatorID; }

    @Override
    public String toString() {
        return "Ticket{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Double.compare(ticket.amount, amount) == 0 && description.equals(ticket.description) && status == ticket.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, description, status);
    }
}
