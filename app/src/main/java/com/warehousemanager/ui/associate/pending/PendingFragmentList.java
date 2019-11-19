package com.warehousemanager.ui.associate.pending;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.internal.FragmentManagerHelper;
import com.warehousemanager.data.internal.IFragmentManagerHelper;
import com.warehousemanager.data.internal.JsonReader;
import com.warehousemanager.data.network.IWarehouseService;
import com.warehousemanager.data.network.WarehouseService;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingFragmentList extends Fragment
        implements View.OnClickListener,  SwipeRefreshLayout.OnRefreshListener, FragmentInteraction {

    IWarehouseService movementService = WarehouseService.getInstance().create(IWarehouseService.class);

    List<MovementOrder> movementOrder;

    PendingListAdapter pendingListAdapter;

    private RecyclerView pendingList;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    JsonReader jsonReader;
    IFragmentManagerHelper fragmentManagerHelper;

    public PendingFragmentList() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_associate_pending_list, container, false);
        jsonReader = new JsonReader(getContext());
        pendingList = view.findViewById(R.id.pendingList);

        fragmentManagerHelper = new FragmentManagerHelper(
                getFragmentManager(), R.id.pendingFragmentContainer);

        swipeRefreshLayout = view.findViewById(R.id.pendingListRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        progressBar = view.findViewById(R.id.progress_loader);

        pendingList.setLayoutManager(new LinearLayoutManager(getContext()));
        movementOrder = new ArrayList<>();
        pendingListAdapter = new PendingListAdapter(movementOrder, this);

        pendingList.setAdapter(pendingListAdapter);

        getData();

        return view;
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
/*        movementService.getAllPendingOrders().enqueue(new Callback<List<MovementOrder>>() {
            @Override
            public void onResponse(Call<List<MovementOrder>> call, Response<List<MovementOrder>> response) {
                if(response.body() != null) {
                    movementOrder.clear();
                    movementOrder.addAll(response.body());
                    pendingListAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<MovementOrder>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to reach the server", Toast.LENGTH_LONG).show();
                Log.d("ERROR", t.getMessage());
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MovementOrder", "RESUME");
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void sendMessage(Message message) {

    }

}
