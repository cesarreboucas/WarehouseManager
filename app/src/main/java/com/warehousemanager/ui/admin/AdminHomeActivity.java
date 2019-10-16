package com.warehousemanager.ui.admin;

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
import dagger.android.support.DaggerAppCompatActivity;

public class AdminHomeActivity extends DaggerAppCompatActivity
implements UsersFragment.OnFragmentInteractionListener{

  @Inject
  WarehouseDatabase warehouseDatabase;

  BottomNavigationView bottomNavigationView;

  FragmentManager fragmentManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

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
          break;
        case R.id.userMenu:
          UsersFragment usersFragment = new UsersFragment();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction.add(R.id.fragmentContainer, usersFragment);
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
          break;
        case R.id.productMenu:
          break;
        case R.id.warehouseMenu:
          break;
      }
      return true;
    }
  };

  @Override
  public void onFragmentInteraction(Uri uri) {

  }
}
