package com.warehousemanager.data.internal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.lang.reflect.Constructor;

public class BottomNavigatorManager implements IFragmentManagerHelper {

  private FragmentManager fragmentManager;
  private int fragmentContainer;

  public BottomNavigatorManager(FragmentManager fragmentManager, int fragmentContainer) {
    this.fragmentManager = fragmentManager;
    this.fragmentContainer = fragmentContainer;
  }

  @Override
  public void attach(Class<? extends Fragment> fragment) {
    try {
      detachFragments();
      Constructor<? extends Fragment> constructor = fragment.getConstructor();
      Fragment newFragment = constructor.newInstance();
      Fragment searchFragment = fragmentManager.findFragmentByTag(fragment.getName());
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      if(searchFragment == null) {
        fragmentTransaction.add(fragmentContainer, newFragment, fragment.getName());
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
    fragmentManager.popBackStack();
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