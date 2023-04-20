package com.example.myapplication.admin;

public class user_add_details {
    private String email,Uid;
    private int valid_status;
    public user_add_details(String email,String Uid,int valid_status){
        this.email=email;
        this.valid_status=valid_status;
        this.Uid=Uid;
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
        this.email = email;
    }




}