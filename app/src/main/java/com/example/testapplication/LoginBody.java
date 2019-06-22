package com.example.testapplication;

public class LoginBody {
    public LoginBody(String phoneNum, String userID, String password){
        this.PhoneNum=phoneNum;
        this.UserID=userID;
        this.Password=password;
    }
    public String PhoneNum;
    public String UserID;
    public String Password;
}
