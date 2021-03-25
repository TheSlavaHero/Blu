package com.blubank.entity;

public class ApplicationUserForm {
    private User user;
    private String confirmPassword;

    public ApplicationUserForm(User user, String confirmPassword) {
        this.user = user;
        this.confirmPassword = confirmPassword;
    }

    public ApplicationUserForm() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
