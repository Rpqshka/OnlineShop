package com.example.OnlineShop.models;


import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String firstName;
    @Column
    private String secondName;
    @Column
    private String email;
    @Column
    private String pass;
    @Column
    private String phone;
    @Column
    private String role;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getSecondName(){
        return secondName;
    }
    public void setSecondName(String secondName){
        this.secondName = secondName;
    }
    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
