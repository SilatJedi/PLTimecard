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

import com.silatsaktistudios.plmgr.ListViewArrayAdapters.StudentListViewArrayAdapter;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;


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

        setUpStudentList(studentList());
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
                    studentID = filteredStudentList(searchEditText.getText().toString()).get(position).getId();
                }
                else {
                    studentID = studentList().get(position).getId();
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
                                Realm realm = Realm.getDefaultInstance();

                                Student student;

                                if (isFiltered) {
                                    student = filteredStudentList(searchEditText.getText().toString()).get(position);
                                }
                                else {
                                    student = studentList().get(position);
                                }

                                RealmResults<Lesson> lessons =
                                        realm.where(Lesson.class)
                                                .equalTo("studentID", student.getId())
                                                .findAll();

                                for (final Lesson lesson : lessons) {
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            lesson.setStudentID(-1);
                                        }
                                    });
                                }

                                final Student toDelete = student;

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        toDelete.deleteFromRealm();
                                    }
                                });

                                realm.close();

                                if (isFiltered) {
                                    setUpStudentList(filteredStudentList(searchEditText.getText().toString()));
                                }
                                else {
                                    setUpStudentList(studentList());
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
                    setUpStudentList(filteredStudentList(charSequence.toString()));
                }
                else {
                    isFiltered = false;
                    setUpStudentList(studentList());
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

    private RealmResults<Student> studentList() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Student> students = realm.where(Student.class).findAll().sort("lastName", Sort.ASCENDING, "firstName", Sort.ASCENDING);
        realm.close();

        return students;
    }

    private RealmList<Student> filteredStudentList(String filter) {
        RealmList<Student> students = new RealmList<>();

        for (Student student : studentList()) {
            if (student.getFullName().toLowerCase().contains(filter.toLowerCase())) {
                students.add(student);
            }
        }

        return students;
    }

}
