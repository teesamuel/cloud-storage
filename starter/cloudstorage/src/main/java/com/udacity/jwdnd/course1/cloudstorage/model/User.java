package com.udacity.jwdnd.course1.cloudstorage.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class User {


    private Integer userId;
    @NotBlank(message = "username cannot be empty")
    private String username;
    private String salt;
    @Length(min = 5, max = 20,message = "Length of password should be between 5 to 20")
    private String password;
    private String firstname;
    private String lastname;

    public User(Integer userId, String username, String salt, String password, String firstname, String lastname) {
        this.userId = userId;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}

