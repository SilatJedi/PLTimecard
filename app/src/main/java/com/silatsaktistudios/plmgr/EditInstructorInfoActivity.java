package com.silatsaktistudios.plmgr;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditInstructorInfoActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, emailEditText, phoneEditText;
    private TextView rankTextView;





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

        rankTextView = (TextView) findViewById(R.id.instructorRankField);
    }

    public void selectRank(View view) {

        AlertDialog.Builder menu = new AlertDialog.Builder(this)
                .setTitle("Select Rank")
                .setSingleChoiceItems(getResources().getStringArray(R.array.AAPRanks), -1,
                        new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int selection) {

                        rankTextView.setText(getResources().getStringArray(R.array.AAPRanks)[selection]);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null);

        menu.show();
    }








}
