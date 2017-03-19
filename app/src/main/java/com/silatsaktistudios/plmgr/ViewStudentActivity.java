package com.silatsaktistudios.plmgr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.silatsaktistudios.plmgr.Models.Student;

import io.realm.Realm;

public class ViewStudentActivity extends AppCompatActivity {

    //variables
    private TextView parent1HeadingTextView,
            primaryPhoneNumTextView, primaryPhoneTypeTextView, parent1TypeTextView,
            parent2HeadingTextView, secondaryPhoneNumTextView, secondaryPhoneTypeTextView,
            parent2TypeTextView, enrollmentTypeTextView, rankTextView, primaryPhoneTypeValueTextView,
            secondaryPhoneTypeValueTextView, parent1TypeValueTextView, parent2TypeValueTextView,
            firstNameTextView, lastNameTextView, emailTextView, parent1FirstNameTextView,
            parent1LastNameTextView, primaryPhoneTextView, parent2FirstNameTextView,
            parent2LastNameTextView, secondaryPhoneTextView;

    private LinearLayout parent1FirstAndLastNameTextViewLayout, parent1FirstAndLastNameEditTextLayout,
            parent2FirstAndLastNameTextViewLayout, parent2FirstAndLastNameEditTextLayout;

    private int studentID = -1;
    private Student student;

    //=================== Activity Methods==================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        setUpUI();

        if (getIntent().hasExtra("studentID")) {
            studentID = getIntent().getIntExtra("studentID", -1);
        }

        if (studentID == -1) {
            goBack(null);
        } else {
            displayStudentInfo();
        }
    }


    //=======================On Click Methods=============================
    public void goBack(View view) {

        Intent i = new Intent(ViewStudentActivity.this, PLMGRActivity.class);
        i.putExtra("listView", PLMGRActivity.STUDENT);
        startActivity(i);
        finish();
    }

    public void edit(View view) {

    }


    private void setUpUI() {
        firstNameTextView = (TextView) findViewById(R.id.studentFirstNameTextView);
        lastNameTextView = (TextView) findViewById(R.id.studentLastNameTextView);
        emailTextView = (TextView) findViewById(R.id.studentEmailTextView);
        parent1FirstNameTextView = (TextView) findViewById(R.id.studentParent1FirstName);
        parent1LastNameTextView = (TextView) findViewById(R.id.studentParent1LastName);
        primaryPhoneTextView = (TextView) findViewById(R.id.studentPrimaryPhoneNum);
        parent2FirstNameTextView = (TextView) findViewById(R.id.studentParent2FirstName);
        parent2LastNameTextView = (TextView) findViewById(R.id.studentParent2LastName);
        secondaryPhoneTextView = (TextView) findViewById(R.id.studentSecondaryPhoneNum);
        parent1HeadingTextView = (TextView) findViewById(R.id.studentParent1HeadingTextView);
        primaryPhoneNumTextView = (TextView) findViewById(R.id.studentPrimaryPhoneNumTextView);
        primaryPhoneTypeTextView = (TextView) findViewById(R.id.studentPrimaryPhoneTypeTextView);
        parent1TypeTextView = (TextView) findViewById(R.id.studentParent1TypeTextView);
        parent2HeadingTextView = (TextView) findViewById(R.id.studentParent2HeadingTextView);
        secondaryPhoneNumTextView = (TextView) findViewById(R.id.studentSecondaryPhoneNumTextView);
        secondaryPhoneTypeTextView = (TextView) findViewById(R.id.studentSecondaryPhoneTypeTextView);
        parent2TypeTextView = (TextView) findViewById(R.id.studentParent2TypeTextView);
        enrollmentTypeTextView = (TextView) findViewById(R.id.studentEnrollmentTypeTextView);
        rankTextView = (TextView) findViewById(R.id.studentRankTextView);
        primaryPhoneTypeValueTextView = (TextView) findViewById(R.id.studentPrimaryPhoneType);
        parent1TypeValueTextView = (TextView) findViewById(R.id.studentParent1Type);
        secondaryPhoneTypeValueTextView = (TextView) findViewById(R.id.studentSecondaryPhoneType);
        parent2TypeValueTextView = (TextView) findViewById(R.id.studentParent2Type);


        parent1FirstAndLastNameTextViewLayout = (LinearLayout) findViewById(R.id.parent1FirstAndlastNameTextViews);
        parent1FirstAndLastNameEditTextLayout = (LinearLayout) findViewById(R.id.parent1FirstAndLastNameEditTexts);
        parent2FirstAndLastNameTextViewLayout = (LinearLayout) findViewById(R.id.parent2FirstAndlastNameTextViews);
        parent2FirstAndLastNameEditTextLayout = (LinearLayout) findViewById(R.id.parent2FirstAndlastNameEditTexts);
    }


    public void changeToAdult() {

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


    }

    private void displayStudentInfo() {
        Realm realm = Realm.getDefaultInstance();
        student = realm.where(Student.class).equalTo("id", studentID).findFirst();

        firstNameTextView.setText(student.getFirstName());
        lastNameTextView.setText(student.getLastName());
        enrollmentTypeTextView.setText(student.getEnrollmentType());
        rankTextView.setText(student.getRank());
        emailTextView.setText(student.getEmail());
        primaryPhoneTextView.setText(formatPhoneNumber(student.getPrimaryPhone()));
        primaryPhoneTypeValueTextView.setText(student.getPrimaryPhoneType());
        secondaryPhoneTextView.setText(formatPhoneNumber(student.getSecondaryPhone()));
        secondaryPhoneTypeValueTextView.setText(student.getSecondaryPhoneType());

        if (student.getStudentType().toLowerCase().equals("adult")) {
            changeToAdult();
        } else {
            parent1FirstNameTextView.setText(student.getParent1FirstName());
            parent1LastNameTextView.setText(student.getParent1LastName());
            parent1TypeValueTextView.setText(student.getParent1type());

            parent2FirstNameTextView.setText(student.getParent2FirstName());
            parent2LastNameTextView.setText(student.getParent2LastName());
            parent2TypeValueTextView.setText(student.getParent2Type());
        }

        realm.close();
    }

    private String formatPhoneNumber(String number) {

        return "(" + number.substring(0, 3) + ")" +
                number.substring(3, 6) + "-" +
                number.substring(6, number.length());
    }

    public void callDialog(View view) {
        final TextView textView = (TextView) view;

        new AlertDialog.Builder(ViewStudentActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("Call " + student.getFullName() + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        call(textView.getText().toString());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();

    }

    private void call(String number) {
        Uri call = Uri.parse("tel:" + number.replace("(", "").replace(")", "").replace("-", ""));
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        callIntent.setData(call);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
            return;
        }else {
            //have got permission
            try{
                startActivity(callIntent);  //call activity and make phone call
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    public void emailDialog(View view) {
        sendEmail();
    }

    private void sendEmail() {
        ShareCompat.IntentBuilder.from(ViewStudentActivity.this)
                .setType("message/rfc822")
                .addEmailTo(student.getEmail())
                //.setSubject("Test Email from PLMGR")
                //.setText("Test email body")
                //.setHtmlText(body) //If you are using HTML in your body text
                .setChooserTitle("Send email to " + student.getFullName() + "?")
                .startChooser();

//        Intent emailIntent  = new Intent(Intent.ACTION_SENDTO);
//        emailIntent.setData(Uri.parse("mailto: silatjedi@gmail.com"));
//        Intent.createChooser(emailIntent, "Send email to " + student.getFullName() + "?");
    }
}
