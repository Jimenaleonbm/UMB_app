package com.jimenaleon.game;

import java.io.Serializable;

/**
 * Created by jimena on 6/05/18.
 */

public class User implements Serializable{

    private String name;
    private String lastName;
    private String phone;
    private String age;
    private String gender;

    public User() {
    }

    public User(String name, String lastName, String phone, String age, String gender) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }


    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
