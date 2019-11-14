package com.warehousemanager.ui.admin.user;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import java.util.HashMap;

public class UserDetailFragment extends Fragment
    implements View.OnClickListener {

    private IWarehouseService warehouseService =
      WarehouseService.getInstance().create(IWarehouseService.class);

    private FragmentManagerHelper fragmentManagerHelper;

    private EditText editAnswer;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editName;

    private Spinner spinnerRoles;
    private Spinner spinnerQuestions;

    private ArrayAdapter<String> rolesAdapter;
    private ArrayAdapter<String> questionsAdapter;

    private Button btnEditUser;
    private Button btnRemoveUser;

    private User user;

    public UserDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if(getArguments().getSerializable("USER") != null) {
                this.user = (User) getArguments().getSerializable("USER");
            }
        } catch (NullPointerException ex) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_users_detail, container, false);

        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.usersFragmentContainer);

        btnEditUser = view.findViewById(R.id.btnEditUser);
        btnEditUser.setOnClickListener(this);
        btnRemoveUser = view.findViewById(R.id.btnRemoveUser);
        btnRemoveUser.setOnClickListener(this);

        editAnswer = view.findViewById(R.id.editAnswer);
        editUsername = view.findViewById(R.id.editUsername);
        editName = view.findViewById(R.id.editName);
        editPassword = view.findViewById(R.id.editPassword);

        spinnerRoles = view.findViewById(R.id.spinnerRoles);
        rolesAdapter = new ArrayAdapter<String>(
          getContext(),
          R.layout.spinner_item,
          getResources().getStringArray(R.array.roles_entries));
        spinnerRoles.setAdapter(rolesAdapter);

        spinnerQuestions  = view.findViewById(R.id.spinnerQuestions);
        questionsAdapter = new ArrayAdapter<String>(
          getContext(),
          R.layout.spinner_item,
          getResources().getStringArray(R.array.questions_entries));
        spinnerQuestions.setAdapter(questionsAdapter);

        setUserFields();

        return view;
    }

    private void setUserFields() {
        editUsername.setText(user.getUsername());
        editName.setText(user.getName());
        editAnswer.setText(user.getAnswer());

        String[] roles = getResources().getStringArray(R.array.roles_entries);
        int index = 0;
        for (int i = 0; i < roles.length; i++) {
            if(roles[i].toLowerCase().equals(user.getRole().toLowerCase())) {
                index = i;
                break;
            }
        }
        spinnerRoles.setSelection(index);
        String[] questions = getResources().getStringArray(R.array.questions_entries);
        index = 0;
        for (int i = 0; i < questions.length; i++) {
            if(questions[i].toLowerCase().equals(user.getQuestion().toLowerCase())) {
                index = i;
                break;
            }
        }
        spinnerQuestions.setSelection(index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditUser:
                onBtnEditUserClicked(v);
                break;
            case R.id.btnRemoveUser:
                onBtnRemoveUserClicked(v);
                break;
        }
    }

    private void onBtnRemoveUserClicked(View v) {
    }

    private void onBtnEditUserClicked(View v) {
    }
}
