package com.silatsaktistudios.pltimecard.Models;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by james on 8/8/16.
 */
public class Student extends RealmObject {
    @PrimaryKey
    private int id;
    private String firstName, lastName, primaryPhone, primaryPhoneType, secondaryPhone,
            secondaryPhoneType, email, rank, studentType, enrollmentType, parent1FirstName, parent1LastName, parent1type,
            parent2FirstName, parent2LastName, parent2Type;
    private RealmList<Lesson> lessons;

    public Student() {}

    //for adult students
    public Student(String firstName, String lastName, String primaryPhone, String primaryPhoneType,
                   String secondaryPhone, String secondaryPhoneType, String email, String rank,
                   String enrollmentType) {
        setId();
        setFirstName(firstName);
        setLastName(lastName);
        setPrimaryPhone(primaryPhone);
        setPrimaryPhoneType(primaryPhoneType);
        setSecondaryPhone(secondaryPhone);
        setSecondaryPhoneType(secondaryPhoneType);
        setEmail(email);
        setRank(rank);
        setStudentType("Adult");
        setEnrollmentType(enrollmentType);
        setParent1FirstName("");
        setParent1LastName("");
        setParent1type("");
        setParent2FirstName("");
        setParent2LastName("");
        setParent2Type("");
    }

    //for child students
    public Student(String firstName, String lastName, String primaryPhone, String primaryPhoneType,
                   String secondaryPhone, String secondaryPhoneType, String email, String rank,
                   String enrollmentType, String parent1FirstName, String parent1LastName, String parent1type,
                   String parent2FirstName, String parent2LastName, String parent2Type) {
        setId();
        setFirstName(firstName);
        setLastName(lastName);
        setPrimaryPhone(primaryPhone);
        setPrimaryPhoneType(primaryPhoneType);
        setSecondaryPhone(secondaryPhone);
        setSecondaryPhoneType(secondaryPhoneType);
        setEmail(email);
        setRank(rank);
        setStudentType("Child");
        setEnrollmentType(enrollmentType);
        setParent1FirstName(parent1FirstName);
        setParent1LastName(parent1LastName);
        setParent1type(parent1type);
        setParent2FirstName(parent2FirstName);
        setParent2LastName(parent2LastName);
        setParent2Type(parent2Type);
    }

    public void setId() {
        int id;
        Realm realm = Realm.getDefaultInstance();


        try {
            id = realm.where(Student.class).max("id").intValue() + 1;
        } catch (NullPointerException e) {
            e.printStackTrace();
            id = 0;
        }


        this.id = id;
        realm.close();
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

    public String getFullName() {
      return getFirstName() + " " +getLastName();
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getPrimaryPhoneType() {
        return primaryPhoneType;
    }

    public void setPrimaryPhoneType(String primaryPhoneType) {
        this.primaryPhoneType = primaryPhoneType;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getSecondaryPhoneType() {
        return secondaryPhoneType;
    }

    public void setSecondaryPhoneType(String secondaryPhoneType) {
        this.secondaryPhoneType = secondaryPhoneType;
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

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public String getEnrollmentType() {
        return enrollmentType;
    }

    public void setEnrollmentType(String enrollmentType) {
        this.enrollmentType = enrollmentType;
    }

    public String getParent1FirstName() {
        return parent1FirstName;
    }

    public void setParent1FirstName(String parent1FirstName) {
        this.parent1FirstName = parent1FirstName;
    }

    public String getParent1LastName() {
        return parent1LastName;
    }

    public void setParent1LastName(String parent1LastName) {
        this.parent1LastName = parent1LastName;
    }

    public String getParent1type() {
        return parent1type;
    }

    public void setParent1type(String parent1type) {
        this.parent1type = parent1type;
    }

    public String getParent2FirstName() {
        return parent2FirstName;
    }

    public void setParent2FirstName(String parent2FirstName) {
        this.parent2FirstName = parent2FirstName;
    }

    public String getParent2LastName() {
        return parent2LastName;
    }

    public void setParent2LastName(String parent2LastName) {
        this.parent2LastName = parent2LastName;
    }

    public String getParent2Type() {
        return parent2Type;
    }

    public void setParent2Type(String parent2Type) {
        this.parent2Type = parent2Type;
    }

    public RealmList<Lesson> getLessons() {
        return lessons;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

}
