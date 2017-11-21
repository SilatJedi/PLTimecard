package com.silatsaktistudios.plmgr.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.silatsaktistudios.plmgr.model.Instructor;
import com.silatsaktistudios.plmgr.model.Lesson;
import com.silatsaktistudios.plmgr.model.Student;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by silatjedi on 11/21/17.
 *
 */

public class Demo {

    public static void createDemoData() {

        Log.d("resetting DB", "blam!");

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.deleteAll();
            }
        });

        for (int i = 0; i < 10; i++) {

            final Student adult = new Student(
                    "Adult",
                    "Student" + (i + 1),
                    "555555555" + i,
                    "Cell",
                    "55555555" + i + "5",
                    "Home",
                    "adult-student" + i + "@mail.net",
                    "Dasar 1",
                    "Athletic Adventure Program");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    realm.insert(adult);
                }
            });

            final Student child = new Student(
                    "Child",
                    "Student" + (i + 1),
                    "555555555" + i,
                    "Cell",
                    "55555555" + i + "5",
                    "Cell",
                    "child-student" + i + "@mail.net",
                    "White Belt",
                    "Persilat Kids",
                    "Parent1",
                    "Student" + (i + 1),
                    "Mother",
                    "Parent2",
                    "Student" + (i + 1),
                    "Father");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    realm.insert(child);
                }
            });


        }


        final RealmResults<Student> students = realm.where(Student.class).findAll();

        for (final Student student : students) {
            final Lesson lesson = new Lesson(
                    student.getId(),
                    student.getFirstName() + " " + student.getLastName(),
                    new Date(),
                    4f,
                    "Good Job",
                    true, true, false);

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {

                    student.addLesson(lesson);
                }
            });
        }

        final Instructor instructor = new Instructor(
                "Obi-wan",
                "Kenobi",
                "Jedi Master", "Mas", "0855378008", "sithsuckass@jediorder.org");

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(instructor);
            }
        });

        realm.close();
    }
}
