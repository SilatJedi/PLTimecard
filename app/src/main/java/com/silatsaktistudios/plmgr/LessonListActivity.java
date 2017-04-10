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
import android.widget.Toast;

import com.silatsaktistudios.plmgr.DataLogic.LessonData;
import com.silatsaktistudios.plmgr.ListViewArrayAdapters.LessonListViewArrayAdapter;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;

import java.util.List;

import io.realm.Realm;


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
        setUpTimecardList(LessonData.lessonList());
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
                    lessonID = LessonData.filteredLessonList(searchEditText.getText().toString())
                            .get(position)
                            .getId();
                }
                else {
                    lessonID = LessonData.lessonList().get(position).getId();
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
                                if (isFiltered) {
                                    String filter = searchEditText.getText().toString();
                                    LessonData.delete(
                                            LessonData.filteredLessonList(filter)
                                                    .get(position)
                                                    .getId());
                                    setUpTimecardList(LessonData.filteredLessonList(filter));
                                }
                                else {
                                    LessonData.delete(LessonData.lessonList().get(position).getId());
                                    setUpTimecardList(LessonData.lessonList());
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
                    setUpTimecardList(LessonData.filteredLessonList(charSequence.toString()));
                }
                else {
                    isFiltered = false;
                    setUpTimecardList(LessonData.lessonList());
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
}
