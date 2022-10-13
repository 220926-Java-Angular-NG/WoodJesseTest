package com.revature.repos;

import com.revature.Driver;
import com.revature.models.Ticket;
import com.revature.utils.CRUDDaoInterface;
import com.revature.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepo implements CRUDDaoInterface<Ticket> {
    private Connection conn;

    public TicketRepo() {
        try {
            conn = ConnectionManager.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int create(Ticket ticket) {
        String sql = "INSERT INTO tickets (id, amount, description, status, u_id) VALUES (default, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setDouble(1, ticket.getAmount());
            stmt.setString(2, ticket.getDescription());
            stmt.setString(3, ticket.getStatus().DATA);
            stmt.setInt(4, Driver.getLogin().getId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();

            return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tickets";

            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(Ticket.Status.valueOf(rs.getString("status")));
                ticket.setCreatorID(rs.getInt("u_id"));

                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tickets;
    }

    public List<Ticket> getTicketsByStatus(Ticket.Status status) {
        List<Ticket> tickets = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tickets WHERE status=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status.DATA);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(Ticket.Status.valueOf(rs.getString("status")));
                ticket.setCreatorID(rs.getInt("u_id"));

                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tickets;
    }

    public List<Ticket> getByCreator(int creatorID) {
        List<Ticket> tickets = new ArrayList<>();

        try {
            String sql = "SELECT * FROM tickets WHERE u_id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, creatorID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(Ticket.Status.valueOf(rs.getString("status")));
                ticket.setCreatorID(rs.getInt("u_id"));

                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tickets;
    }

    @Override
    public Ticket getByID(int id) {
        try {
            String sql = "SELECT * FROM users WHERE id=?";
            Ticket ticket = new Ticket();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ticket.setId(rs.getInt("id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(Ticket.Status.valueOf(rs.getString("status")));
                ticket.setCreatorID(rs.getInt("u_id"));

                return ticket;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Ticket update(Ticket ticket) {
        String sql = "UPDATE tickets SET amount = ?, description = ?, status = ?, u_id = ? WHERE id = ?";
        Ticket updatedTicket = new Ticket();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setDouble(1, ticket.getAmount());
            stmt.setString(2, ticket.getDescription());
            stmt.setString(3, ticket.getStatus().DATA);
            stmt.setInt(4, ticket.getCreatorID());
            stmt.setInt(5, ticket.getId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                updatedTicket.setId(rs.getInt("id"));
                updatedTicket.setAmount(rs.getDouble("amount"));
                updatedTicket.setDescription(rs.getString("description"));
                updatedTicket.setStatus(Ticket.Status.valueOf(rs.getString("status")));
                updatedTicket.setCreatorID(rs.getInt("u_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return updatedTicket;
    }

    @Override
    public boolean delete(Ticket ticket) {
        String sql = "DELETE FROM tickets WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(2, ticket.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
