package com.warehousemanager.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.data.services.FirebaseService;
import com.warehousemanager.ui.admin.AdminHomeActivity;
import com.warehousemanager.ui.associate.AssociateHomeActivity;
import com.warehousemanager.ui.client.ClientHomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    FirebaseService firebaseService;

    WarehouseDatabase warehouseDatabase;
    IWarehouseService warehouseService =
            WarehouseService.getInstance().create(IWarehouseService.class);

    String username, password;

    EditText editPasssword, editUsername;

    TextView txtWarehouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        warehouseDatabase = WarehouseDatabase.getAppDatabase(this.getApplicationContext());

        editUsername = findViewById(R.id.editUsername);
        editPasssword = findViewById(R.id.editPassword);

        TextView txtView2 = findViewById(R.id.textView2);
        txtView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsername.setText("mgalbraethe@purevolume.com");
                editPasssword.setText("1234");
            }
        });

        txtWarehouse = findViewById(R.id.txtWarehouse);
        txtWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsername.setText("hcastagne0@foxnews.com");
                editPasssword.setText("1234");
            }
        });

        // TODO REMOVE LATER
        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUsername.setText("ww.meta@chemistry.org");
                editPasssword.setText("1234");
            }
        });

    }

    public void onSignUp(View view) {
        Intent it = new Intent(this, SignUpActivity.class);
        startActivity(it);
    }

    public void onSignIn(View view) {
        username = editUsername.getText().toString();
        password = editPasssword.getText().toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        warehouseDatabase.userDao().deleteAllUsers();
        warehouseService.authenticate(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200) {
                    if(response.body() != null) {
                        User loggedUser = response.body();
                        warehouseDatabase.userDao().insertUser(loggedUser);
                        WarehouseService.setCredentials(username, password);
                        if(loggedUser.getRole().equals("admin")) {
                            Intent it = new Intent(getBaseContext(), AdminHomeActivity.class);
                            startActivity(it);
                        } else if(loggedUser.getRole().equals("associate")) {
                            Intent it = new Intent(getBaseContext(), AssociateHomeActivity.class);
                            startActivity(it);
                        } else {
                            Intent it = new Intent(getBaseContext(), ClientHomeActivity.class);
                            startActivity(it);
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Username or password incorrect",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Unable to reach the server",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onForgotPassword(View view) {
        Intent it = new Intent(this, ForgotPasswordActivity.class);
        startActivity(it);
    }
}
