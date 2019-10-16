package com.warehousemanager.ui.admin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.JsonReader;
import com.warehousemanager.data.internal.model.UserRow;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerFragment;

public class UsersFragment extends DaggerFragment
  implements FragmentInteraction, View.OnClickListener {

    private FragmentInteraction mListener;

    private RecyclerView usersList;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    @Inject
    JsonReader jsonReader;

    public UsersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_users, container, false);
        usersList = view.findViewById(R.id.usersList);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getChildFragmentManager().getFragments().size() == 1) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
                getChildFragmentManager().popBackStack();
            }
        });
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);

        usersList.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<UserRow> userRows = jsonReader.getUserRows();
        UsersListAdapter usersListAdapter = new UsersListAdapter(userRows);
        usersList.setAdapter(usersListAdapter);
        usersList.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Message message) {
        if (mListener != null) {
            mListener.sendMessage(message);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteraction) {
            mListener = (FragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void sendMessage(Message message) {

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
        v.setVisibility(View.GONE);
        AddUserFragment addUserFragment = new AddUserFragment();
        getChildFragmentManager()
          .beginTransaction()
          .addToBackStack(null)
          .add(R.id.usersFragmentContainer, addUserFragment)
          .commit();
    }
}
