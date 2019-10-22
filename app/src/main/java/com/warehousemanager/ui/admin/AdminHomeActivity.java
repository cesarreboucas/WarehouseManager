package com.warehousemanager.ui.admin;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.UserEntity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public class AdminHomeActivity extends DaggerAppCompatActivity
  implements FragmentInteraction, BottomNavigationView.OnNavigationItemSelectedListener{

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
    bottomNavigationView.setOnNavigationItemSelectedListener(this);

    UserEntity user = warehouseDatabase.userDao().getUser();
    Log.i("USER", user.toString());
  }

  @Override
  public void sendMessage(Message message) {

  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      case R.id.summaryMenu:
        break;
      case R.id.userMenu:
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(UsersFragment.class.getName());
        if(fragment == null) {
          Toast.makeText(this, "ADDED FRAGMENT", Toast.LENGTH_LONG).show();
          UsersFragment usersFragment = new UsersFragment();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction.add(R.id.fragmentContainer, usersFragment, UsersFragment.class.getName());
          fragmentTransaction.addToBackStack(null);
          fragmentTransaction.commit();
        }
        break;
      case R.id.productMenu:
        break;
      case R.id.warehouseMenu:
        break;
    }
    return true;
  }
}
