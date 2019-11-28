package com.warehousemanager.ui.admin.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.ui.admin.FragmentInteraction;

public class ProductsFragment extends Fragment implements FragmentInteraction {

    private FragmentInteraction mListener;
    private IFragmentManagerHelper fragmentManagerHelper;

    public ProductsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_admin_products, container, false);
        fragmentManagerHelper = new FragmentManagerHelper(getChildFragmentManager(), R.id.productsFragmentContainer);
        Bundle bundle = new Bundle();
        bundle.putString("test", "Testando arguments");
        Log.d("START", "ON_CREATE");
        fragmentManagerHelper.attach(ProductsFragmentList.class, bundle);

        Toolbar toolbar = view.findViewById(R.id.toolbarProdMain);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManagerHelper.goBack();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        Bundle bundle = getArguments();
        Log.d("START", "ON_START");
        try {
            String tag = bundle.getString("summary", "");
            if(tag.equals("showmovs")) {
                Product product = (Product) bundle.getSerializable("product");
                fragmentManagerHelper.attach(MoveProductsFragment.class, bundle);
            }
        } catch (Exception ex) {

        }
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void sendMessage(Message message) {   }
}
