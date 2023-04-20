package com.example.myapplication.super_admin;

public class admin_add_details {
    private String email,Uid;
    private int valid_status;
    public admin_add_details(String email,String Uid,int valid_status){
        this.email=email;
        this.Uid=Uid;
        this.valid_status=valid_status;
    }
    public String geteMail() {
        return email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public int getValid_status() {
        return valid_status;
    }

    public void setValid_status(int valid_status) {
        this.valid_status = valid_status;
    }

    public void seteMail(String eMail) {
        this.email = eMail;
    }
}

