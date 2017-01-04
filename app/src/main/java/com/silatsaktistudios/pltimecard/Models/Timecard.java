package com.silatsaktistudios.pltimecard.Models;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by james on 12/19/16.
 */

public class Timecard extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private Date startDate, endDate;
    private RealmList<Lesson> lessons;
    private boolean isCurrent = true;

    public Timecard() {}

    public Timecard(Date startDate) {
        setId();
        setStartDate(startDate);
        setTitle(android.text.format.DateFormat.format("MMM YYYY", startDate).toString());
    }

    public void setId() {
        int id;
        Realm realm = Realm.getDefaultInstance();

        try {
            id = realm.where(Student.class).max("id").intValue() + 1;
        } catch(NullPointerException ex) {
            id = 0;
        }

        this.id = id;
        realm.close();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public RealmList<Lesson> getLessons() {
        return lessons;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
