package com.silatsaktistudios.plmgr;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.silatsaktistudios.plmgr.Models.Configs;
import com.silatsaktistudios.plmgr.Models.Instructor;
import com.silatsaktistudios.plmgr.Old.PLMGRActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditInstructorActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText, reportingEmailEditText;
    private TextView rankTextView;
    private TextView titleTextView;
    private RealmResults<Instructor> instructors;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instructor);

        Realm realm = Realm.getDefaultInstance();
        instructors = realm.where(Instructor.class).findAll();
        setUpUI();
    }



    private void setUpUI() {

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateForBlank((EditText) v);
                }
            }
        };

        firstNameEditText = (EditText) findViewById(R.id.instructorFirstNameEditText);
        firstNameEditText.setOnFocusChangeListener(onFocusChangeListener);

        lastNameEditText = (EditText) findViewById(R.id.instructorLastNameEditText);
        lastNameEditText.setOnFocusChangeListener(onFocusChangeListener);

        emailEditText = (EditText) findViewById(R.id.instructorEmailEditText);
        emailEditText.setOnFocusChangeListener(onFocusChangeListener);

        phoneEditText = (EditText) findViewById(R.id.instructorPhoneNumEditText);
        phoneEditText.setOnFocusChangeListener(onFocusChangeListener);

        reportingEmailEditText = (EditText) findViewById(R.id.reportingEmailEditText);
        reportingEmailEditText.setOnFocusChangeListener(onFocusChangeListener);

        titleTextView = (TextView) findViewById(R.id.instructorTitleField);
        rankTextView = (TextView) findViewById(R.id.instructorRankField);

        TextView activityTitleTextView = (TextView) findViewById(R.id.viewInstructorActivityTitle);
        if (instructors.size() == 0) {
            activityTitleTextView.setText(R.string.add_new_instructor);
        }
    }

    public void selectRank(View view) {

         new AlertDialog.Builder(this)
                .setTitle("Select Rank")
                .setSingleChoiceItems(getResources().getStringArray(R.array.instructorRanks), -1,
                        new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selection) {

                        rankTextView.setText(getResources().getStringArray(R.array.instructorRanks)[selection]);
                        rankTextView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null)
                 .show();
    }

    public void selectTitle(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Select Rank")
                .setSingleChoiceItems(getResources().getStringArray(R.array.instructorTitles), -1,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int selection) {

                                titleTextView.setText(getResources().getStringArray(R.array.instructorTitles)[selection]);
                                titleTextView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                                dialogInterface.dismiss();
                            }
                        })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void cancel(View view) {
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("Cancel")
                .setMessage("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(EditInstructorActivity.this, PLMGRActivity.class));
                        finish();
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

    public void submit(View view) {

        if (validateFields()) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(
                                new Instructor(
                                        firstNameEditText.getText().toString(),
                                        lastNameEditText.getText().toString(),
                                        rankTextView.getText().toString(),
                                        titleTextView.getText().toString(),
                                        phoneEditText.getText().toString(),
                                        emailEditText.getText().toString()
                                )
                        );

                        realm.insertOrUpdate(
                                new Configs(reportingEmailEditText.getText().toString())
                        );
                    }
                }
            );

            startActivity(new Intent(EditInstructorActivity.this, PLMGRActivity.class));
            finish();
        }
        else {
            new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("Uh-oh!")
                .setMessage("Please do not leave any fields blank")
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .show();
        }
    }

    private boolean validateFields() {

        boolean isValid = true;

        if (!validateForBlank(titleTextView)) {
            isValid = false;
        }

        if (!validateForBlank(firstNameEditText)) {
            isValid = false;
        }

        if (!validateForBlank(lastNameEditText)) {
            isValid = false;
        }

        if (!validateForBlank(emailEditText)) {
            isValid = false;
        }

        if (!validateForBlank(phoneEditText)) {
            isValid = false;
        }

        if (!validateForBlank(rankTextView)) {
            isValid = false;
        }

        return isValid;
    }

    @SuppressWarnings("deprecation")
    private boolean validateForBlank(EditText editText) {

        if (editText.getText().toString().isEmpty()) {



            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                // do something for phones running an SDK before lollipop
                editText.setHintTextColor(getResources().getColor(R.color.yellow));
                editText.setBackground(getResources().getDrawable(R.drawable.red_rounded_corners));
            }
            else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                // do something for below SDK marshmallow
                editText.setHintTextColor(getResources().getColor(R.color.yellow));
                editText.setBackground(getResources().getDrawable(R.drawable.red_rounded_corners));
            }
            else {
                // Do something for lollipop and above versions
                editText.setHintTextColor(getResources().getColor(R.color.yellow, null));
                editText.setBackground(getResources().getDrawable(R.drawable.red_rounded_corners, null));
            }
            return false;
        }
        else{
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                // do something for phones running an SDK before lollipop
                editText.setBackground(getResources().getDrawable(R.drawable.gray_rounded_corners));
            }
            else{
                // Do something for lollipop and above
                editText.setBackground(getResources().getDrawable(R.drawable.gray_rounded_corners, null));
            }

            return true;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean validateForBlank(TextView textView) {

        if (textView.getText().toString().isEmpty()) {
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                // do something for phones running an SDK before lollipop
                textView.setBackground(getResources().getDrawable(R.drawable.red_rounded_corners));
                textView.setHintTextColor(getResources().getColor(R.color.yellow));
            }
            else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                // do something for below SDK marshmallow
                textView.setHintTextColor(getResources().getColor(R.color.yellow));
                textView.setBackground(getResources().getDrawable(R.drawable.red_rounded_corners));
            }
            else {
                // Do something for lollipop and above versions
                textView.setHintTextColor(getResources().getColor(R.color.yellow, null));
                textView.setBackground(getResources().getDrawable(R.drawable.red_rounded_corners, null));
            }
            return false;
        }
        else{
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                // do something for phones running an SDK before lollipop
                textView.setBackground(getResources().getDrawable(R.drawable.gray_rounded_corners));
            } else{
                // Do something for lollipop and above
                textView.setBackground(getResources().getDrawable(R.drawable.gray_rounded_corners, null));
            }

            return true;
        }
    }


}
