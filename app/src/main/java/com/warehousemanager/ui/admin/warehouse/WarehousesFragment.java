package com.warehousemanager.ui.admin.warehouse;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.FragmentInteraction;

public class WarehousesFragment extends Fragment implements FragmentInteraction {

  IFragmentManagerHelper fragmentManagerHelper;

  Toolbar toolbar;

  public WarehousesFragment() { }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.warehousesFragmentContainer);
    View view = inflater.inflate(R.layout.fragment_admin_warehouses, container, false);

    toolbar = view.findViewById(R.id.toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragmentManagerHelper.goBack();
      }
    });
    fragmentManagerHelper.attach(WarehousesFragmentList.class);
    return view;
  }

  @Override
  public void sendMessage(Message message) {
  }
}
