package sample.model;

import sample.utils.IDGenerator;

public class User {
    private int id;
    private String firstName;
    private String surName;
    private String userName;
    private String password;
    private int idRol;



    public User(int id,String firstName, String surName, String userName, String password, int idRol) {
        this.firstName = firstName;
        this.surName = surName;
        this.userName = userName;
        this.password = password;
        this.idRol = idRol;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
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

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
