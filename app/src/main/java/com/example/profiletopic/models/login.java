package com.example.profiletopic.models;

public class login {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String email;

    public login(String email, String password, String fullname, String address, String phonnumer, String image) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
        this.phonnumer = phonnumer;
        this.image = image;
    }

    private String password;
    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonnumer() {
        return phonnumer;
    }

    public void setPhonnumer(String phonnumer) {
        this.phonnumer = phonnumer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String address;
    private String phonnumer;
    private String image;

}
