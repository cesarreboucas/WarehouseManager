package com.warehousemanager.ui.admin.user;

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

public class UserFragment extends Fragment implements FragmentInteraction {

  private FragmentInteraction mListener;

  private Toolbar toolbar;

  private IFragmentManagerHelper fragmentManagerHelper;

  public UserFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_admin_users, container, false);

    fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.usersFragmentContainer);
    fragmentManagerHelper.attach(UserFragmentList.class);

    toolbar = view.findViewById(R.id.toolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragmentManagerHelper.goBack();
      }
    });


    return view;
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void sendMessage(Message message) {
  }
}
