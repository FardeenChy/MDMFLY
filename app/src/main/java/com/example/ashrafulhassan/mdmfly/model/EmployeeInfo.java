package com.example.ashrafulhassan.mdmfly.model;

/**
 * Created by user on 16-Nov-17.
 */

public class EmployeeInfo {

    private String name, email; private int age;

    public EmployeeInfo() {
    }

    public EmployeeInfo(String name, String email, int age) {
        setName(name);  setAge(age);   setEmail(email);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
