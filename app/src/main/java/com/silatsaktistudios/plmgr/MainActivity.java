package com.silatsaktistudios.plmgr;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.silatsaktistudios.plmgr.data.Demo;
import com.silatsaktistudios.plmgr.data.LessonData;
import com.silatsaktistudios.plmgr.fragment.LessonListFragment;
import com.silatsaktistudios.plmgr.fragment.Transaction;
import com.silatsaktistudios.plmgr.fragment.LessonDetailFragment;
import com.silatsaktistudios.plmgr.fragment.StudentDetailFragment;
import com.silatsaktistudios.plmgr.fragment.StudentListFragment;

public class MainActivity extends AppCompatActivity implements
        LessonListFragment.OnFragmentInteractionListener,
        StudentListFragment.OnFragmentInteractionListener {

    private BottomNavigationView bottomNavigation;
    MenuItem editMenuItem, saveMenuItem, deleteMenuItem, cancelMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = findViewById(R.id.plmgrToolbar);
        setSupportActionBar(toolBar);

        setUpNavBar();
        Demo.createDemoData();

        if (savedInstanceState == null) {
            Transaction.setFragment(MainActivity.this, LessonListFragment.newInstance(), R.id.fragmentContainer);
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
        switch (item.getItemId()) {
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        cancelMenuItemFunction();
    }


    //toolbar menu item functions
    private void editMenuItemFunctions() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        editMenuItem.setVisible(false);
        saveMenuItem.setVisible(true);

        if (f instanceof LessonDetailFragment) {
            ((LessonDetailFragment) f).editLesson();
        }

    }

    private void saveMenuItemFunctions() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        editMenuItem.setVisible(true);
        saveMenuItem.setVisible(false);

        if (f instanceof LessonDetailFragment) {
            ((LessonDetailFragment) f).saveLesson();
        }
    }

    private void cancelMenuItemFunction() {
        //bring up the most recently called fragment
        bottomNavigation.setSelectedItemId(bottomNavigation.getSelectedItemId());
    }

    private void deleteMenuItemFunction() {
        new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("Delete Item?")
                .setMessage("Are you sure that you want to do this? Please note that this cannot be undone.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

                        if (f instanceof LessonDetailFragment) {
                            LessonData.delete(((LessonDetailFragment) f).lessonID);
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
        bottomNavigation.setVisibility(View.GONE);
        Transaction.setFragment(MainActivity.this, LessonDetailFragment.newInstance(lessonId), R.id.fragmentContainer);
    }

    @Override
    public void onAddLessonButtonClick() {
        bottomNavigation.setVisibility(View.GONE);
        cancelMenuItem.setVisible(true);
        Transaction.setFragment(MainActivity.this, LessonDetailFragment.newInstance(), R.id.fragmentContainer);
    }

    //student list fragment events
    @Override
    public void onStudentListItemClick(int studentId) {
        showToolBarButtons();
        bottomNavigation.setVisibility(View.GONE);
        Transaction.setFragment(MainActivity.this, StudentDetailFragment.newInstance(studentId), R.id.fragmentContainer);
    }

    @Override
    public void onAddStudentButtonClick() {

    }


    private void setUpNavBar() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.timeCardItem:
                        hideAllToolBarButtons();
                        bottomNavigation.setVisibility(View.VISIBLE);
                        Transaction.setFragment(MainActivity.this, LessonListFragment.newInstance(), R.id.fragmentContainer);
                        return true;
                    case R.id.lessonNavItem:
                        Transaction.clearFragment(MainActivity.this, R.id.fragmentContainer);
                        return true;
                    case R.id.studentNavItem:
                        hideAllToolBarButtons();
                        bottomNavigation.setVisibility(View.VISIBLE);
                        Transaction.setFragment(MainActivity.this, StudentListFragment.newInstance(), R.id.fragmentContainer);
                        return true;
                    case R.id.submitItem:
                        Transaction.clearFragment(MainActivity.this, R.id.fragmentContainer);
                        return true;
                    case R.id.settingsItem:
                        Transaction.clearFragment(MainActivity.this, R.id.fragmentContainer);
                        return true;
                    default:
                        return false;
                }
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

}
