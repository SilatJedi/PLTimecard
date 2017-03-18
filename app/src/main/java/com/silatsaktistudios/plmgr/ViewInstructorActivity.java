package com.silatsaktistudios.plmgr;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.silatsaktistudios.plmgr.Models.Instructor;

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewInstructorActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText;
    private TextView rankTextView;
    private TextView titleTextView;
    private RealmResults<Instructor> instructors;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_instructor);

        Realm realm = Realm.getDefaultInstance();
        instructors = realm.where(Instructor.class).findAll();
        setUpUI();
    }



    private void setUpUI() {

        firstNameEditText = (EditText) findViewById(R.id.instructorFirstNameEditText);

        lastNameEditText = (EditText) findViewById(R.id.instructorLastNameEditText);

        emailEditText = (EditText) findViewById(R.id.instructorEmailEditText);

        phoneEditText = (EditText) findViewById(R.id.instructorPhoneNumEditText);

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
        if (instructors.size() ==0) {
            finish();
        }

    }

    public void submit(View view) {

        if (validateFields()) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(
                            new Instructor(
                                firstNameEditText.getText().toString(),
                                lastNameEditText.getText().toString(),
                                rankTextView.getText().toString(),
                                titleTextView.getText().toString()
                            )
                    );
                }
            });
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

        if (!validateforBlank(titleTextView)) {
            isValid = false;
        }

        if (!validateforBlank(firstNameEditText)) {
            isValid = false;
        }

        if (!validateforBlank(lastNameEditText)) {
            isValid = false;
        }

        if (!validateforBlank(emailEditText)) {
            isValid = false;
        }

        if (!validateforBlank(phoneEditText)) {
            isValid = false;
        }

        if (!validateforBlank(rankTextView)) {
            isValid = false;
        }

        return isValid;
    }

    private boolean validateforBlank(EditText editText) {

        if (editText.getText().toString().isEmpty()) {
            editText.setBackgroundColor(Color.RED);
            return false;
        }
        else{
            editText.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
            return true;
        }
    }

    private boolean validateforBlank(TextView textView) {

        if (textView.getText().toString().isEmpty()) {
            textView.setBackgroundColor(Color.RED);
            return false;
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
            return true;
        }
    }


}
