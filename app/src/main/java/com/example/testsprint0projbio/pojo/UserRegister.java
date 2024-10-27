package com.example.testsprint0projbio.pojo;

import androidx.annotation.NonNull;

public class UserRegister {
    private String name;
    private String surname_1;
    private String surname_2;
    private String email;
    private String telephone;
    private String password;

    public UserRegister(String name, String surname_1, String surname_2, String email, String telephone, String password) {
        this.name = name;
        this.surname_1 = surname_1;
        this.surname_2 = surname_2;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname_1() {
        return surname_1;
    }

    public void setSurname_1(String surname_1) {
        this.surname_1 = surname_1;
    }

    public String getSurname_2() {
        return surname_2;
    }

    public void setSurname_2(String surname_2) {
        this.surname_2 = surname_2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserRegister{" +
                "name='" + name + '\'' +
                ", surname_1='" + surname_1 + '\'' +
                ", surname_2='" + surname_2 + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
