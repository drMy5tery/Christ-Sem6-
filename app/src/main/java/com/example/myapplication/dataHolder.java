package com.example.myapplication;

public class dataHolder {
    String name,organization,password,email,uniq;
    int valid_status;
    public String getUniq() {
        return uniq;
    }

    public void setUniq(String uniq) {
        this.uniq = uniq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String uid) {
        this.email = email;
    }

    public int getValid_status() {
        return valid_status;
    }

    public void setValid_status(int valid_status) {
        this.valid_status = valid_status;
    }

    public dataHolder(String name, String organization, String password, String email, String uniq, int valid_status) {
        this.name = name;
        this.organization = organization;
        this.password = password;
        this.email=email;
        this.uniq=uniq;
        this.valid_status=valid_status;
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
