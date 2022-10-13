package com.revature.models;

import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String password;
    private boolean financeManager;

    public User(int id, String username, String password, boolean financeManager) {
        this(username, password, financeManager);
        this.id = id;
    }

    public User(String username, String password, boolean financeManager) {
        this(username, password);
        this.financeManager = financeManager;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User() {}

    public void setId(int id) { this.id = id; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setFinanceManager(boolean financeManager) { this.financeManager = financeManager; }

    public int getId() { return id; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public boolean isFinanceManager() { return financeManager; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return financeManager == user.financeManager && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, financeManager);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", financeManager=" + financeManager +
                '}';
    }

}
