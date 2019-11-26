package com.warehousemanager.ui.admin.summary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.db.entities.ProductHang;
import com.warehousemanager.data.db.entities.WarehouseHang;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.admin.product.ProductsFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDatail extends Fragment {

    ClientOrder clientOrder;
    TextView txtOrderID;
    TextView txtReady;
    TableLayout tableProducts;
    List<ProductHang> productHangList;
    IFragmentManagerHelper fragmentManagerHelper;
    IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

    public OrderDatail() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientOrder = (ClientOrder) getArguments().getSerializable("clientOrder");
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.summariesFragmentContainer);
        Log.d("DBX", clientOrder.getId()+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_admin_order_datail, container, false);
        txtOrderID = view.findViewById(R.id.txtOrderID);
        txtReady = view.findViewById(R.id.txtReady);
        tableProducts = view.findViewById(R.id.tableProducts);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtOrderID.setText(String.valueOf(clientOrder.getId()));
        txtReady.setText(clientOrder.getReady()+"");
        if(clientOrder.getReady()==1) {
            txtReady.setText("Ready");
        } else {
            txtReady.setText("Not Ready");
            txtReady.setBackgroundColor(Color.parseColor("#e77fd5"));
        }

        warehouseService.getAllProductsHangs().enqueue(new Callback<List<ProductHang>>() {
            @Override
            public void onResponse(Call<List<ProductHang>> call, Response<List<ProductHang>> response) {
                productHangList = response.body();
                populateTable();

            }

            @Override
            public void onFailure(Call<List<ProductHang>> call, Throwable t) {

            }
        });
    }

    public void populateTable() {
        TableRow headingRow = getDefaultTableRow();
        TextView txtBarcode = getDefaultTextView(4, "BarCode");
        TextView txtOrdered = getDefaultTextView(3, "Ordered");
        TextView txtAvailable = getDefaultTextView(3, "Available");
        headingRow.addView(txtBarcode);
        headingRow.addView(txtOrdered);
        headingRow.addView(txtAvailable);
        tableProducts.addView(headingRow);

        for(final Product orderProduct : clientOrder.getProducts()) {
            for(ProductHang productHangs : productHangList) {
                if(productHangs.getBarcode().equals(orderProduct.getBarcode())) {
                    TableRow row = getDefaultTableRow();

                    TextView tvName = getDefaultTextView(4, productHangs.getBarcode());
                    row.addView(tvName);

                    TextView tvOrderQuantity = getDefaultTextView(3, String.valueOf(orderProduct.getQuantity()));
                    row.addView(tvOrderQuantity);

                    TextView tvQuantityAvailable = getDefaultTextView(3, "");
                    for(WarehouseHang warehouseHang : productHangs.getWarehouses()) {
                        if(warehouseHang.getWarehouse_key().equals(clientOrder.getWarehouseKey())) {
                            tvQuantityAvailable.setText(String.valueOf(warehouseHang.getFreeQuantity()));
                            if(warehouseHang.getFreeQuantity()<orderProduct.getQuantity()) {
                                tvQuantityAvailable.setTextColor(Color.RED);
                            }
                        }
                    }

                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Message message = new Message();
                            message.what = 2;
                            message.obj = orderProduct;
                            ((FragmentInteraction) getActivity()).sendMessage(message);

                            //fragmentManagerHelper.attach(ProductsFragment.class);

                        }
                    });

                    row.addView(tvQuantityAvailable);

                    tableProducts.addView(row);
                }
            }
        }
    }

    public TableRow getDefaultTableRow() {
        TableRow row = new TableRow(getContext());
        row.setWeightSum(10);
        row.setPadding(0,5,0,5);
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        return row;
    }

    public TextView getDefaultTextView(int size, String content) {
        TextView tv = new TextView(getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(18);
        tv.setBackground(getResources().getDrawable(R.drawable.border));
        tv.setTextColor(Color.BLACK);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,size));
        tv.setText(content);
        return tv;
    }
}
