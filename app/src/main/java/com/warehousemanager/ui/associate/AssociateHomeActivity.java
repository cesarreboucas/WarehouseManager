package com.warehousemanager.ui.associate;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.internal.BottomNavigatorManager;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.admin.product.ProductsFragment;
import com.warehousemanager.ui.admin.report.ReportsFragment;
import com.warehousemanager.ui.admin.summary.SummariesFragment;
import com.warehousemanager.ui.admin.user.UserFragment;
import com.warehousemanager.ui.admin.warehouse.WarehousesFragment;
import com.warehousemanager.ui.associate.completed.CompletedFragment;
import com.warehousemanager.ui.associate.pending.PendingFragment;
import com.warehousemanager.ui.associate.todo.TodoFragment;

public class AssociateHomeActivity extends AppCompatActivity
        implements FragmentInteraction, BottomNavigationView.OnNavigationItemSelectedListener {

    WarehouseDatabase warehouseDatabase;
    IFragmentManagerHelper fragmentManagerHelper;
    User user;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_home);

        // Dependencies
        warehouseDatabase = WarehouseDatabase.getAppDatabase(getApplicationContext());
        fragmentManagerHelper =
                new BottomNavigatorManager(getSupportFragmentManager(), R.id.fragmentContainer);
        fragmentManagerHelper.attach(TodoFragment.class);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        user = (User) b.getSerializable("user");
        Log.d("DBX", user.getName());
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.userMenu:
                fragmentManagerHelper.attach(TodoFragment.class);
                break;
            case R.id.warehouseMenu:
                fragmentManagerHelper.attach(CompletedFragment.class);
                break;
            case R.id.productMenu:
                fragmentManagerHelper.attach(PendingFragment.class);
                break;
        }
        return true;
    }
}
