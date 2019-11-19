package com.warehousemanager.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

  IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

  EditText editPassword;
  EditText editRepeatPassword;
  EditText editUsername;
  Spinner spinnerQuestions;
  EditText editAnswer;

  ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);

    editUsername = findViewById(R.id.editUsername);
    editPassword = findViewById(R.id.editPassword);
    editRepeatPassword = findViewById(R.id.editRepeatPassword);

    progressBar = new ProgressBar(this);

    spinnerQuestions = findViewById(R.id.spinnerRoles);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
      R.layout.spinner_item_white,
      getResources().getStringArray(R.array.questions_entries));
    spinnerQuestions.setAdapter(adapter);

    editAnswer = findViewById(R.id.editAnswer);

    Toolbar toolbar = findViewById(R.id.forgotPasswordToolBar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  public void onResetPassword(View view) {
    String username = editUsername.getText().toString();
    String password = editPassword.getText().toString();
    String repeatPassword = editRepeatPassword.getText().toString();
    String question = spinnerQuestions.getSelectedItem().toString();
    String answer = editAnswer.getText().toString();

    if(!password.equals(repeatPassword)) {
      Toast.makeText(this, "The passwords doesn't match", Toast.LENGTH_SHORT).show();
    }

    User user = new User("", username, password, "client", "", question, answer, "");
    progressBar.setVisibility(View.VISIBLE);
    warehouseService.editUserPassword(user).enqueue(new Callback() {
      @Override
      public void onResponse(Call call, Response response) {
        if(response.isSuccessful()) {
          Snackbar.make(
            findViewById(android.R.id.content),
            "User password changed successfully!",
            Snackbar.LENGTH_LONG
          ).show();
        } else {
          Toast.makeText(ForgotPasswordActivity.this, response.message(), Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onFailure(Call call, Throwable t) {
        Toast.makeText(ForgotPasswordActivity.this, "Failed to contact the server", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
      }
    });
  }
}
