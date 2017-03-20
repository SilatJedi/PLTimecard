package com.silatsaktistudios.plmgr.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by james on 12/18/16.
 */

public class Instructor extends RealmObject {
    @PrimaryKey
    private int id = 0;
    private String firstName, lastName, rank, title, phone, email;

    public Instructor() {}

    public Instructor(String firstName, String lastName, String rank, String title, String phone, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setRank(rank);
        setTitle(title);
        setPhone(phone);
        setEmail(email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
