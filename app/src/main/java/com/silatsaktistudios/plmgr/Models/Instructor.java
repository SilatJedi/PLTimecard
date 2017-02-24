package com.silatsaktistudios.plmgr.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by james on 12/18/16.
 */

public class Instructor extends RealmObject {
    @PrimaryKey
    private int id = 0;
    private String firstName, lastName, rank, title;

    public Instructor() {}

    public Instructor(String firstName, String lastName, String rank, String title) {
        setFirstName(firstName);
        setLastName(lastName);
        setRank(rank);
        setTitle(title);
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
