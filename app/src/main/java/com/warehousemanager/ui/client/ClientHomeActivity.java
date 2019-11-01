package com.warehousemanager.ui.client;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.BottomNavigatorManager;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.client.account.AccountFragment;

public class ClientHomeActivity extends AppCompatActivity
        implements FragmentInteraction, BottomNavigationView.OnNavigationItemSelectedListener{

    IFragmentManagerHelper fragmentManagerHelper;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        fragmentManagerHelper = new BottomNavigatorManager(getSupportFragmentManager(), R.id.fragmentContainerClient);
        fragmentManagerHelper.attach(AccountFragment.class);

        bottomNavigationView = findViewById(R.id.bottomNavigationClientView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.userClientMenu:
                fragmentManagerHelper.attach(AccountFragment.class);
                break;
            case R.id.productClientMenu:
                break;
            case R.id.orderClientMenu:
                break;
            case R.id.shoppingCartMenu:
                break;
        }
        return true;
    }

    @Override
    public void sendMessage(Message message) {

    }
}
