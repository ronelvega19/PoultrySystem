package com.example.poultryapp;

public class RegistrationData {
   String email,password,num;
    String first_name;
    String last_name;

    public String getNum() {
        return num;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public RegistrationData(String email, String password, String num, String first_name, String last_name){
        this.email = email;
        this.password = password;
        this.num = num;
        this.first_name =first_name;
        this.last_name = last_name;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
