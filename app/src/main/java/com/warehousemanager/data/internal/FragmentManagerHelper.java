package com.warehousemanager.data.internal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.List;

public class FragmentManagerHelper implements IFragmentManagerHelper{

    private FragmentManager fragmentManager;
    private int fragmentContainer;

    public FragmentManagerHelper(FragmentManager fragmentManager, int fragmentContainer) {
        this.fragmentManager = fragmentManager;
        this.fragmentContainer = fragmentContainer;
    }

    @Override
    public void attach(Class<? extends Fragment> newClass) {
        try {
            detachFragments();
            Constructor<? extends Fragment> constructor = newClass.getConstructor();
            Fragment fragment = constructor.newInstance();
            Fragment searchFragment = fragmentManager.findFragmentByTag(newClass.getName());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(searchFragment == null) {
                fragmentTransaction.add(fragmentContainer, fragment, newClass.getName());
            } else {
                fragmentTransaction.show(searchFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception ex) {
            Log.d("EXCEPTION", ex.getMessage());
        }
    }

    @Override
    public void attach(Class<? extends Fragment> newClass, Bundle bundle) {
        try {
            detachFragments();
            Constructor<? extends Fragment> constructor = newClass.getConstructor();
            Fragment fragment = constructor.newInstance();
            Fragment searchFragment = fragmentManager.findFragmentByTag(newClass.getName());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(searchFragment == null) {
                fragmentTransaction.add(fragmentContainer, fragment, newClass.getName());
                fragment.setArguments(bundle);
            } else {
                fragmentTransaction.show(searchFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception ex) {
            Log.d("EXCEPTION", ex.getMessage());
        }
    }

    @Override
    public boolean goBack() {
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments.size() <= 1) return false;
        Fragment lastFragment = fragments.get(fragments.size() - 1);
        Fragment penultimateFragment = fragments.get(fragments.size() - 2);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(lastFragment);
        fragmentTransaction.show(penultimateFragment);
        fragmentTransaction.commit();
        return true;
    }

    private void detachFragments() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if(fragment.isVisible()) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commit();
    }
}
