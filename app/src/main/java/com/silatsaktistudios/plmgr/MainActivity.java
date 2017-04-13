package com.silatsaktistudios.plmgr;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.silatsaktistudios.plmgr.DataLogic.LessonData;
import com.silatsaktistudios.plmgr.Fragments.FragmentHelper;
import com.silatsaktistudios.plmgr.Fragments.LessonDetailFragment;
import com.silatsaktistudios.plmgr.Fragments.LessonListFragment;
import com.silatsaktistudios.plmgr.Fragments.StudentDetailFragment;
import com.silatsaktistudios.plmgr.Fragments.StudentListFragment;
import com.silatsaktistudios.plmgr.Models.Instructor;
import com.silatsaktistudios.plmgr.Models.Lesson;
import com.silatsaktistudios.plmgr.Models.Student;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity implements
        LessonListFragment.OnFragmentInteractionListener,
        StudentListFragment.OnFragmentInteractionListener{

    private AHBottomNavigation bottomNavigation;
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
        createDemoData();

        if(savedInstanceState == null) {
            FragmentHelper.setFragment(MainActivity.this, LessonListFragment.newInstance(), R.id.fragmentContainer);
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
            case R.id.edit_menu_item:
                editMenuItemFunctions();
                break;
            case R.id.save_menu_item:
                saveMenuItemFunctions();
                break;
            case R.id.delete_menu_item:
                deleteMenuItemFunction();
                break;
            case R.id.cancel_menu_item:
                cancelMenuItemFunction();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        cancelMenuItemFunction();
    }























    //toolbar menu item functions
    private void editMenuItemFunctions(){
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        editMenuItem.setVisible(false);
        saveMenuItem.setVisible(true);

        if(f instanceof LessonDetailFragment) {
            ((LessonDetailFragment)f).editLesson();
        }

    }

    private void saveMenuItemFunctions() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        editMenuItem.setVisible(true);
        saveMenuItem.setVisible(false);

        if(f instanceof LessonDetailFragment) {
            ((LessonDetailFragment)f).saveLesson();
        }
    }

    private void cancelMenuItemFunction() {
        bottomNavigation.setCurrentItem(bottomNavigation.getCurrentItem());
    }

    private void deleteMenuItemFunction() {
        new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("Delete Item?")
                .setMessage("Are you sure that you want to do this? Please note that this cannot be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

                        if(f instanceof LessonDetailFragment) {
                            LessonData.delete(((LessonDetailFragment)f).lessonID);
                        }

                        cancelMenuItemFunction();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }















    //FRAGMENT LISTENER EVENTS
    //lesson list fragment events
    @Override
    public void onLessonListItemClick(int lessonId) {
        showToolBarButtons();
        bottomNavigation.hideBottomNavigation(true);
        FragmentHelper.setFragment(MainActivity.this, LessonDetailFragment.newInstance(lessonId), R.id.fragmentContainer);
    }

    @Override
    public void onAddLessonButtonClick() {
        bottomNavigation.hideBottomNavigation(true);
        cancelMenuItem.setVisible(true);
        FragmentHelper.setFragment(MainActivity.this, LessonDetailFragment.newInstance(), R.id.fragmentContainer);
    }

    //student list fragment events
    @Override
    public void onStudentListItemClick(int studentId) {
        showToolBarButtons();
        bottomNavigation.hideBottomNavigation(true);
        FragmentHelper.setFragment(MainActivity.this, StudentDetailFragment.newInstance(studentId), R.id.fragmentContainer);
    }

    @Override
    public void onAddStudentButtonClick() {

    }








    private void setUpNavBar() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem timeCardItem = new AHBottomNavigationItem("Timecard", R.drawable.cash);
        AHBottomNavigationItem lessonNavItem = new AHBottomNavigationItem("Lessons", R.drawable.note_multiple);
        AHBottomNavigationItem studentNavItem = new AHBottomNavigationItem("Students", R.drawable.student_icon);
        AHBottomNavigationItem submitItem = new AHBottomNavigationItem("Send Timecard", R.drawable.send);
        AHBottomNavigationItem settingsItem = new AHBottomNavigationItem("Settings", R.drawable.settings);

        //add items
        bottomNavigation.addItem(timeCardItem);
        bottomNavigation.addItem(lessonNavItem);
        bottomNavigation.addItem(studentNavItem);
        bottomNavigation.addItem(submitItem);
        bottomNavigation.addItem(settingsItem);

        if(Build.VERSION.SDK_INT < 23) {
            //set background color
            bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimary));
            // Change colors
            bottomNavigation.setAccentColor(getResources().getColor(R.color.white));
            bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorPrimaryMediumLight));
        }
        else {
            //set background color
            bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            // Change colors
            bottomNavigation.setAccentColor(getResources().getColor(R.color.white, null));
            bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorPrimaryMediumLight, null));
        }

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        // Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(false);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch(position) {
                    case 0:
                        hideAllToolBarButtons();
                        bottomNavigation.restoreBottomNavigation(true);
                        FragmentHelper.setFragment(MainActivity.this, LessonListFragment.newInstance(), R.id.fragmentContainer);
                        break;
                    case 1:
                        break;
                    case 2:
                        hideAllToolBarButtons();
                        bottomNavigation.restoreBottomNavigation(true);
                        FragmentHelper.setFragment(MainActivity.this, StudentListFragment.newInstance(), R.id.fragmentContainer);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default: break;
                }

                return true;
            }
        });
    }

    private void hideAllToolBarButtons() {
        editMenuItem.setVisible(false);
        saveMenuItem.setVisible(false);
        deleteMenuItem.setVisible(false);
        cancelMenuItem.setVisible(false);
    }

    private void showToolBarButtons() {
        editMenuItem.setVisible(true);
        deleteMenuItem.setVisible(true);
        cancelMenuItem.setVisible(true);
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
