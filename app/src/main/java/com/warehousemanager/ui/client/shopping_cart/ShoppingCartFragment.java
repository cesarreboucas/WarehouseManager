package com.warehousemanager.ui.client.shopping_cart;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.FragmentInteraction;

public class ShoppingCartFragment extends Fragment implements FragmentInteraction{

    private FragmentInteraction mListener;
    private IFragmentManagerHelper fragmentManagerHelper;

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
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void sendMessage(Message message) {   }

}
