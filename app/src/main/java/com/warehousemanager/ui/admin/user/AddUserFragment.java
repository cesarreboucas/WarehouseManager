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
import com.warehousemanager.data.db.entities.User;

import java.lang.reflect.Array;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddUserFragment extends Fragment implements View.OnClickListener {

  CircleImageView imageView;

  EditText editAnswer;
  EditText editUsername;
  EditText editName;

  Spinner spinnerRoles;
  Spinner spinnerQuestions;

  ArrayAdapter<String> rolesAdapter;
  ArrayAdapter<String> questionsAdapter;

  Button btnCreateUser;

  private User user;

  public AddUserFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.user = (User) getArguments().getSerializable("USER");
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

    if(user != null) {
      editUsername.setText(user.getUsername());
      editName.setText(user.getName());

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

      editAnswer.setText(user.getAnswer());
      btnCreateUser.setText(getContext().getString(R.string.btnEditUser));
    }

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
