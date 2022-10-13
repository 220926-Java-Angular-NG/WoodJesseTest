package com.revature.services;

import com.revature.models.User;
import com.revature.repos.UserRepo;

import java.util.List;

public class UserService {
    private final UserRepo repo;

    public UserService(UserRepo repo) { this.repo = repo; }
    public UserService() { this(new UserRepo()); }
    public int createUser(User user) { return repo.create(user); }
    public List<User> getAllUsers() { return repo.getAll(); }
    public User getUserByID(int id) { return repo.getByID(id); }
    public User getUser(String username) { return repo.getByUsername(username); }
    public boolean updateUser(User user) { return repo.update(user).equals(user); }
    public boolean deleteUser(User user) { return repo.delete(user); }
}
