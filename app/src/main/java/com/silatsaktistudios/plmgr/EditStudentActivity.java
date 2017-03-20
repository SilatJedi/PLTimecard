package com.silatsaktistudios.plmgr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.silatsaktistudios.plmgr.Models.Student;

import io.realm.Realm;


public class EditStudentActivity extends AppCompatActivity {

    private int studentID = -1;

    //constants
    private final int ADULT_STUDENT = 1, CHILD_STUDENT = 0;




    //variables
    private EditText firstNameEditText, lastNameEditText, emailEditText, parent1FirstNameEditText,
            parent1LastNameEditText, primaryPhoneEditText, parent2FirstNameEditText,
            parent2LastNameEditText, secondaryPhoneEditText;

    private TextView activityTitleTextView, childTextView, adultTextView, parent1HeadingTextView,
            primaryPhoneNumTextView, primaryPhoneTypeTextView, parent1TypeTextView,
            parent2HeadingTextView, secondaryPhoneNumTextView, secondaryPhoneTypeTextView,
            parent2TypeTextView, enrollmentTypeTextView, rankTextView, primaryPhoneTypeValueTextView,
            secondaryPhoneTypeValueTextView, parent1TypeValueTextView, parent2TypeValueTextView;

    private LinearLayout parent1FirstAndLastNameTextViewLayout, parent1FirstAndLastNameEditTextLayout,
            parent2FirstAndLastNameTextViewLayout, parent2FirstAndLastNameEditTextLayout;

    private int studentType = CHILD_STUDENT;

    private Student student;

    //=====================Activity methods====================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        setUpUI();

        activityTitleTextView.setText(R.string.edit_student);

        if (getIntent().hasExtra("studentID")) {
            studentID = getIntent().getIntExtra("studentID", -1);
        }

        if (studentID == -1) {
            onBackPressed();
        } else {
            displayStudentInfo();
        }
    }

    @Override
    public void onBackPressed() {
         new AlertDialog.Builder(this)
                .setTitle("Cancel")
                .setMessage("Do you really want to abandon all changes to this record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(EditStudentActivity.this, ViewStudentActivity.class);
                        intent.putExtra("studentID", studentID);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                 .show();

    }








    //========================onclick methods==============================================
    public void cancel(View view) {
        onBackPressed();
    }



    public void submit(View view) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                student.setFirstName(firstNameEditText.getText().toString());
                student.setLastName(lastNameEditText.getText().toString());
                student.setPrimaryPhone(primaryPhoneEditText.getText().toString());
                student.setPrimaryPhoneType(primaryPhoneTypeValueTextView.getText().toString());
                student.setSecondaryPhone(secondaryPhoneEditText.getText().toString());
                student.setSecondaryPhoneType(secondaryPhoneTypeValueTextView.getText().toString());
                student.setEmail(emailEditText.getText().toString());
                student.setRank(rankTextView.getText().toString());
                student.setEnrollmentType(enrollmentTypeTextView.getText().toString());
                student.setParent1FirstName(parent1FirstNameEditText.getText().toString());
                student.setParent1LastName(parent1LastNameEditText.getText().toString());
                student.setParent1type(parent1TypeValueTextView.getText().toString());
                student.setParent2FirstName(parent2FirstNameEditText.getText().toString());
                student.setParent2LastName(parent2LastNameEditText.getText().toString());
                student.setParent2Type(parent2TypeValueTextView.getText().toString());
            }
        });

        realm.close();

        Intent i = new Intent(EditStudentActivity.this, ViewStudentActivity.class);
        i.putExtra("studentID", studentID);
        startActivity(i);
        finish();
    }


    public void selectEnrollmentType(View view) {

        AlertDialog.Builder menu = new AlertDialog.Builder(this)
                .setTitle("Select Enrollment Type")
                .setSingleChoiceItems(getResources().getStringArray(R.array.enrollmentTypes), -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selection) {

                        enrollmentTypeTextView.setText(getResources().getStringArray(R.array.enrollmentTypes)[selection]);
                        rankTextView.setText("");
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null);

        menu.show();
    }


    public void selectRank(View view) {

        if(enrollmentTypeTextView.getText().toString().isEmpty()) {
            Toast.makeText(this, "Choose an enrollment type first.", Toast.LENGTH_SHORT).show();
        }
        else {
            String[] rankArray;

            switch (enrollmentTypeTextView.getText().toString()) {
                case "Persilat Kids":
                    rankArray = getResources().getStringArray(R.array.childRanks);
                    break;
                case "Athletic Adventure Program":
                    rankArray = getResources().getStringArray(R.array.AAPRanks);
                    break;
                case "Wealth of Health":
                    rankArray = getResources().getStringArray(R.array.WOHRanks);
                    break;
                case "VibraVision":
                    rankArray = getResources().getStringArray(R.array.VVRanks);
                    break;
                default:
                    rankArray = new String[1];
                    break;
            }

            final String[] forTheDialog = rankArray;

            AlertDialog.Builder menu = new AlertDialog.Builder(this)
                    .setTitle("Select Rank")
                    .setSingleChoiceItems(rankArray, -1, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int selection) {

                            rankTextView.setText(forTheDialog[selection]);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", null);

            menu.show();
        }
    }


    public void selectPrimaryPhoneType(View view) {
        AlertDialog.Builder menu = new AlertDialog.Builder(this)
                .setTitle("Select Phone Type")
                .setSingleChoiceItems(getResources().getStringArray(R.array.phoneTypes), -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selection) {

                        primaryPhoneTypeValueTextView.setText(getResources().getStringArray(R.array.phoneTypes)[selection]);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null);

        menu.show();
    }

    public void selectSecondaryPhoneType(View view) {
        AlertDialog.Builder menu = new AlertDialog.Builder(this)
                .setTitle("Select Phone Type")
                .setSingleChoiceItems(getResources().getStringArray(R.array.phoneTypes), -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selection) {

                        secondaryPhoneTypeValueTextView.setText(getResources().getStringArray(R.array.phoneTypes)[selection]);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null);

        menu.show();
    }


    public void selectParent1Type(View view) {
        AlertDialog.Builder menu = new AlertDialog.Builder(this)
                .setTitle("Select Parent Type")
                .setSingleChoiceItems(getResources().getStringArray(R.array.parentTypes), -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selection) {

                        parent1TypeValueTextView.setText(getResources().getStringArray(R.array.parentTypes)[selection]);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null);

        menu.show();
    }

    public void selectParent2Type(View view) {
        AlertDialog.Builder menu = new AlertDialog.Builder(this)
                .setTitle("Select Parent Type")
                .setSingleChoiceItems(getResources().getStringArray(R.array.parentTypes), -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selection) {

                        parent2TypeValueTextView.setText(getResources().getStringArray(R.array.parentTypes)[selection]);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null);

        menu.show();
    }

    @SuppressWarnings("deprecation")
    public void changeToChild(View view) {

        if(studentType == ADULT_STUDENT) {
            parent1HeadingTextView.setVisibility(View.VISIBLE);
            parent1FirstAndLastNameTextViewLayout.setVisibility(View.VISIBLE);
            parent1FirstAndLastNameEditTextLayout.setVisibility(View.VISIBLE);
            parent1TypeTextView.setVisibility(View.VISIBLE);
            parent1TypeValueTextView.setVisibility(View.VISIBLE);

            primaryPhoneNumTextView.setText(getResources().getString(R.string.parent1PhoneNumber));
            primaryPhoneTypeTextView.setText(getResources().getString(R.string.parent1PhoneType));

            parent2HeadingTextView.setVisibility(View.VISIBLE);
            parent2FirstAndLastNameTextViewLayout.setVisibility(View.VISIBLE);
            parent2FirstAndLastNameEditTextLayout.setVisibility(View.VISIBLE);
            parent2TypeTextView.setVisibility(View.VISIBLE);
            parent2TypeValueTextView.setVisibility(View.VISIBLE);

            secondaryPhoneNumTextView.setText(getResources().getString(R.string.parent2PhoneNumber));
            secondaryPhoneTypeTextView.setText(getResources().getString(R.string.parent2PhoneType));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                childTextView.setTextColor(getResources().getColor(R.color.blue, null));
                adultTextView.setTextColor(getResources().getColor(R.color.white, null));
            } else{
                childTextView.setTextColor(getResources().getColor(R.color.blue));
                adultTextView.setTextColor(getResources().getColor(R.color.white));
            }

            enrollmentTypeTextView.setText(getResources().getStringArray(R.array.enrollmentTypes)[CHILD_STUDENT]);
            rankTextView.setText("");

            studentType = CHILD_STUDENT;
        }
    }


    @SuppressWarnings("deprecation")
    public void changeToAdult(View view) {

        if(studentType == CHILD_STUDENT) {
            parent1HeadingTextView.setVisibility(View.GONE);
            parent1FirstAndLastNameTextViewLayout.setVisibility(View.GONE);
            parent1FirstAndLastNameEditTextLayout.setVisibility(View.GONE);
            parent1TypeTextView.setVisibility(View.GONE);
            parent1TypeValueTextView.setVisibility(View.GONE);

            primaryPhoneNumTextView.setText(getResources().getString(R.string.primaryPhoneNumber));
            primaryPhoneTypeTextView.setText(getResources().getString(R.string.primaryPhoneType));

            parent2HeadingTextView.setVisibility(View.GONE);
            parent2FirstAndLastNameTextViewLayout.setVisibility(View.GONE);
            parent2FirstAndLastNameEditTextLayout.setVisibility(View.GONE);
            parent2TypeTextView.setVisibility(View.GONE);
            parent2TypeValueTextView.setVisibility(View.GONE);

            secondaryPhoneNumTextView.setText(getResources().getString(R.string.secondaryPhoneNumber));
            secondaryPhoneTypeTextView.setText(getResources().getString(R.string.secondaryPhoneType));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                childTextView.setTextColor(getResources().getColor(R.color.white, null));
                adultTextView.setTextColor(getResources().getColor(R.color.blue, null));
            } else{
                childTextView.setTextColor(getResources().getColor(R.color.white));
                adultTextView.setTextColor(getResources().getColor(R.color.blue));
            }

            enrollmentTypeTextView.setText(getResources().getStringArray(R.array.enrollmentTypes)[ADULT_STUDENT]);
            rankTextView.setText("");

            studentType = ADULT_STUDENT;
        }
    }




















    public void  setUpUI() {
        firstNameEditText = (EditText)findViewById(R.id.studentFirstNameEditText);
        lastNameEditText = (EditText)findViewById(R.id.studentLastNameEditText);
        emailEditText = (EditText)findViewById(R.id.studentEmailEditText);
        parent1FirstNameEditText = (EditText)findViewById(R.id.studentParent1FirstNameEditText);
        parent1LastNameEditText = (EditText)findViewById(R.id.studentParent1LastNameEditText);
        primaryPhoneEditText = (EditText)findViewById(R.id.studentPrimaryPhoneNumEditText);
        parent2FirstNameEditText = (EditText)findViewById(R.id.studentParent2FirstNameEditText);
        parent2LastNameEditText = (EditText)findViewById(R.id.studentParent2LastNameEditText);
        secondaryPhoneEditText = (EditText)findViewById(R.id.studentSecondaryPhoneNumEditText);


        activityTitleTextView = (TextView)findViewById(R.id.addStudentActivityTitle);
        childTextView = (TextView)findViewById(R.id.studentChildTextView);
        adultTextView = (TextView)findViewById(R.id.studentAdultTextView);
        parent1HeadingTextView = (TextView)findViewById(R.id.studentParent1HeadingTextView);
        primaryPhoneNumTextView = (TextView)findViewById(R.id.studentPrimaryPhoneNumTextView);
        primaryPhoneTypeTextView = (TextView)findViewById(R.id.studentPrimaryPhoneTypeTextView);
        parent1TypeTextView = (TextView)findViewById(R.id.studentParent1TypeTextView);
        parent2HeadingTextView = (TextView) findViewById(R.id.studentParent2HeadingTextView);
        secondaryPhoneNumTextView = (TextView)findViewById(R.id.studentSecondaryPhoneNumTextView);
        secondaryPhoneTypeTextView = (TextView)findViewById(R.id.studentSecondaryPhoneTypeTextView);
        parent2TypeTextView = (TextView)findViewById(R.id.studentParent2TypeTextView);
        enrollmentTypeTextView = (TextView) findViewById(R.id.studentEnrollmentTypeSpinner);
        rankTextView = (TextView) findViewById(R.id.studentRankSpinner);
        primaryPhoneTypeValueTextView = (TextView)findViewById(R.id.studentPrimaryPhoneTypeSpinner);
        parent1TypeValueTextView = (TextView) findViewById(R.id.studentParent1TypeSpinner);
        secondaryPhoneTypeValueTextView = (TextView)findViewById(R.id.studentSecondaryPhoneTypeSpinner);
        parent2TypeValueTextView = (TextView) findViewById(R.id.studentParent2TypeSpinner);


        parent1FirstAndLastNameTextViewLayout = (LinearLayout)findViewById(R.id.parent1FirstAndlastNameTextViews);
        parent1FirstAndLastNameEditTextLayout = (LinearLayout)findViewById(R.id.parent1FirstAndLastNameEditTexts);
        parent2FirstAndLastNameTextViewLayout = (LinearLayout)findViewById(R.id.parent2FirstAndlastNameTextViews);
        parent2FirstAndLastNameEditTextLayout = (LinearLayout)findViewById(R.id.parent2FirstAndlastNameEditTexts);
    }

    private void displayStudentInfo() {
        Realm realm = Realm.getDefaultInstance();
        student = realm.where(Student.class).equalTo("id", studentID).findFirst();

        firstNameEditText.setText(student.getFirstName());
        lastNameEditText.setText(student.getLastName());
        enrollmentTypeTextView.setText(student.getEnrollmentType());
        emailEditText.setText(student.getEmail());
        primaryPhoneEditText.setText(formatPhoneNumber(student.getPrimaryPhone()));
        primaryPhoneTypeValueTextView.setText(student.getPrimaryPhoneType());
        secondaryPhoneEditText.setText(formatPhoneNumber(student.getSecondaryPhone()));
        secondaryPhoneTypeValueTextView.setText(student.getSecondaryPhoneType());

        if (student.getStudentType().toLowerCase().equals("adult")) {
            changeToAdult(null);
        } else {
            parent1FirstNameEditText.setText(student.getParent1FirstName());
            parent1LastNameEditText.setText(student.getParent1LastName());
            parent1TypeValueTextView.setText(student.getParent1type());

            parent2FirstNameEditText.setText(student.getParent2FirstName());
            parent2LastNameEditText.setText(student.getParent2LastName());
            parent2TypeValueTextView.setText(student.getParent2Type());
        }


        rankTextView.setText(student.getRank());

        realm.close();
    }

    private String formatPhoneNumber(String number) {
        if (number.length() >= 7) {
            return "(" + number.substring(0, 3) + ")" +
                    number.substring(3, 6) + "-" +
                    number.substring(6, number.length());
        }
        else {
            return "";
        }
    }

}
