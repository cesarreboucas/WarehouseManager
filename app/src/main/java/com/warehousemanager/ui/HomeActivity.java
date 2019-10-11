package com.warehousemanager.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.UserEntity;
import com.warehousemanager.ui.admin.UserDetail;
import com.warehousemanager.ui.admin.UsersFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class HomeActivity extends AppCompatActivity
implements UsersFragment.OnFragmentInteractionListener,
 UserDetail.OnFragmentInteractionListener{

  @Inject
  WarehouseDatabase warehouseDatabase;

  BottomNavigationView bottomNavigationView;
  Toolbar toolbar;

  FragmentManager fragmentManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    toolbar = findViewById(R.id.homeToolbar);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragmentManager.popBackStack();
      }
    });

    fragmentManager = getSupportFragmentManager();

    bottomNavigationView = findViewById(R.id.bottomNavigationView);
    bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

    UserEntity user = warehouseDatabase.userDao().getUser();
    Log.i("USER", user.toString());
  }

  public BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
          = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
      switch (menuItem.getItemId()) {
        case R.id.homeMenu:
          toolbar.setTitle("Home");
          break;
        case R.id.userMenu:
          toolbar.setTitle("Users");
          UsersFragment usersFragment = new UsersFragment();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction.add(R.id.fragmentContainer, usersFragment);
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
          break;
        case R.id.productMenu:
          toolbar.setTitle("Products");
          break;
        case R.id.warehouseMenu:
          toolbar.setTitle("Warehouses");
          break;
      }
      return true;
    }
  };

  @Override
  public void onFragmentInteraction(Uri uri) {

  }
}
