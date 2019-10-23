package com.warehousemanager.ui.admin.user;

import android.content.Context;
import android.net.Uri;
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

public class UsersFragmentList extends Fragment implements View.OnClickListener {

  private RecyclerView usersList;
  private FloatingActionButton floatingActionButton;

  JsonReader jsonReader;
  IFragmentManagerHelper fragmentManagerHelper;

  public UsersFragmentList() { }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_admin_users_fragment_list, container, false);
    jsonReader = new JsonReader(getContext());
    usersList = view.findViewById(R.id.usersList);

    fragmentManagerHelper = new FragmentManagerHelper(
      getFragmentManager(), R.id.usersFragmentContainer);

    floatingActionButton = view.findViewById(R.id.floatingActionButton);
    floatingActionButton.setOnClickListener(this);

    usersList.setLayoutManager(new LinearLayoutManager(getActivity()));
    List<UserRow> userRows = jsonReader.getUserRows();
    UsersListAdapter usersListAdapter = new UsersListAdapter(userRows);
    usersList.setAdapter(usersListAdapter);
    usersList.setItemAnimator(new DefaultItemAnimator());

    return view;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.floatingActionButton:
        onFloatingActionButtonClicked(v);
        break;
    }
  }

  private void onFloatingActionButtonClicked(View v) {
    fragmentManagerHelper.attach(AddUserFragment.class);
  }
}
