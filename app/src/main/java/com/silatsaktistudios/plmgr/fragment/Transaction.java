package com.silatsaktistudios.plmgr.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

/**
 * Created by SilatJedi on 4/13/17.
 */

public class Transaction {
    public static void setFragment(FragmentActivity activity, Fragment newFragment, int layoutId) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
          ft.replace(layoutId, newFragment)
            .addToBackStack(null)
            .commit();
    }

    public static void clearFragment(FragmentActivity activity, int layoutId) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(layoutId, new Fragment())
                .addToBackStack(null)
                .commit();
    }
}
