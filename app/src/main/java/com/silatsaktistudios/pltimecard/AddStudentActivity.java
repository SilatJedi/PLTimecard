package com.silatsaktistudios.pltimecard;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.silatsaktistudios.pltimecard.Models.Student;

import io.realm.Realm;

public class AddStudentActivity extends AppCompatActivity {

    //constants
    private final int ADULT_STUDENT = 1, CHILD_STUDENT = 0;




    //variables
    private EditText firstNameEditText, lastNameEditText, emailEditText, parent1FirstNameEditText,
            parent1LastNameEditText, primaryPhoneEditText, parent2FirstNameEditText,
            parent2LastNameEditText, secondaryPhoneEditText;

    private TextView childTextView, adultTextView, parent1HeadingTextView,
            primaryPhoneNumTextView, primaryPhoneTypeTextView, parent1TypeTextView,
            parent2HeadingTextView, secondaryPhoneNumTextView, secondaryPhoneTypeTextView,
            parent2TypeTextView, enrollmentTypeTextView, rankTextView, primaryPhoneTypeValueTextView,
            secondaryPhoneTypeValueTextView;

    private Spinner parent1TypeSpinner, parent2TypeSpinner;

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

            enrollmentTypeTextView.setText(getResources().getStringArray(R.array.enrollmentTypes)[ADULT_STUDENT]);
            rankTextView.setText("");

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
                    primaryPhoneTypeValueTextView.getText().toString(),
                    secondaryPhoneEditText.getText().toString(),
                    secondaryPhoneTypeValueTextView.getText().toString(),
                    emailEditText.getText().toString(),
                    rankTextView.getText().toString(),
                    enrollmentTypeTextView.getText().toString(),
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
                    primaryPhoneTypeValueTextView.getText().toString(),
                    secondaryPhoneEditText.getText().toString(),
                    secondaryPhoneTypeValueTextView.getText().toString(),
                    emailEditText.getText().toString(),
                    rankTextView.getText().toString(),
                    enrollmentTypeTextView.getText().toString());

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(student);
                }
            });
        }

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
                    .setTitle("Select Enrollment Type")
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
                .setTitle("Select Enrollment Type")
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
                .setTitle("Select Enrollment Type")
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
        enrollmentTypeTextView = (TextView) findViewById(R.id.studentEnrollmentTypeSpinner);
        rankTextView = (TextView) findViewById(R.id.studentRankSpinner);





        // Bind Spinner to View
        primaryPhoneTypeValueTextView = (TextView)findViewById(R.id.studentPrimaryPhoneTypeSpinner);



        // Bind Spinner to View
        parent1TypeSpinner = (Spinner)findViewById(R.id.studentParent1TypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> parent1TypeArrayAdapter = ArrayAdapter.createFromResource(this, R.array.parentTypes,
                android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        parent1TypeSpinner.setAdapter(parent1TypeArrayAdapter);


        // Bind Spinner to View
        secondaryPhoneTypeValueTextView = (TextView)findViewById(R.id.studentSecondaryPhoneTypeSpinner);


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
