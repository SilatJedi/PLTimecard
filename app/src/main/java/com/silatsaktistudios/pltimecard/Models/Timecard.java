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
    private Date startDate, endDate;
    private RealmList<Lesson> lessons;

    public void setId() {
        int id;
        Realm realm = Realm.getDefaultInstance();

        try {
            id = realm.where(Student.class).max("id").intValue() + 1;
        } catch(ArrayIndexOutOfBoundsException ex) {
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
}
