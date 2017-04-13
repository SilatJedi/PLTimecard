package com.silatsaktistudios.plmgr.DataLogic;

import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by SilatJedi on 4/9/17.
 */

public class StudentData {

    public static void delete(int id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Lesson> lessons =
                realm.where(Lesson.class)
                        .equalTo("studentID", id)
                        .findAll();

        for (final Lesson lesson : lessons) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    lesson.setStudentID(-1);
                }
            });
        }

        final Student toDelete = realm.where(Student.class).equalTo("id", id).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                toDelete.deleteFromRealm();
            }
        });

        realm.close();
    }

    public static void addOrEdit(final Student student) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(student);
            }
        });

        realm.close();
    }

    public static List<Student> studentList() {
        Realm realm = Realm.getDefaultInstance();
        List<Student> students = realm.copyFromRealm(realm.where(Student.class).findAll().sort("lastName", Sort.ASCENDING, "firstName", Sort.ASCENDING));
        realm.close();

        return students;
    }

    public static List<Student> filteredStudentList(String filter) {
        List<Student> students = new RealmList<>();

        for (Student student : studentList()) {
            if (student.getFullName().toLowerCase().contains(filter.toLowerCase())) {
                students.add(student);
            }
        }

        return students;
    }

    public static Student getStudent(int id) {
        Realm realm = Realm.getDefaultInstance();
        Student student = realm.where(Student.class)
                .equalTo("id", id)
                .findFirst();
        realm.close();

        return student;
    }

    public static int getIdForStudent(String fullName) {
        String[] names = fullName.split(" ");
        Realm realm = Realm.getDefaultInstance();
        Student student = realm
                .where(Student.class)
                .equalTo("firstName", names[0])
                .equalTo("lastName", names[1])
                .findFirst();
        realm.close();

        if(student != null) {
            return student.getId();
        }
        else {
            return -1;
        }
    }

    public static String[] studentNameArray() {
        List<Student> students = studentList();
        String[] studentNames = new String[students.size()];

        for (int i = 0; i < students.size(); i++) {
            studentNames[i] = students.get(i).getFullName();
        }

        return studentNames;
    }
}
