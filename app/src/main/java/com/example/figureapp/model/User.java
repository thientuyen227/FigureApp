package com.example.figureapp.model;

public class User extends AbstractModel<User> {
    private String name;
    private String userName;
    private String email;

    private String idCard;
    private String avatar;
    private int eWallet;

    public User(String name, String email, String idCard, String avatar, int eWallet) {
        super();
        this.name = name;
        this.email = email;
        this.idCard = idCard;
        this.avatar = avatar;
        this.eWallet = eWallet;
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

    public User( String idCard, String avatar, int eWallet) {
        this.idCard = idCard;
        this.avatar = avatar;
        this.eWallet = eWallet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String email) {
        this.email = email;
    }

    public User(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    private String password;


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
