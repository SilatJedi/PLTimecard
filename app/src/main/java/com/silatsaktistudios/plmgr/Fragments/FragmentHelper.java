package com.silatsaktistudios.plmgr.Fragments;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

/**
 * Created by SilatJedi on 4/13/17.
 */

public class FragmentHelper {

    /*
    /Example: FragmentHelper.setFragment(MainActivity.this, DemoFragment.newInstance(), R.id.fragment_container);
    */
    public static void setFragment(FragmentActivity activity, Fragment newFragment, int layoutId) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
          ft.replace(layoutId, newFragment)
            .addToBackStack(null)
            .commit();
    }
}
