package com.shopping.basket;

/**
 * User object
 */
public class UserModel {

    private String name;
    private String email;

    public UserModel(){

    }

    public UserModel(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

}
