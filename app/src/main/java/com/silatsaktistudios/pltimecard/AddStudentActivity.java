package com.silatsaktistudios.pltimecard;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.silatsaktistudios.pltimecard.Models.Student;

import io.realm.Realm;

public class AddStudentActivity extends AppCompatActivity {

    //constants
    private final int ADULT_STUDENT = 0, CHILD_STUDENT = 1;




    //variables
    private EditText firstNameEditText, lastNameEditText, emailEditText, parent1FirstNameEditText,
            parent1LastNameEditText, primaryPhoneEditText, parent2FirstNameEditText,
            parent2LastNameEditText, secondaryPhoneEditText;

    private TextView childTextView, adultTextView, parent1HeadingTextView,
            primaryPhoneNumTextView, primaryPhoneTypeTextView, parent1TypeTextView,
            parent2HeadingTextView, secondaryPhoneNumTextView, secondaryPhoneTypeTextView,
            parent2TypeTextView;

    private Spinner enrollmentTypeSpinner, rankSpinner, primaryPhoneTypeSpinner, parent1TypeSpinner,
            secondaryPhoneTypeSpinner, parent2TypeSpinner;

    private ArrayAdapter<CharSequence> rankArrayAdapter;

    private LinearLayout parent1FirstAndLastNameTextViewLayout, parent1FirstAndLastNameEditTextLayout,
            parent2FirstAndLastNameTextViewLayout, parent2FirstAndLastNameEditTextLayout;

    private Realm realm;

    private int studentType = CHILD_STUDENT;




//==================================Activity Methods================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

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
    @SuppressWarnings("deprecation")
    public void changeToChild(View view) {

        if(studentType == ADULT_STUDENT) {
            parent1HeadingTextView.setVisibility(View.VISIBLE);
            parent1FirstAndLastNameTextViewLayout.setVisibility(View.VISIBLE);
            parent1FirstAndLastNameEditTextLayout.setVisibility(View.VISIBLE);
            parent1TypeTextView.setVisibility(View.VISIBLE);
            parent1TypeSpinner.setVisibility(View.VISIBLE);

            primaryPhoneNumTextView.setText(getResources().getString(R.string.parent1PhoneNumber));
            primaryPhoneTypeTextView.setText(getResources().getString(R.string.parent1PhoneType));

            parent2HeadingTextView.setVisibility(View.VISIBLE);
            parent2FirstAndLastNameTextViewLayout.setVisibility(View.VISIBLE);
            parent2FirstAndLastNameEditTextLayout.setVisibility(View.VISIBLE);
            parent2TypeTextView.setVisibility(View.VISIBLE);
            parent2TypeSpinner.setVisibility(View.VISIBLE);

            secondaryPhoneNumTextView.setText(getResources().getString(R.string.parent2PhoneNumber));
            secondaryPhoneTypeTextView.setText(getResources().getString(R.string.parent2PhoneType));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                childTextView.setTextColor(getResources().getColor(R.color.blue, null));
                adultTextView.setTextColor(getResources().getColor(R.color.white, null));
            } else{
                childTextView.setTextColor(getResources().getColor(R.color.blue));
                adultTextView.setTextColor(getResources().getColor(R.color.white));
            }

            enrollmentTypeSpinner.setSelection(0);

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
            parent1TypeSpinner.setVisibility(View.GONE);

            primaryPhoneNumTextView.setText(getResources().getString(R.string.primaryPhoneNumber));
            primaryPhoneTypeTextView.setText(getResources().getString(R.string.primaryPhoneType));

            parent2HeadingTextView.setVisibility(View.GONE);
            parent2FirstAndLastNameTextViewLayout.setVisibility(View.GONE);
            parent2FirstAndLastNameEditTextLayout.setVisibility(View.GONE);
            parent2TypeTextView.setVisibility(View.GONE);
            parent2TypeSpinner.setVisibility(View.GONE);

            secondaryPhoneNumTextView.setText(getResources().getString(R.string.secondaryPhoneNumber));
            secondaryPhoneTypeTextView.setText(getResources().getString(R.string.secondaryPhoneType));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                childTextView.setTextColor(getResources().getColor(R.color.white, null));
                adultTextView.setTextColor(getResources().getColor(R.color.blue, null));
            } else{
                childTextView.setTextColor(getResources().getColor(R.color.white));
                adultTextView.setTextColor(getResources().getColor(R.color.blue));
            }

            enrollmentTypeSpinner.setSelection(1);

            studentType = ADULT_STUDENT;
        }
    }



    public void cancel(View view) {
        warnBeforeCanceling();
    }



    public void submit(View view) {

        if(studentType == CHILD_STUDENT){

            final Student student = new Student(
                    firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(),
                    primaryPhoneEditText.getText().toString(),
                    primaryPhoneTypeSpinner.getSelectedItem().toString(),
                    secondaryPhoneEditText.getText().toString(),
                    secondaryPhoneTypeSpinner.getSelectedItem().toString(),
                    emailEditText.getText().toString(),
                    rankSpinner.getSelectedItem().toString(),
                    enrollmentTypeSpinner.getSelectedItem().toString(),
                    parent1FirstNameEditText.getText().toString(),
                    parent1LastNameEditText.getText().toString(),
                    parent1TypeSpinner.getSelectedItem().toString(),
                    parent2FirstNameEditText.getText().toString(),
                    parent2LastNameEditText.getText().toString(),
                    parent2TypeSpinner.getSelectedItem().toString());

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(student);
                }
            });
        }
        else {
            final Student student = new Student(
                    firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(),
                    primaryPhoneEditText.getText().toString(),
                    primaryPhoneTypeSpinner.getSelectedItem().toString(),
                    secondaryPhoneEditText.getText().toString(),
                    secondaryPhoneTypeSpinner.getSelectedItem().toString(),
                    emailEditText.getText().toString(),
                    rankSpinner.getSelectedItem().toString(),
                    enrollmentTypeSpinner.getSelectedItem().toString());

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(student);
                }
            });
        }

        finish();
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

    private void setUpUI() {

        firstNameEditText = (EditText)findViewById(R.id.studentFirstNameEditText);
        lastNameEditText = (EditText)findViewById(R.id.studentLastNameEditText);
        emailEditText = (EditText)findViewById(R.id.studentEmailEditText);
        parent1FirstNameEditText = (EditText)findViewById(R.id.studentParent1FirstNameEditText);
        parent1LastNameEditText = (EditText)findViewById(R.id.studentParent1LastNameEditText);
        primaryPhoneEditText = (EditText)findViewById(R.id.studentPrimaryPhoneNumEditText);
        parent2FirstNameEditText = (EditText)findViewById(R.id.studentParent2FirstNameEditText);
        parent2LastNameEditText = (EditText)findViewById(R.id.studentParent2LastNameEditText);
        secondaryPhoneEditText = (EditText)findViewById(R.id.studentSecondaryPhoneNumEditText);


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


        // Bind Spinner to View
        enrollmentTypeSpinner = (Spinner)findViewById(R.id.studentEnrollmentTypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> enrollmentTypeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.enrollmentTypes,
                android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        enrollmentTypeSpinner.setAdapter(enrollmentTypeArrayAdapter);
        enrollmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int choice, long l) {
                //                0 = Persilat Kids
                //                1 = Athletic Adventure Program
                //                2 = Wealth of Health
                //                3 = VibraVision

                switch (choice) {
                    case 0 :
                        rankArrayAdapter = ArrayAdapter.createFromResource(AddStudentActivity.this, R.array.childRanks,
                                android.R.layout.simple_spinner_item);
                        break;
                    case 1 :
                        rankArrayAdapter = ArrayAdapter.createFromResource(AddStudentActivity.this, R.array.AAPRanks,
                                android.R.layout.simple_spinner_item);
                        break;
                    case 2 :
                        rankArrayAdapter = ArrayAdapter.createFromResource(AddStudentActivity.this, R.array.WOHRanks,
                                android.R.layout.simple_spinner_item);
                        break;
                    case 3 :
                        rankArrayAdapter = ArrayAdapter.createFromResource(AddStudentActivity.this, R.array.VVRanks,
                                android.R.layout.simple_spinner_item);
                        break;
                }

                rankSpinner.setAdapter(rankArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            // do nothing
            }
        });


        //Bind the spinner to View
        rankSpinner = (Spinner) findViewById(R.id.studentRankSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        rankArrayAdapter = ArrayAdapter.createFromResource(this, R.array.childRanks,
                android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        rankSpinner.setAdapter(rankArrayAdapter);


        // Bind Spinner to View
        primaryPhoneTypeSpinner = (Spinner)findViewById(R.id.studentPrimaryPhoneTypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> primaryPhoneTypeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.phoneTypes,
                android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        primaryPhoneTypeSpinner.setAdapter(primaryPhoneTypeArrayAdapter);



        // Bind Spinner to View
        parent1TypeSpinner = (Spinner)findViewById(R.id.studentParent1TypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> parent1TypeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.parentTypes,
                android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        parent1TypeSpinner.setAdapter(parent1TypeArrayAdapter);


        // Bind Spinner to View
        secondaryPhoneTypeSpinner = (Spinner)findViewById(R.id.studentSecondaryPhoneTypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> secondaryPhoneTypeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.phoneTypes,
                android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        secondaryPhoneTypeSpinner.setAdapter(secondaryPhoneTypeArrayAdapter);


        // Bind Spinner to View
        parent2TypeSpinner = (Spinner)findViewById(R.id.studentParent2TypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> parent2TypeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.parentTypes,
                android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        parent2TypeSpinner.setAdapter(parent2TypeArrayAdapter);


        parent1FirstAndLastNameTextViewLayout = (LinearLayout)findViewById(R.id.parent1FirstAndlastNameTextViews);
        parent1FirstAndLastNameEditTextLayout = (LinearLayout)findViewById(R.id.parent1FirstAndLastNameEditTexts);
        parent2FirstAndLastNameTextViewLayout = (LinearLayout)findViewById(R.id.parent2FirstAndlastNameTextViews);
        parent2FirstAndLastNameEditTextLayout = (LinearLayout)findViewById(R.id.parent2FirstAndlastNameEditTexts);
    }
}
