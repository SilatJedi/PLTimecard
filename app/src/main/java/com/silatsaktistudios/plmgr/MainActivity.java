package com.silatsaktistudios.plmgr;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.silatsaktistudios.plmgr.Fragments.LessonDetailFragment;
import com.silatsaktistudios.plmgr.Fragments.LessonListFragment;
import com.silatsaktistudios.plmgr.Models.Instructor;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements
        LessonDetailFragment.OnFragmentInteractionListener,
        LessonListFragment.OnFragmentInteractionListener{

    private FragmentManager fragmentManager;
    private Fragment lessonListFragment;
    private LessonDetailFragment lessonDetailFragment;
    MenuItem editMenuItem, saveMenuItem, deleteMenuItem, cancelMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder(getApplicationContext())
                        .name("MPPLDB.realm")
                        .build();
        Realm.setDefaultConfiguration(realmConfiguration);


        Toolbar toolBar = (Toolbar) findViewById(R.id.plmgrToolbar);
        setSupportActionBar(toolBar);


        setUpNavBar();

        fragmentManager = getSupportFragmentManager();



        createDemoData();

        if(lessonListFragment == null) {

            lessonListFragment = LessonListFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, lessonListFragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        editMenuItem = menu.findItem(R.id.edit_menu_item);
        saveMenuItem = menu.findItem(R.id.save_menu_item);
        deleteMenuItem = menu.findItem(R.id.delete_menu_item);
        cancelMenuItem = menu.findItem(R.id.cancel_menu_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.submit_timecard_menu_item:
                Toast.makeText(getApplicationContext(),"need to write code to submit timecard",Toast.LENGTH_SHORT).show();
                break;
            case R.id.options_menu_item:
                Toast.makeText(getApplicationContext(),"need to write code to go to options",Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit_menu_item:
                editMenuItemFunctions();
                break;
            case R.id.save_menu_item:
                saveMenuItemFunctions();
                break;
            case R.id.delete_menu_item:
                Toast.makeText(getApplicationContext(),"need to write code to delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel_menu_item:
                Toast.makeText(getApplicationContext(),"need to write code to cancel",Toast.LENGTH_SHORT).show();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpNavBar() {
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem timeCardItem = new AHBottomNavigationItem("Timecard", R.drawable.cash);
        AHBottomNavigationItem lessonNavItem = new AHBottomNavigationItem("Lessons", R.drawable.note_multiple);
        AHBottomNavigationItem studentNavItem = new AHBottomNavigationItem("Students", R.drawable.student_icon);

        //add items
        bottomNavigation.addItem(timeCardItem);
        bottomNavigation.addItem(lessonNavItem);
        bottomNavigation.addItem(studentNavItem);

        //set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        // Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);



        // Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "tapped", Toast.LENGTH_SHORT).show();
                        break;
                    default: break;
                }

                return true;
            }
        });
    }



    @Override
    public void onLessonListItemClick(int lessonId) {
        lessonDetailFragment = LessonDetailFragment.newInstance(lessonId, true);

        fragmentManager.beginTransaction()
                .remove(lessonListFragment)
                .add(R.id.fragmentContainer, lessonDetailFragment)
                .commit();

        editMenuItem.setVisible(true);
        deleteMenuItem.setVisible(true);
        cancelMenuItem.setVisible(true);
    }



    private void editMenuItemFunctions(){
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        editMenuItem.setVisible(false);
        saveMenuItem.setVisible(true);

        if(f instanceof LessonDetailFragment) {
            lessonDetailFragment.editLesson();
        }

    }

    private void saveMenuItemFunctions() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        editMenuItem.setVisible(true);
        saveMenuItem.setVisible(false);

        if(f instanceof LessonDetailFragment) {
            lessonDetailFragment.saveLesson();
        }
    }

































    public void createDemoData() {

        Log.d("resetting DB", "blam!");

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });

        for (int i = 0; i < 10; i++) {

            final Student adult = new Student(
                    "Adult",
                    "Student" + ( i + 1),
                    "555555555" + i,
                    "Cell",
                    "55555555" + i + "5",
                    "Home",
                    "adult-student" + i + "@mail.net",
                    "Dasar 1",
                    "Athletic Adventure Program");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(adult);
                }
            });

            final Student child = new Student(
                    "Child",
                    "Student" + ( i + 1),
                    "555555555" + i,
                    "Cell",
                    "55555555" + i + "5",
                    "Cell",
                    "child-student" + i + "@mail.net",
                    "White Belt",
                    "Persilat Kids",
                    "Parent1",
                    "Student" + ( i + 1),
                    "Mother",
                    "Parent2",
                    "Student" + ( i + 1),
                    "Father");

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(child);
                }
            });



        }



        final RealmResults<Student> students = realm.where(Student.class).findAll();

        for (final Student student : students) {
            final Lesson lesson = new Lesson(
                    student.getId(),
                    student.getFirstName() + " " + student.getLastName(),
                    new Date(),
                    4f,
                    "Good Job",
                    true,true,false);

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    student.addLesson(lesson);
                }
            });
        }

        final Instructor instructor = new Instructor(
                "Obi-wan",
                "Kenobi",
                "Jedi Master", "Mas", "0855378008", "sithsuckass@jediorder.org");

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(instructor);
            }
        });

        realm.close();
    }
}
