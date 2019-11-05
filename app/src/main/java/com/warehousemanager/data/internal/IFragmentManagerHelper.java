package com.warehousemanager.data.internal;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface IFragmentManagerHelper {
  void attach(Class<? extends Fragment> fragment);
  void attach(Class<? extends Fragment> newClass, Bundle bundle);
  boolean goBack();
}
