package com.silatsaktistudios.plmgr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.silatsaktistudios.plmgr.Models.Instructor;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;
import com.silatsaktistudios.plmgr.Models.TimeCard;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class PLMGRActivity extends AppCompatActivity {

    LinearLayout mainActivityLayout;
    private Button debugDbButton;

//==================================Activity Methods================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plmgr);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
        }

        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder(getApplicationContext())
                        .name("MPPLDB.realm")
                        .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Realm realm = Realm.getDefaultInstance();

        setUpUI();

        debugDbButton.setEnabled(true);


        if (realm.where(Instructor.class).findAll().size() == 0) {

            mainActivityLayout.setVisibility(View.INVISIBLE);

            new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                    .setTitle("Welcome New User!!!")
                    .setMessage("Welcome to the MP USA Private Lesson Manager. Press OK to get started.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(PLMGRActivity.this, EditInstructorActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }












//========================================On Click Methods==========================================
    public void gotoLessonList(View view) {
        startActivity(new Intent(PLMGRActivity.this, LessonListActivity.class));
        finish();
    }

    public void gotoTimeCardList(View view) {

    }

    public void gotoStudentList(View view) {
        startActivity(new Intent(PLMGRActivity.this, StudentListActivity.class));
        finish();
    }

    public void gotoScheduler(View view) {

    }

    public void gotoReminders(View view) {

    }

    public void gotoOptions(View view) {
        startActivity(new Intent(PLMGRActivity.this, OptionsActivity.class));
    }

    public void createDemoData(View view) {

        Log.d("resetting DB", "blam!");

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });

        for (int i = 0; i < 10; i++) {

            final Student adult = new Student(
                    "Adult",
                    "Student" + ( i + 1),
                    "555555555" + i,
                    "Cell",
                    "55555555" + i + "5",
                    "Home",
                    "adult-student" + i + "@mail.net",
                    "Dasar 1",
                    "Athletic Adventure Program");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(adult);
                }
            });

            final Student child = new Student(
                    "Child",
                    "Student" + ( i + 1),
                    "555555555" + i,
                    "Cell",
                    "55555555" + i + "5",
                    "Cell",
                    "child-student" + i + "@mail.net",
                    "White Belt",
                    "Persilat Kids",
                    "Parent1",
                    "Student" + ( i + 1),
                    "Mother",
                    "Parent2",
                    "Student" + ( i + 1),
                    "Father");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(child);
                }
            });



        }

        Calendar firstOfMonth = Calendar.getInstance();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstOfMonth.set(Calendar.MINUTE, 0);
        final TimeCard timeCard = new TimeCard(firstOfMonth.getTime());

        final RealmResults<Student> students = realm.where(Student.class).findAll();

        for (final Student student : students) {
            final Lesson lesson = new Lesson(
                    student.getId(),
                    student.getFirstName() + " " + student.getLastName(),
                    new Date(),
                    5,
                    "Good Job",
                    true,true,false);

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    student.addLesson(lesson);
                    timeCard.addLesson(lesson);
                    realm.insert(timeCard);
                }
            });
        }

        final Instructor instructor = new Instructor(
                "Obi-wan",
                "Kenobi",
                "Jedi Master", "Mas", "0855378008", "sithsuckass@jediorder.org");

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(instructor);
            }
        });

        realm.close();
    }

//=========================================Class Methods========================================
    private void setUpUI() {
        debugDbButton = (Button) findViewById(R.id.debugDbButton);
        mainActivityLayout = (LinearLayout) findViewById(R.id.mainActivityLayout);
    }

}
