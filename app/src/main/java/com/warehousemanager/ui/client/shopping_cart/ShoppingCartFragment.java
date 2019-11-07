package com.warehousemanager.ui.client.shopping_cart;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.internal.What;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

public class ShoppingCartFragment extends Fragment implements FragmentInteraction{

    private FragmentInteraction mListener;
    private IFragmentManagerHelper fragmentManagerHelper;
    private WarehouseDatabase warehouseDatabase;
    TextView txtTotals;

    public ShoppingCartFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_shopping_cart, container, false);
        fragmentManagerHelper= new FragmentManagerHelper(getChildFragmentManager(), R.id.shoppingCartContainer);
        Bundle bundle = new Bundle();
        bundle.putString("test", "Testando arguments");
        fragmentManagerHelper.attach(ShoppingCartListFragment.class, bundle);
        warehouseDatabase  = WarehouseDatabase.getAppDatabase(getActivity().getApplicationContext());
        txtTotals = view.findViewById(R.id.txtTotals);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    private void updateTotals() {
        List<Product> products = warehouseDatabase.productDao().getProducts();
        double total = 0;
        for(Product p : products) {
            total += (p.getQuantity() * p.getSalePrice());
        }
        txtTotals.setText(String.format("$%.2f", total)
                + "\n" + String.format("$%.2f", total * 0.12)
                + "\n" + String.format("$%.2f", total * 1.12));
    }

    @Override
    public void sendMessage(Message message) {
        if(message.obj.toString().equals("Update totals")) {
            updateTotals();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTotals();
    }
}
