package com.warehousemanager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.services.FirebaseService;
import com.warehousemanager.data.services.FirebaseUserCallback;

public class SignUpActivity extends AppCompatActivity implements FirebaseUserCallback {

  FirebaseService firebaseService;

  EditText editPassword;
  EditText editUsername;
  EditText editName;
  Spinner spinnerQuestions;
  EditText editAnswer;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    firebaseService = new FirebaseService();
    firebaseService.setUserCallback(this);

    Toolbar toolbar = findViewById(R.id.signUpToolBar);

    editName = findViewById(R.id.editName);
    editUsername = findViewById(R.id.editUsername);
    editPassword = findViewById(R.id.editPassword);

    spinnerQuestions = findViewById(R.id.spinnerRoles);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            R.layout.spinner_item,
            getResources().getStringArray(R.array.questions_entries));
    spinnerQuestions.setAdapter(adapter);

    editAnswer = findViewById(R.id.editAnswer);

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent it = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(it);
      }
    });

  }

  public void onSignUp(View view) {
    String username = editUsername.getText().toString();
    String password = editPassword.getText().toString();
    String name = editName.getText().toString();
    String question = spinnerQuestions.getSelectedItem().toString();
    String answer = editAnswer.getText().toString();

    //TODO handle unassigned and noImage
    User user = new User(name, username, password, "client", "unAssigned", question, answer, "NoImage");
    firebaseService.addUser(user);
  }

  @Override
  public void onUserAddSuccess() {
    Toast.makeText(this, "User added successfully!", Toast.LENGTH_LONG).show();
  }

  @Override
  public void onUserAddFail(Exception ex) {
    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onUserFetchComplete(User user) {

  }

  @Override
  public void onUserFetchFail(Exception ex) {

  }
}
