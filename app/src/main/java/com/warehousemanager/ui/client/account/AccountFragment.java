package com.warehousemanager.ui.client.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.User;

public class AccountFragment extends Fragment {

    private EditText name;
    private EditText email;
    private WarehouseDatabase warehouseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_account, container, false);
        warehouseDatabase  = WarehouseDatabase.getAppDatabase(getActivity().getApplicationContext());

        User user = warehouseDatabase.userDao().getUser();
        name = view.findViewById(R.id.editTextClientName);
        name.setText(user.getName());
        email = view.findViewById(R.id.editTextClientUsername);
        email.setText(user.getUsername());

        return view;
    }

}
