package com.silatsaktistudios.pltimecard;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.silatsaktistudios.pltimecard.Models.Lesson;
import com.silatsaktistudios.pltimecard.Models.Student;
import com.silatsaktistudios.pltimecard.Models.Timecard;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AddLessonActivity extends AppCompatActivity {

    //=======constants





    //=======variables
    private TextView lessonStudentNameTextView, gradeTextView;

    private DatePicker datePicker;

    private TimePicker timePicker;

    private CheckBox showedUpCheckBox, eligibleCheckBox, makeUpLessonCheckBox;

    private EditText notesEditText;

    private Realm realm;

    private RealmResults<Student> students;

    private Student student;


    //=================================activity methods===========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        realm = Realm.getDefaultInstance();

        students = realm.where(Student.class).findAll();

        setUpUI();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(!realm.isClosed()) {
            realm.close();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!realm.isClosed()) {
            realm.close();
        }
    }

    @Override
    public void onBackPressed() {
        warnBeforeCanceling();
    }




    //=====================================on click methods=========================================
    public void cancel(View view) {
        warnBeforeCanceling();
    }

    public void submit(View view) {

        if(isValid()) {
            final Lesson lesson = new Lesson(
                    student.getId(),
                    lessonStudentNameTextView.getText().toString(),
                    getDateTime(),
                    getGradeInt(gradeTextView.getText().toString()),
                    notesEditText.getText().toString(),
                    showedUpCheckBox.isChecked(),
                    eligibleCheckBox.isChecked(),
                    makeUpLessonCheckBox.isChecked()
            );

            final RealmResults<Timecard> timecards = realm.where(Timecard.class).findAll();



            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Timecard timecard = timecards.get(timecards.size() - 1);
                    timecard.addLesson(lesson);

                    student.addLesson(lesson);
                }
            });

            finish();
        } else {
            Toast.makeText(this, "Please choose a student before you submit a lesson.", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectStudent(View view) {

        if(students.size() > 0) {
            final String[] studentNames = new String[students.size()];

            for (int i = 0; i < students.size(); i++) {
                studentNames[i] = students.get(i).getFullName();
            }

            AlertDialog.Builder menu = new AlertDialog.Builder(this)
                    .setTitle("Select Student")
                    .setSingleChoiceItems(studentNames, -1, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int selection) {

                            lessonStudentNameTextView.setText(studentNames[selection]);
                            student = students.get(selection);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", null);

            menu.show();
        } else {
            Toast.makeText(this, "You need to add students first.", Toast.LENGTH_SHORT).show();
        }
    }


    public void toggleShowedUp(View view) {
        if(showedUpCheckBox.isChecked()) {
            eligibleCheckBox.setVisibility(View.GONE);
        } else {
            eligibleCheckBox.setVisibility(View.VISIBLE);
            eligibleCheckBox.setChecked(true);
        }
    }

    public void selectGrade(View view) {
        AlertDialog.Builder menu = new AlertDialog.Builder(this)
                .setTitle("Select Student")
                .setSingleChoiceItems(getResources().getStringArray(R.array.grades), -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selection) {

                        gradeTextView.setText(getResources().getStringArray(R.array.grades)[selection]);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null);

        menu.show();
    }







    //=======================================helper methods========================================
    private void warnBeforeCanceling() {
        AlertDialog.Builder warningDialog = new AlertDialog.Builder(this)
                .setTitle("Cancel")
                .setMessage("Do you really want to abandon all changes to this record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        warningDialog.show();
    }

    public void  setUpUI() {
        lessonStudentNameTextView = (TextView)findViewById(R.id.lessonStudentNameTextView);
        gradeTextView = (TextView)findViewById(R.id.lessonGradeTextView);

        datePicker = (DatePicker)findViewById(R.id.lessonDatePicker);

        timePicker = (TimePicker)findViewById(R.id.lessonTimePicker);

        showedUpCheckBox = (CheckBox)findViewById(R.id.lessonShowedUpCheckBox);
        eligibleCheckBox = (CheckBox)findViewById(R.id.lessonEligibleCheckBox);
        makeUpLessonCheckBox = (CheckBox)findViewById(R.id.lessonMakeUpCheckBox);

        notesEditText = (EditText)findViewById(R.id.lessonNotesEditText);
    }

    private boolean isValid() {
        return !lessonStudentNameTextView.getText().toString().trim().isEmpty();
    }

    private Date getDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.YEAR, datePicker.getYear());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        }

        return calendar.getTime();
    }

    private int getGradeInt(String grade) {
        switch(grade) {
            case "F" : return 0;
            case "D" : return 1;
            case "C" : return 2;
            case "B" : return 3;
            case "A" : return 4;
            default : return -1;
        }
    }
}