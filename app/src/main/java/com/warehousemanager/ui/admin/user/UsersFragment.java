package com.warehousemanager.ui.admin.user;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.internal.JsonReader;
import com.warehousemanager.data.internal.model.UserRow;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

public class UsersFragment extends Fragment implements FragmentInteraction {

  private FragmentInteraction mListener;

  private Toolbar toolbar;

  private IFragmentManagerHelper fragmentManagerHelper;

  public UsersFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_admin_users, container, false);

    fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.usersFragmentContainer);
    fragmentManagerHelper.attach(UsersFragmentList.class);

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
