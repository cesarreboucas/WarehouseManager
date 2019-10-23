package com.warehousemanager.data.internal;

import android.support.v4.app.Fragment;

public interface IFragmentManagerHelper {
  void attach(Class<? extends Fragment> fragment);
  boolean goBack();
}
