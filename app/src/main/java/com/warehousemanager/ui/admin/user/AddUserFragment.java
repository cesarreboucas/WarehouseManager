package com.warehousemanager.ui.admin.user;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserFragment extends Fragment implements View.OnClickListener {

  CircleImageView imageView;

  EditText editAnswer;
  EditText editUsername;
  EditText editName;

  Spinner spinnerRoles;
  Spinner spinnerQuestions;

  Button btnCreateUser;

  public AddUserFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_admin_users_add, container, false);

    btnCreateUser = view.findViewById(R.id.btnCreateUser);

    editAnswer = view.findViewById(R.id.editAnswer);
    editUsername = view.findViewById(R.id.editUsername);
    editName = view.findViewById(R.id.editName);

    spinnerRoles = view.findViewById(R.id.spinnerRoles);
    ArrayAdapter<String> rolesAdapter = new ArrayAdapter<String>(
            getContext(),
            R.layout.spinner_item,
            getResources().getStringArray(R.array.roles_entries));
    spinnerRoles.setAdapter(rolesAdapter);

    spinnerQuestions  = view.findViewById(R.id.spinnerQuestions);
    ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(
            getContext(),
            R.layout.spinner_item,
            getResources().getStringArray(R.array.questions_entries));
    spinnerQuestions.setAdapter(questionsAdapter);

    btnCreateUser.setOnClickListener(this);
    return view;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnCreateUser:
        onButtonCreateUser(v);
        break;
    }
  }

  private void onButtonCreateUser(View view) {

  }
}
