package com.silatsaktistudios.pltimecard;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import io.realm.Realm;

public class AddLessonActivity extends AppCompatActivity {

    //variables
    private TextView studentNameTextView, gradeTextView;

    private DatePicker datePicker;

    private TimePicker timePicker;

    private CheckBox showedUpCheckBox, makeUpCheckBox, eligibleCheckBox;

    private EditText notesEditText;

    private Realm realm;





//==================================Activity Methods================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        realm = Realm.getDefaultInstance();

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




    //===========================ON CLICK METHODS==================================
    public void cancel(View view) {
        warnBeforeCanceling();
    }

    public void submit(View view) {

    }







    //==========================HELPER METHODS====================================
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

    public void setUpUI() {
//        private TextView studentNameTextView, gradeTextView;
//        private DatePicker datePicker;
//        private TimePicker timePicker;
//        private CheckBox showedUpCheckBox, makeUpCheckBox, eligibleCheckBox;
//        private EditText notesEditText;

        studentNameTextView = (TextView)findViewById(R.id.lessonStudentNameTextView);
        gradeTextView = (TextView)findViewById(R.id.lessonGradeTextView);

        datePicker = (DatePicker)findViewById(R.id.lessonDatePicker);

        timePicker = (TimePicker)findViewById(R.id.lessonTimePicker);

        showedUpCheckBox = (CheckBox)findViewById(R.id.lessonShowedUpCheckBox);
        makeUpCheckBox = (CheckBox)findViewById(R.id.lessonMakeUpCheckBox);
        eligibleCheckBox = (CheckBox)findViewById(R.id.lessonEligibleCheckBox);

        notesEditText = (EditText)findViewById(R.id.lessonNotesEditText);
    }
}
