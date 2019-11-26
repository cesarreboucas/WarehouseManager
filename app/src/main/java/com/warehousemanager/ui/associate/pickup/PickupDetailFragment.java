package com.warehousemanager.ui.associate.pickup;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.ClientOrder;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.What;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickupDetailFragment extends Fragment implements View.OnClickListener
{
    MovementOrder movementOrder = new MovementOrder();

    TextView txtOrderNumber;
    TextView txtCustomerName;
    TableLayout tv;

    IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

    FragmentManagerHelper fragmentManagerHelper;

    ClientOrder clientOrder = (ClientOrder) getArguments().getSerializable("CLIENT_ORDER");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_associate_pickup_details, container, false);


        Button btnCompletePickup = view.findViewById(R.id.btnCompletePickup);
        btnCompletePickup.setOnClickListener(this);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.pickupFragmentContainer);

        txtOrderNumber = view.findViewById(R.id.txtOrderNumber);
        txtCustomerName = view.findViewById(R.id.txtCustomerName);

        tv = view.findViewById(R.id.tblItemsList);

        setData();

        return view;
    }

    public void setData()
    {
        tv.removeAllViews();

        for (Product p: clientOrder.getProducts()) {
            LayoutInflater inflator = getLayoutInflater();
            TableRow tableRow = (TableRow) inflator.inflate(R.layout.product_list_table_row, null);
            TextView txtItemName = tableRow.findViewById(R.id.txtItemName);
            TextView txtItemAmount = tableRow.findViewById(R.id.txtItemAmount);

            txtItemName.setText(p.getName());
            txtItemAmount.setText(p.getQuantity());

            tv.addView(tableRow);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(final View v) {
        try{

            android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Complete Pickup");
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(v.getContext(), "Order Pickup Canceled", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setPositiveButton("Complete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    clientOrder.setDone(1);
                    warehouseService.editClientOrder(clientOrder);
                    Toast.makeText(v.getContext(), "YAAAY!", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
        // pickup The movements endpoints

        warehouseService.createMovementOrder(movementOrder).enqueue(new Callback<MovementOrder>() {
            @Override
            public void onResponse(Call<MovementOrder> call, Response<MovementOrder> response) {
                Toast.makeText(v.getContext(), "Order Pickup Completed", Toast.LENGTH_SHORT).show();
                fragmentManagerHelper.goBack();
            }

            @Override
            public void onFailure(Call<MovementOrder> call, Throwable t) {
                Toast.makeText(v.getContext(), "Error Completing Order Pickup", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
