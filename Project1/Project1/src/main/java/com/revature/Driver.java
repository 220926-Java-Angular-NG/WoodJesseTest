package com.revature;

import com.revature.controllers.TicketController;
import com.revature.controllers.UserController;
import com.revature.models.User;
import io.javalin.Javalin;

public class Driver {
    private static User login;
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        UserController userController = new UserController();
        TicketController ticketController = new TicketController();

        app.post("/user/signup", userController.createNewUser);
        app.post("/user/login", userController.login);
        app.post("/user/logout", userController.logout);
        app.post("/tickets/create", ticketController.createTicket);
        app.get("/", ticketController.mainScreen);
        app.put("/tickets/process", ticketController.processTicket);
        app.get("/tickets/creator", ticketController.myTickets);

    }

    public static void setLogin(User login) {
        Driver.login = login;
    }

    public static User getLogin() {
        return login;
    }
}
