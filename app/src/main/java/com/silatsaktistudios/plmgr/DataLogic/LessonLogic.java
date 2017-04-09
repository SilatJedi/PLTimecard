package com.silatsaktistudios.plmgr.DataLogic;

import android.util.Log;

import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by SilatJedi on 4/9/17.
 */

public class LessonLogic {


    public static void delete(int id) {
        Realm realm = Realm.getDefaultInstance();

        final Lesson lesson = realm.where(Lesson.class).equalTo("id", id).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                lesson.deleteFromRealm();
            }
        });

        realm.close();
    }

    public static void add(final Student student, final Lesson lesson) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                student.addLesson(lesson);
            }
        });

        realm.close();
    }

    public static void edit(){

    }

    public static RealmResults<Lesson> lessonList() {
        Calendar firstOfMonth = Calendar.getInstance();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstOfMonth.set(Calendar.MINUTE, 0);
        firstOfMonth.set(Calendar.SECOND, 0);
        firstOfMonth.set(Calendar.MILLISECOND, 0);


        Realm realm = Realm.getDefaultInstance();
        RealmResults<Lesson> lessons = realm.where(Lesson.class).greaterThan("date", firstOfMonth.getTime()).findAll();
        realm.close();

        return lessons;
    }

    public static RealmList<Lesson> filteredLessonList(String filter) {
        RealmList<Lesson> filteredLessons = new RealmList<>();

        for(Lesson lesson : lessonList()) {
            if(lesson.getStudentName().toLowerCase().contains(filter.toLowerCase())) {
                filteredLessons.add(lesson);
            }
        }

        return filteredLessons;
    }

    public static RealmResults<Lesson> lessonList(Date from, Date to) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Lesson> lessons = realm.where(Lesson.class).greaterThan("date", from).lessThan("date", to).findAll();
        realm.close();

        return lessons;
    }

    public static RealmList<Lesson> filteredLessonList(Date from, Date to, String filter) {
        RealmList<Lesson> filteredLessons = new RealmList<>();

        for(Lesson lesson : lessonList(from, to)) {
            if(lesson.getStudentName().toLowerCase().contains(filter.toLowerCase())) {
                filteredLessons.add(lesson);
            }
        }

        return filteredLessons;
    }
}
