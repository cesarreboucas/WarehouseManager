package com.warehousemanager.data.internal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.warehousemanager.ui.admin.AddUserFragment;

import java.lang.reflect.Constructor;

public class FragmentManagerHelper {

    private FragmentManager fragmentManager;
    private int fragmentContainer;

    public FragmentManagerHelper(FragmentManager fragmentManager, int fragmentContainer) {
        this.fragmentManager = fragmentManager;
        this.fragmentContainer = fragmentContainer;
    }

    public void attachFragment(Class<? extends Fragment> newClass) {
        try {
            detachFragments();
            Constructor<? extends Fragment> constructor = newClass.getConstructor();
            Fragment fragment = constructor.newInstance();
            Fragment searchFragment = fragmentManager.findFragmentByTag(newClass.getName());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(searchFragment == null) {
                fragmentTransaction.add(fragmentContainer, fragment, newClass.getName());
            } else {
                fragmentTransaction.attach(searchFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception ex) {
            Log.d("EXCEPTION", ex.getMessage());
        }
    }

    private void detachFragments() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if(fragment.isVisible()) {
                fragmentTransaction.detach(fragment);
            }
        }
        fragmentTransaction.commit();
    }

}
