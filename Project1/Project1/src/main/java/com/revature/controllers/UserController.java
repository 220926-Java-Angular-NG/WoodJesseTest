package com.revature.controllers;

import com.revature.Driver;
import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.http.Handler;

public class UserController {
    private UserService service;

    public UserController(UserService service) { this.service = service; }
    public UserController() { this(new UserService()); }
    public Handler createNewUser = context -> {
        if (Driver.getLogin() == null) {
            User newUser = context.bodyAsClass(User.class);
            int id = service.createUser(newUser);
            if (id > 0) {
                Driver.setLogin(newUser);
                context.json(service.getUserByID(id)).status(200);
            } else {
                context.result("Unable to create user.").status(500);
            }
        } else {
            context.result("Please logout first.").status(400);
        }
    };
    public Handler getUserByUsername = context -> {

    };
    public Handler updateUser = context -> {

    };
    public Handler deleteUser = context -> {

    };
    public Handler login = context -> {
        if (Driver.getLogin() == null) {
            User loginUser = context.bodyAsClass(User.class);
            User recordUser = service.getUser(loginUser.getUsername());
            if (recordUser != null && recordUser.getPassword().equals(loginUser.getPassword())) {
                Driver.setLogin(recordUser);
                context.json(recordUser).status(200);
            } else {
                context.result("Invalid login.").status(400);
            }
        } else {
            context.result("Already logged in. Logout first.").status(400);
        }
    };
    public Handler logout = context -> {
        if (Driver.getLogin() != null) {
            Driver.setLogin(null);
            context.result("Successfully logged out.").status(200);
        } else {
            context.result("Already logged out.").status(400);
        }
    };
}
