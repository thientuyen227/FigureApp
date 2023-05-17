package com.example.figureapp.model;

public class User extends AbstractModel<User> {
    private String name;
    private String userName;
    private String email;

    private String idCard;
    private String avatar;
    private int eWallet;
    private String role;
    private String password;

    public User(String name, String password, String role , String email, String idCard, int eWallet) {
        this.name = name;
        this.email = email;
        this.idCard = idCard;
        this.eWallet = eWallet;
        this.role = role;
        this.password = password;
    }

    public User(String username, String email, String password, String name) {
        super();
        this.userName = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String name , String avatar, String email, String idCard, int eWallet, String role, String password) {
        super();
        this.name = name;
        this.email = email;
        this.idCard = idCard;
        this.avatar =avatar;
        this.eWallet = eWallet;
        this.role = role;
        this.password = password;
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getEWallet() {
        return eWallet;
    }

    public void setEWallet(int eWallet) {
        this.eWallet = eWallet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
