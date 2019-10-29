package com.warehousemanager.ui.admin.summary;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.FragmentInteraction;

public class SummariesFragment extends Fragment implements FragmentInteraction {

  IFragmentManagerHelper fragmentManagerHelper;

  public SummariesFragment() { }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.summariesFragmentContainer);
    View view = inflater.inflate(R.layout.fragment_admin_summaries, container, false);

    fragmentManagerHelper.attach(SummariesFragmentList.class);
    return view;
  }

  @Override
  public void sendMessage(Message message) {

  }
}
