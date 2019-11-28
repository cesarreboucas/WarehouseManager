package com.warehousemanager.ui.associate.pickup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.admin.product.ProductsListAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PickupDetailFragment extends Fragment {

    ClientOrder clientOrder;

    TextView txtOrderNumber;
    ListView itemListView;
    Button btnClose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if(getArguments().getSerializable("CLIENT_ORDER") != null) {
                this.clientOrder = (ClientOrder) getArguments().getSerializable("CLIENT_ORDER");
            }
        } catch (NullPointerException ex) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_associate_pickup_details, container, false);

        txtOrderNumber = view.findViewById(R.id.txtOrderNumber);
        itemListView = view.findViewById(R.id.itemListView);

        setPickupDetails();

        return view;
    }

    public void setPickupDetails()
    {
        txtOrderNumber.setText(String.valueOf(clientOrder.getId()));

        ArrayList<String> myList = new ArrayList<>();


        for( Product p : clientOrder.getProducts())
        {
            String string = p.getName() + " " + p.getQuantity();
            myList.add(string);
        }


        ArrayAdapter<String> itemList = new ArrayAdapter<>(getContext() , android.R.layout.simple_list_item_1, myList);

        itemListView.setAdapter(itemList);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}