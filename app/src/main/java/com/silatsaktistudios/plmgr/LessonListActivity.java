package com.silatsaktistudios.plmgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.silatsaktistudios.plmgr.ListViewArrayAdapters.MainActivity.TimeCardListViewArrayAdapter;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;
import com.silatsaktistudios.plmgr.Models.TimeCard;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class LessonListActivity extends AppCompatActivity {


    private TextView timecardTextView;
    private ListView timecardListView;
    private RealmResults<Lesson> lessons;


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
        setUpTimecardList();
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(LessonListActivity.this, PLMGRActivity.class));
        finish();
    }



    //===============On Click Methods==========================================
    public void goBack(View view) {
        onBackPressed();
    }

    public void addNew(View view) {

        Realm realm = Realm.getDefaultInstance();

        if(realm.where(Student.class).findAll().size() != 0) {
            Intent addLessonIntent = new Intent(LessonListActivity.this, AddLessonActivity.class);
            startActivity(addLessonIntent);
        } else {
            Toast.makeText(this, "You need to add students first.", Toast.LENGTH_SHORT).show();
        }

        realm.close();

    }




    //===========================================Helper Methods==========================================
    private void setUpTimecardList(){

        Realm realm = Realm.getDefaultInstance();

        Calendar firstOfMonth = Calendar.getInstance();
        firstOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstOfMonth.set(Calendar.MINUTE, 0);

        Calendar now = Calendar.getInstance();

        if(realm.where(TimeCard.class).findAll().size() == 0) {
            Log.d("timecard list", "is empty");
            final TimeCard newTimecard = new TimeCard(firstOfMonth.getTime());

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(newTimecard);
                }
            });
        }

        RealmResults<TimeCard> timecards = realm.where(TimeCard.class).findAll();

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

            final TimeCard newTimecard = new TimeCard(firstOfMonth.getTime());

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(newTimecard);
                }
            });


            //Todo:: notify user that a new timecard has been created and give them the option to submit previous timecard
        }

        timecards = realm.where(TimeCard.class).findAll();
        TimeCard timecard = timecards.get(timecards.size() - 1);

        lessons = timecard.getLessons().sort("date", Sort.ASCENDING);

        if(lessons.size() > 0) {

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
                    LessonListActivity.this,
                    names,
                    dates,
                    showedUps,
                    eligibles,
                    makeUps);

            timecardListView.setAdapter(timeCardListViewArrayAdapter);
        }

        realm.close();
    }











//=========================================Class Methods========================================

    private void setUpUI() {

        final Realm realm = Realm.getDefaultInstance();



        timecardListView = (ListView)findViewById(R.id.timecardListView);
        timecardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



        realm.close();
    }
    }
