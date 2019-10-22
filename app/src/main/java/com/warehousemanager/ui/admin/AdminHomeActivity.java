package com.warehousemanager.ui.admin;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.internal.FragmentManagerHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity
  implements FragmentInteraction, BottomNavigationView.OnNavigationItemSelectedListener{

  WarehouseDatabase warehouseDatabase;
  FragmentManagerHelper fragmentManagerHelper;

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    // Dependencies
    warehouseDatabase = WarehouseDatabase.getAppDatabase(getApplicationContext());
    fragmentManagerHelper =
            new FragmentManagerHelper(getSupportFragmentManager(), R.id.fragmentContainer);

    bottomNavigationView = findViewById(R.id.bottomNavigationView);
    bottomNavigationView.setOnNavigationItemSelectedListener(this);
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
        fragmentManagerHelper.attachFragment(UsersFragment.class);
        break;
      case R.id.productMenu:
        fragmentManagerHelper.attachFragment(ProductsFragment.class);
        break;
      case R.id.warehouseMenu:
        break;
    }
    return true;
  }

}
