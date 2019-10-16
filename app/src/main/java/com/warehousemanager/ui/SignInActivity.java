package com.warehousemanager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.UserEntity;
import com.warehousemanager.data.services.FirebaseService;
import com.warehousemanager.data.services.FirebaseUserCallback;
import com.warehousemanager.ui.admin.AdminHomeActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SignInActivity extends AppCompatActivity implements FirebaseUserCallback {

    @Inject
    WarehouseDatabase warehouseDatabase;

    @Inject
    FirebaseService firebaseService;

    String username, password;

    EditText editPassword, editUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        firebaseService.setUserCallback(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);

        // REMOVE LATER
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsername.setText("teste@teste.com");
                editPassword.setText("teste");
            }
        });

    }

    public void onSignUp(View view) {
        Intent it = new Intent(this, SignUpActivity.class);
        startActivity(it);
    }

    public void onSignIn(View view) {
        username = editUsername.getText().toString();
        password = editPassword.getText().toString();
        firebaseService.getUser(username);
    }

    public void onForgotPassword(View view) {
        Intent it = new Intent(this, ForgotPasswordActivity.class);
        startActivity(it);
    }


    @Override
    public void onUserAddSuccess() {

    }

    @Override
    public void onUserAddFail(Exception ex) {

    }

    @Override
    public void onUserFetchComplete(UserEntity userEntity) {
        if(userEntity == null) {
            Toast.makeText(this, "This user dont exist", Toast.LENGTH_SHORT).show();
        } else {
            if(userEntity.getPassword().equals(password)) {
                Toast.makeText(this, "Logged successfully", Toast.LENGTH_SHORT).show();
                warehouseDatabase.userDao().insertUser(userEntity);
                Intent it = new Intent(this, AdminHomeActivity.class);
                startActivity(it);
            }
        }
    }

    @Override
    public void onUserFetchFail(Exception ex) {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
