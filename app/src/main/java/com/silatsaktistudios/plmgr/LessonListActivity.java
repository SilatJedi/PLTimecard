package com.silatsaktistudios.plmgr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.silatsaktistudios.plmgr.ListViewArrayAdapters.MainActivity.LessonListViewArrayAdapter;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;
import com.silatsaktistudios.plmgr.Models.TimeCard;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;


public class LessonListActivity extends AppCompatActivity {


    private ListView timecardListView;
    EditText searchEditText;
    private boolean isFiltered = false;

    //==============Activity Methods========================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
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
        setUpTimecardList(lessonList());
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(LessonListActivity.this, PLMGRActivity.class));
        finish();
    }


    //===============On Click Methods==========================================
    public void addNew(View view) {

        Realm realm = Realm.getDefaultInstance();

        if (realm.where(Student.class).findAll().size() != 0) {
            Intent addLessonIntent = new Intent(LessonListActivity.this, AddLessonActivity.class);
            startActivity(addLessonIntent);
        } else {
            Toast.makeText(this, "You need to add students first.", Toast.LENGTH_SHORT).show();
        }

        realm.close();

    }

    public void goBack(View view) {onBackPressed();}



    private void setUpUI() {
        timecardListView = (ListView) findViewById(R.id.timecardListView);
        timecardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int lessonID;

                if(isFiltered) {
                    lessonID = filteredLessonList(searchEditText.getText().toString()).get(position).getId();
                }
                else {
                    lessonID = lessonList().get(position).getId();
                }

                Intent i = new Intent(LessonListActivity.this, ViewLessonActivity.class);
                i.putExtra("lessonID", lessonID);
                startActivity(i);
                finish();
                }
            }
        );
        timecardListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long l) {
                new AlertDialog.Builder(LessonListActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle("Delete Lesson?")
                        .setMessage("Are you sure that you want to do this? Please note that this cannot be undone.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Realm realm = Realm.getDefaultInstance();

                                Lesson lesson;

                                if (isFiltered) {
                                    lesson = filteredLessonList(searchEditText.getText().toString()).get(position);
                                }
                                else {
                                    lesson = lessonList().get(position);
                                }

                                final Lesson toDelete = lesson;

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        toDelete.deleteFromRealm();
                                    }
                                });

                                realm.close();

                                if(isFiltered) {
                                    setUpTimecardList(filteredLessonList(searchEditText.getText().toString()));
                                }
                                else {
                                    setUpTimecardList(lessonList());
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

        searchEditText = (EditText) findViewById(R.id.lessonSearchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    isFiltered = true;
                    setUpTimecardList(filteredLessonList(charSequence.toString()));
                }
                else {
                    isFiltered = false;
                    setUpTimecardList(lessonList());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }

    private void setUpTimecardList(List<Lesson> lessons) {
        LessonListViewArrayAdapter lessonListViewArrayAdapter = new LessonListViewArrayAdapter(
                LessonListActivity.this,
                lessons);
        timecardListView.setAdapter(lessonListViewArrayAdapter);
    }

    private RealmResults<Lesson> lessonList() {
        Calendar firstOfMonth = Calendar.getInstance();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstOfMonth.set(Calendar.MINUTE, 0);
        firstOfMonth.set(Calendar.SECOND, 0);
        firstOfMonth.set(Calendar.MILLISECOND, 0);


        Realm realm = Realm.getDefaultInstance();
        RealmResults<Lesson> lessons = realm.where(Lesson.class).greaterThan("date", firstOfMonth.getTime()).findAll();
        realm.close();

        Log.d("lesson list size", String.valueOf(lessons.size()));

        return lessons;
    }

    private RealmList<Lesson> filteredLessonList(String filter) {
        RealmList<Lesson> filteredLessons = new RealmList<>();

        for(Lesson lesson : lessonList()) {
            if(lesson.getStudentName().toLowerCase().contains(filter.toLowerCase())) {
                filteredLessons.add(lesson);
            }
        }

        return filteredLessons;
    }
}
