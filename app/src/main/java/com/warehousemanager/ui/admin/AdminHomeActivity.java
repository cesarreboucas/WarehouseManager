package com.warehousemanager.ui.admin;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.BottomNavigatorManager;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.product.MoveProductsFragment;
import com.warehousemanager.ui.admin.product.ProductsFragment;
import com.warehousemanager.ui.admin.report.ReportsFragment;
import com.warehousemanager.ui.admin.summary.SummariesFragment;
import com.warehousemanager.ui.admin.user.UserFragment;
import com.warehousemanager.ui.admin.warehouse.WarehousesFragment;

import java.util.List;

public class AdminHomeActivity extends AppCompatActivity
  implements FragmentInteraction, BottomNavigationView.OnNavigationItemSelectedListener{

  WarehouseDatabase warehouseDatabase;
  IFragmentManagerHelper fragmentManagerHelper;

  BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_home);

    // Dependencies
    warehouseDatabase = WarehouseDatabase.getAppDatabase(getApplicationContext());
    fragmentManagerHelper =
            new BottomNavigatorManager(getSupportFragmentManager(), R.id.fragmentContainer);
    fragmentManagerHelper.attach(UserFragment.class);

    bottomNavigationView = findViewById(R.id.bottomNavigationView);
    bottomNavigationView.setOnNavigationItemSelectedListener(this);
  }

  @Override
  public void sendMessage(Message message) {
      switch (message.what) {
        case 2:
          Log.d("TESTE", "MESSAGE ACTIVITY");
          Bundle bundle = new Bundle();
          bundle.putSerializable("product", (Product) message.obj);
          bundle.putString("summary", "showmovs");
          fragmentManagerHelper.attach(ProductsFragment.class, bundle);
          List<Fragment> fragments = getSupportFragmentManager().getFragments();
          bottomNavigationView.setSelectedItemId(R.id.productMenu);
          String oi = "";
          //Fragment fragment  = getSupportFragmentManager().findFragmentByTag(ProductsFragment.class.getName());
          break;
      }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    switch (menuItem.getItemId()) {
      case R.id.summaryMenu:
        fragmentManagerHelper.attach(SummariesFragment.class);
        break;
      case R.id.userMenu:
        fragmentManagerHelper.attach(UserFragment.class);
        break;
      case R.id.productMenu:
        fragmentManagerHelper.attach(ProductsFragment.class);
        break;
      case R.id.warehouseMenu:
        fragmentManagerHelper.attach(WarehousesFragment.class);
        break;
      case R.id.reportsMenu:
        fragmentManagerHelper.attach(ReportsFragment.class);
        break;
    }
    return true;
  }

}
