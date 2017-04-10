package com.silatsaktistudios.plmgr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.silatsaktistudios.plmgr.DataLogic.StudentData;
import com.silatsaktistudios.plmgr.ListViewArrayAdapters.StudentListViewArrayAdapter;
import com.silatsaktistudios.plmgr.Models.Student;

import java.util.List;


public class StudentListActivity extends AppCompatActivity {

    //variables
    private ListView studentsListView;
    EditText searchEditText;
    private boolean isFiltered = false;



    //==============Activity methods=================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        setUpUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpStudentList(StudentData.studentList());
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(StudentListActivity.this, PLMGRActivity.class));
        finish();
    }


  //==================On Click Methods======================================
    public void addNew(View view) {
        Intent addStudentIntent = new Intent(StudentListActivity.this, AddStudentActivity.class);
        startActivity(addStudentIntent);
    }

    public void goBack(View view) {
        onBackPressed();
    }








    private void setUpUI() {
        studentsListView = (ListView)findViewById(R.id.studentsListView);
        studentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Log.d("student select position", String.valueOf(position));
//                Log.d("student id", String.valueOf(students.get(position).getId()));

                int studentID;

                if (isFiltered) {
                    studentID = StudentData.filteredStudentList(searchEditText.getText().toString()).get(position).getId();
                }
                else {
                    studentID = StudentData.studentList().get(position).getId();
                }

                Intent i = new Intent(StudentListActivity.this, ViewStudentActivity.class);
                i.putExtra("studentID", studentID);
                startActivity(i);
                finish();
            }
        });
        studentsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(StudentListActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle("Delete Student?")
                        .setMessage("Are you sure that you want to do this? Please note that this cannot be undone.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isFiltered) {
                                    String filter = searchEditText.getText().toString();
                                    StudentData.delete(StudentData.filteredStudentList(filter).get(position).getId());
                                    setUpStudentList(StudentData.filteredStudentList(filter));
                                }
                                else {
                                    StudentData.delete(StudentData.studentList().get(position).getId());
                                    setUpStudentList(StudentData.studentList());
                                }

                             }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                return false;
            }
        });

        searchEditText = (EditText) findViewById(R.id.studentSearchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    isFiltered = true;
                    setUpStudentList(StudentData.filteredStudentList(charSequence.toString()));
                }
                else {
                    isFiltered = false;
                    setUpStudentList(StudentData.studentList());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }



    private void setUpStudentList(List<Student> students) {
        StudentListViewArrayAdapter studentListViewArrayAdapter = new StudentListViewArrayAdapter(
                StudentListActivity.this,
                students);
        studentsListView.setAdapter(studentListViewArrayAdapter);
    }
}
