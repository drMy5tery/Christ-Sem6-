package com.example.myapplication.admin;

public class admin_details {
    String name,organization,password,uid;




    public admin_details(String name, String organization, String password,String uid) {
        this.name = name;
        this.organization = organization;
        this.password = password;
        this.uid=uid;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
