package com.silatsaktistudios.plmgr;

import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.silatsaktistudios.plmgr.Fragments.LessonDetailFragment;
import com.silatsaktistudios.plmgr.Fragments.LessonListFragment;
import com.silatsaktistudios.plmgr.Models.Lesson;

import java.util.Date;


public class MainActivity extends AppCompatActivity implements LessonDetailFragment.OnFragmentInteractionListener{

    Toolbar toolBar;
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.plmgrToolbar);
        setSupportActionBar(toolBar);


        fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);
        setUpNavBar();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

        Lesson testLesson = new Lesson(41, "Luke Skywalker", new Date(), 4f, "Best lesson ever!", true, false, false);

        if(fragment == null) {
            fragment = LessonDetailFragment.newInstance(testLesson);
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onFragmentInteraction(Uri uri) {

    }
}
