package com.silatsaktistudios.plmgr.Models;

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

    private int studentID;
    private String studentName;
    private float grade;
    private String note;
    private Date date;
    private boolean showedUp;
    private boolean isEligible;
    private boolean isMakeUp;

    public Lesson() {}

    public Lesson(int studentID, String studentName, Date date, float grade, String note, boolean showedUp, boolean eligible, boolean isMakeUp) {
        setId();
        setDate(date);
        setGrade(grade);
        setShowedUp(showedUp);
        setEligible(eligible);
        setNote(note);
        setMakeUp(isMakeUp);
        setStudentID(studentID);
        setStudentName(studentName);
    }

    public int getId() {
        return id;
    }

    public void setId() {
        int id;
        Realm realm = Realm.getDefaultInstance();

        try {
            id = realm.where(Lesson.class).max("id").intValue() + 1;
        } catch(NullPointerException ex) {
            id = 0;
        }

        this.id = id;
        realm.close();
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
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

    public boolean isMakeUp() {
        return isMakeUp;
    }

    public void setMakeUp(boolean makeUp) {
        isMakeUp = makeUp;
    }

}