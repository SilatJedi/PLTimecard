package com.silatsaktistudios.plmgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.silatsaktistudios.plmgr.ListViewArrayAdapters.MainActivity.StudentListViewArrayAdapter;
import com.silatsaktistudios.plmgr.Models.Student;

import io.realm.Realm;
import io.realm.RealmResults;


public class StudentListActivity extends AppCompatActivity {

    //variables
    private ListView studentsListView;
    private RealmResults<Student> students;




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

        setUpStudentList();
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







//========================helper methods======================================
    private void setUpUI() {
        studentsListView = (ListView)findViewById(R.id.studentsListView);
        studentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Log.d("student select position", String.valueOf(position));
//                Log.d("student id", String.valueOf(students.get(position).getId()));

                Intent i = new Intent(StudentListActivity.this, ViewStudentActivity.class);
                i.putExtra("studentID", students.get(position).getId());
                startActivity(i);
                finish();
            }
        });
    }

    private void setUpStudentList() {

        Realm realm = Realm.getDefaultInstance();
        students = realm.where(Student.class).findAll();

        if(students.size() > 0) {
            String[] names = new String[students.size()];
            String[] enrollmentTypes = new String[students.size()];
            String[] ranks = new String[students.size()];

            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);

                names[i] = student.getFullName();
                enrollmentTypes[i] = student.getEnrollmentType();
                ranks[i] = student.getRank();
            }

            StudentListViewArrayAdapter studentListViewArrayAdapter = new StudentListViewArrayAdapter(
                    StudentListActivity.this,
                    names,
                    enrollmentTypes,
                    ranks);
            studentsListView.setAdapter(studentListViewArrayAdapter);
        }

        realm.close();
    }
}
