package com.silatsaktistudios.pltimecard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditInstructorInfoActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText;
    private TextView rankTexView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instructor_info);

        setUpUI();
    }



    private void setUpUI() {
        firstNameEditText = (EditText) findViewById(R.id.instructorFirstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.instructorLastNameEditText);
        emailEditText = (EditText) findViewById(R.id.instructorEmailEditText);
        phoneEditText = (EditText) findViewById(R.id.instructorPhoneNumEditText);

        rankTexView = (TextView) findViewById(R.id.instructorRankField);
    }

    public void setRank(View view) {

    }








}
