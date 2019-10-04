package com.warehousemanager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.warehousemanager.R;

public class SignUpActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    Toolbar toolbar = findViewById(R.id.signUpToolBar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent it = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(it);
      }
    });

  }

  public void onSignUp(View view) {
    // TODO Create sign up logic using firebase
  }
}
