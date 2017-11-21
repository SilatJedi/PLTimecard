package com.silatsaktistudios.plmgr.data;

import android.support.annotation.NonNull;

import com.silatsaktistudios.plmgr.model.Lesson;
import com.silatsaktistudios.plmgr.model.Student;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by SilatJedi on 4/9/17.
 *
 */

public class LessonData {

    public static void delete(int id) {
        Realm realm = Realm.getDefaultInstance();

        final Lesson lesson = realm.where(Lesson.class).equalTo("id", id).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                if (lesson != null) {
                    lesson.deleteFromRealm();
                }
            }
        });

        realm.close();
    }

    public static void add(final Lesson lesson) {
        Realm realm = Realm.getDefaultInstance();

        final Student student = realm.where(Student.class)
                .equalTo("id", lesson.getStudentID())
                .findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                if(student != null) {
                    student.addLesson(lesson);
                }
                else {
                    realm.insertOrUpdate(lesson);
                }
            }
        });

        realm.close();
    }

    public static void edit(final Lesson lesson){
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insertOrUpdate(lesson);
            }
        });

        realm.close();
    }

    private static List<Lesson> lessonList() {
        Calendar firstOfMonth = Calendar.getInstance();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstOfMonth.set(Calendar.MINUTE, 0);
        firstOfMonth.set(Calendar.SECOND, 0);
        firstOfMonth.set(Calendar.MILLISECOND, 0);


        Realm realm = Realm.getDefaultInstance();
        List<Lesson> lessons = realm.copyFromRealm(
                realm.where(Lesson.class)
                        .greaterThan("date", firstOfMonth.getTime())
                        .findAll()
                        .sort("date", Sort.ASCENDING));
        realm.close();

        return lessons;
    }

    public static List<Lesson> lessonList(String filter) {
        if (filter.length() > 0) {
            List<Lesson> filteredLessons = new RealmList<>();

            for (Lesson lesson : lessonList()) {
                if (lesson.getStudentName().toLowerCase().contains(filter.toLowerCase())) {
                    filteredLessons.add(lesson);
                }
            }

            return filteredLessons;
        }
        else {
            return lessonList();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static RealmResults<Lesson> lessonList(Date from, Date to) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Lesson> lessons = realm.where(Lesson.class).greaterThan("date", from).lessThan("date", to).findAll().sort("date", Sort.ASCENDING);
        realm.close();

        return lessons;
    }

    @SuppressWarnings("unused")
    public static RealmList<Lesson> lessonList(Date from, Date to, String filter) {
        RealmList<Lesson> filteredLessons = new RealmList<>();

        for(Lesson lesson : lessonList(from, to)) {
            if(lesson.getStudentName().toLowerCase().contains(filter.toLowerCase())) {
                filteredLessons.add(lesson);
            }
        }

        return filteredLessons;
    }

    public static Lesson getLesson(int id) {
        Realm realm = Realm.getDefaultInstance();
        Lesson temp = realm.where(Lesson.class).equalTo("id", id).findFirst();
        Lesson lesson = new Lesson();
        if (temp != null) {
            lesson = realm.copyFromRealm(temp);
        }
        realm.close();

        return lesson;
    }
}
