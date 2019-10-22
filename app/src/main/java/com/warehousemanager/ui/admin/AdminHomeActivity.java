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

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.User;

import java.util.List;

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

    User user = warehouseDatabase.userDao().getUser();
    Log.i("USER", user.toString());

    UsersFragment usersFragment = new UsersFragment();
    ProductsFragment productsFragment = new ProductsFragment();
    getSupportFragmentManager().beginTransaction()
            .add(R.id.fragmentContainer, usersFragment, UsersFragment.class.getName())
            .detach(usersFragment)
            .add(R.id.fragmentContainer, productsFragment, ProductsFragment.class.getName())
            .detach(productsFragment)
            .commit();
  }

  @Override
  public void sendMessage(Message message) {

  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    detachAllFragments(getSupportFragmentManager());
    Fragment fragment = new Fragment();
    FragmentTransaction fragmentTransactionAttach = fragmentManager.beginTransaction();

    switch (menuItem.getItemId()) {
      case R.id.summaryMenu:
        break;
      case R.id.userMenu:
          fragment = getSupportFragmentManager().findFragmentByTag(UsersFragment.class.getName());
        break;
      case R.id.productMenu:
          fragment = getSupportFragmentManager().findFragmentByTag(ProductsFragment.class.getName());
        break;
      case R.id.warehouseMenu:
        break;
    }
    fragmentTransactionAttach.attach(fragment);
    fragmentTransactionAttach.commit();
    return true;
  }

  public void detachAllFragments(FragmentManager fragmentManager) {
    //TODO move to a static class
    List<Fragment> fragments = fragmentManager.getFragments();
    for(Fragment f: fragments) {
      if(f.isVisible()) {
        Log.d("Fragments", f.getTag() + " Detached");
        fragmentManager.beginTransaction().detach(f).commit();
      }
    }
  }

}
