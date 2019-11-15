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
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.data.services.FirebaseService;
import com.warehousemanager.data.services.FirebaseUserCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity{

  IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

  EditText editPassword;
  EditText editUsername;
  EditText editName;
  Spinner spinnerQuestions;
  EditText editAnswer;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    Toolbar toolbar = findViewById(R.id.signUpToolBar);

    editName = findViewById(R.id.editName);
    editUsername = findViewById(R.id.editUsername);
    editPassword = findViewById(R.id.editPassword);

    spinnerQuestions = findViewById(R.id.spinnerRoles);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            R.layout.spinner_item_white,
            getResources().getStringArray(R.array.questions_entries));
    spinnerQuestions.setAdapter(adapter);

    editAnswer = findViewById(R.id.editAnswer);

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
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
    warehouseService.createUser(user).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if(response.code() == 200) {
          Toast.makeText(SignUpActivity.this, "USER CREATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(SignUpActivity.this, "Your credentials are wrong", Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(SignUpActivity.this, "The server is down", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
