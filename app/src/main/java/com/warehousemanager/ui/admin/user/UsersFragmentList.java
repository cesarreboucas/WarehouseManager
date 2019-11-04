package com.warehousemanager.ui.admin.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.internal.JsonReader;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragmentList extends Fragment
  implements View.OnClickListener,  SwipeRefreshLayout.OnRefreshListener, FragmentInteraction {

  IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);
  List<User> users;

  private RecyclerView usersList;
  private UsersListAdapter usersListAdapter;
  private FloatingActionButton floatingActionButton;
  private SwipeRefreshLayout swipeRefreshLayout;

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
    usersList = view.findViewById(R.id.usersList);

    fragmentManagerHelper = new FragmentManagerHelper(
      getFragmentManager(), R.id.usersFragmentContainer);

    floatingActionButton = view.findViewById(R.id.floatingActionButton);
    floatingActionButton.setOnClickListener(this);
    swipeRefreshLayout = view.findViewById(R.id.usersListRefresh);
    swipeRefreshLayout.setOnRefreshListener(this);

    users = new ArrayList<>();
    usersList.setLayoutManager(new LinearLayoutManager(getContext()));
    usersListAdapter = new UsersListAdapter(users, this);
    usersList.setAdapter(usersListAdapter);
    usersList.setItemAnimator(new DefaultItemAnimator());

    getData();

    return view;
  }

  private void getData() {
    warehouseService.getUsers().enqueue(new Callback<List<User>>() {
      @Override
      public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if(response.body() != null) {
          users.clear();
          users.addAll(response.body());
          usersListAdapter.notifyDataSetChanged();
          swipeRefreshLayout.setRefreshing(false);
        }
      }

      @Override
      public void onFailure(Call<List<User>> call, Throwable t) {
        Toast.makeText(getContext(), "Failed to reach the server", Toast.LENGTH_LONG).show();
        Log.d("ERROR", t.getMessage());
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d("USERS", "RESUME");
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

  @Override
  public void onRefresh() {
    getData();
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if(!hidden) {
      onRefresh();
    }
  }

  @Override
  public void sendMessage(Message message) {
    User user = (User) message.obj;
    Bundle bundle = new Bundle();
    bundle.putSerializable("USER", user);
    fragmentManagerHelper.attach(AddUserFragment.class, bundle);
  }
}
