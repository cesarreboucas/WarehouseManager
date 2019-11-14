package com.warehousemanager.ui.associate.todo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.Warehouse;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.network.IMovementService;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.MovementService;
import com.warehousemanager.data.network.WarehouseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoReportFragment extends Fragment implements View.OnClickListener {

    MovementOrder movementOrder = new MovementOrder();

    TextView txtOrderNumber;
    Spinner spnReason;
    EditText editReason;

    FragmentManagerHelper fragmentManagerHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_associate_todo_report, container, false);

        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        fragmentManagerHelper = new FragmentManagerHelper(getFragmentManager(), R.id.todoFragmentContainer);

        txtOrderNumber = view.findViewById(R.id.txtOrderNumber);
        spnReason = view.findViewById(R.id.spnReason);
        editReason = view.findViewById(R.id.editReason);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(final View v) {
        try{
            movementOrder.setReportType(spnReason.getSelectedItem().toString());
            movementOrder.setProblem(editReason.getText().toString());

        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }

        IMovementService movementService = MovementService.getInstance()
                .create(IWarehouseService.class);
        movementService.createMovementOrder(movementOrder).enqueue(new Callback<MovementOrder>() {
            @Override
            public void onResponse(Call<MovementOrder> call, Response<MovementOrder> response) {
                Toast.makeText(v.getContext(), "MovementOrder Created", Toast.LENGTH_SHORT).show();
                fragmentManagerHelper.goBack();
            }

            @Override
            public void onFailure(Call<MovementOrder> call, Throwable t) {
                Toast.makeText(v.getContext(), "Error creating MovementOrder", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
