package com.silatsaktistudios.pltimecard.Models;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by james on 8/8/16.
 */
public class Lesson extends RealmObject {
    @PrimaryKey
    private int id;
    private int grade;
    private String note;
    private Date date;
    private boolean showedUp;
    private boolean isEligible;

    public Lesson() {}

    public Lesson(Date date, int grade, boolean showedUp, boolean eligible, String note) {
        setId();
        setDate(date);
        setGrade(grade);
        setShowedUp(showedUp);
        setEligible(eligible);
        setNote(note);
    }

    public void setId() {
        int id;
        Realm realm = Realm.getDefaultInstance();

        try {
            id = realm.where(Lesson.class).max("id").intValue() + 1;
        } catch(ArrayIndexOutOfBoundsException ex) {
            id = 0;
        }

        this.id = id;
        realm.close();
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean didShowUp() {
        return showedUp;
    }

    public void setShowedUp(boolean showedUp) {
        this.showedUp = showedUp;
    }

    public boolean isEligible() {
        return isEligible;
    }

    public void setEligible(boolean eligible) {
        isEligible = eligible;
    }
}
