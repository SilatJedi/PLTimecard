package com.silatsaktistudios.pltimecard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.silatsaktistudios.pltimecard.ListViewArrayAdapters.MainActivity.StudentListViewArrayAdapter;
import com.silatsaktistudios.pltimecard.ListViewArrayAdapters.MainActivity.TimeCardListViewArrayAdapter;
import com.silatsaktistudios.pltimecard.Models.Lesson;
import com.silatsaktistudios.pltimecard.Models.Student;
import com.silatsaktistudios.pltimecard.Models.Timecard;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private TextView timecardTextView, studentsTextView;
    private ListView timecardListView, studentsListView;


//==================================Activity Methods================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder(getApplicationContext())
                        .name("MPPLDB.realm")
                        .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        realm = Realm.getDefaultInstance();
        setUpUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!realm.isClosed()) {
            realm.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }

        setUpStudentList();
        setUpTimecardList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!realm.isClosed()) {
            realm.close();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }










//========================================On Click Methods==========================================
    public void summonMenu(View view) {

        String[] menuItems = {"Submit Timecard", "Timecard History", "Edit User Info"};
        AlertDialog.Builder menu = new AlertDialog.Builder(this)
                .setTitle("Menu")
                .setSingleChoiceItems(menuItems, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int selection) {

                switch(selection) {
                    case 0 : //Todo: create submit timecard stuff
                        break;
                    case 1 : //Todo: create timecard history stuff
                        break;
                    case 2 : //Todo: create edit user info stuff
                        break;
                    default :
                        break;
                }

                dialogInterface.dismiss();
            }
        })
        .setNegativeButton("Cancel", null);

        menu.show();
    }

    public void showTimecard(View view) {

        if(timecardListView.getVisibility() != View.VISIBLE) {
            timecardListView.setVisibility(View.VISIBLE);
            timecardTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
            studentsListView.setVisibility(View.INVISIBLE);
            studentsTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }
    }

    public void showStudents(View view) {

        if(studentsListView.getVisibility() != View.VISIBLE) {
            studentsListView.setVisibility(View.VISIBLE);
            studentsTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
            timecardListView.setVisibility(View.INVISIBLE);
            timecardTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }
    }

    public void addNew(View view) {
        //add to the correct list based on the visibility of the timecard list view
        if(timecardListView.getVisibility() == View.VISIBLE) {
            Intent addLessonIntent = new Intent(MainActivity.this, AddLessonActivity.class);
            startActivity(addLessonIntent);
        } else {
            Intent addStudentIntent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(addStudentIntent);
        }
    }











//===========================================Helper Methods==========================================
    private void setUpTimecardList(){
        Calendar firstOfMonth = Calendar.getInstance();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstOfMonth.set(Calendar.MINUTE, 0);

        Calendar now = Calendar.getInstance();

        if(realm.where(Timecard.class).findAll().size() == 0) {
            Log.d("timecard list", "is empty");
            final Timecard newTimecard = new Timecard(firstOfMonth.getTime());

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(newTimecard);
                }
            });
        }

        RealmResults<Timecard> timecards = realm.where(Timecard.class).findAll();

        Calendar calFromDate = Calendar.getInstance();
        calFromDate.setTimeInMillis(timecards.get(timecards.size() - 1).getStartDate().getTime());

        int timeCardMonth = calFromDate.get(Calendar.MONTH);
        int currentMonth = now.get(Calendar.MONTH);

        Log.d("timecard month int", String.valueOf(timeCardMonth));
        Log.d("current month int", String.valueOf(currentMonth));
        Log.d("calendar.january", String.valueOf(Calendar.JANUARY));
        Log.d("calendar.december", String.valueOf(Calendar.DECEMBER));

        if(currentMonth > timeCardMonth ||
                (currentMonth == Calendar.JANUARY &&
                        timeCardMonth == Calendar.DECEMBER) ) {

            final Timecard newTimecard = new Timecard(firstOfMonth.getTime());

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(newTimecard);
                }
            });


            //Todo:: notify user that a new timecard has been created and give them the option to submit previous timecard
        }

        timecards = realm.where(Timecard.class).findAll();
        Timecard timecard = timecards.get(timecards.size() - 1);

        RealmResults<Lesson> lessons = timecard.getLessons().sort("date", Sort.DESCENDING);

        if(timecard.getLessons().size() > 0) {

            String[] names = new String[lessons.size()];
            Date[] dates = new Date[lessons.size()];
            boolean[] showedUps = new boolean[lessons.size()];
            boolean[] eligibles = new boolean[lessons.size()];
            boolean[] makeUps = new boolean[lessons.size()];

            for(int i = 0; i < timecard.getLessons().size(); i++) {
                Lesson lesson = lessons.get(i);

                names[i] = lesson.getStudentName();
                dates[i] = lesson.getDate();
                showedUps[i] = lesson.didShowUp();
                eligibles[i] = lesson.isEligible();
                makeUps[i] = lesson.isMakeUp();
            }
            TimeCardListViewArrayAdapter timeCardListViewArrayAdapter = new TimeCardListViewArrayAdapter(
                    MainActivity.this,
                    names,
                    dates,
                    showedUps,
                    eligibles,
                    makeUps);

            timecardListView.setAdapter(timeCardListViewArrayAdapter);
        }
    }





    private void setUpStudentList() {

        RealmResults<Student> students = realm.where(Student.class).findAll();

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
                    MainActivity.this,
                    names,
                    enrollmentTypes,
                    ranks);
            studentsListView.setAdapter(studentListViewArrayAdapter);
        }
    }




    private void setUpUI() {

        timecardTextView = (TextView)findViewById(R.id.timecardTextView);
        timecardListView = (ListView)findViewById(R.id.timecardListView);
        setUpTimecardList();

        studentsTextView = (TextView)findViewById(R.id.studentsTextView);
        studentsListView = (ListView)findViewById(R.id.studentsListView);

        EditText searchEditText = (EditText) findViewById(R.id.searchEditText);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //do nothing
            }
        });
    }
//=========================================End Class Methods========================================
}
