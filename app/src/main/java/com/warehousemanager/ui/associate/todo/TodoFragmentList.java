package com.warehousemanager.ui.associate.todo;

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

public class TodoFragmentList extends Fragment
        implements View.OnClickListener,  SwipeRefreshLayout.OnRefreshListener, FragmentInteraction {

    private IWarehouseService warehouseService = WarehouseService.getInstance().create(IWarehouseService.class);

    private List<MovementOrder> movementOrder;

    private RecyclerView todoList;
    private TodoListAdapter todoListAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    private IFragmentManagerHelper fragmentManagerHelper;

    public TodoFragmentList() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_associate_todo_list, container, false);
        todoList = view.findViewById(R.id.todoList);

        fragmentManagerHelper = new FragmentManagerHelper(
                getFragmentManager(), R.id.todoFragmentContainer);

        swipeRefreshLayout = view.findViewById(R.id.todoListRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        progressBar = view.findViewById(R.id.progress_loader);

        todoList.setLayoutManager(new LinearLayoutManager(getContext()));
        movementOrder = new ArrayList<>();
        todoListAdapter = new TodoListAdapter(movementOrder, this);

        todoList.setAdapter(todoListAdapter);

        getData();

        return view;
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        warehouseService.getAllMovementOrders().enqueue(new Callback<List<MovementOrder>>() {
            @Override
            public void onResponse(Call<List<MovementOrder>> call, Response<List<MovementOrder>> response) {
                if(response.isSuccessful()) {
                    movementOrder.addAll(response.body());
                    todoListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to get the movement orders", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MovementOrder>> call, Throwable t) {
                Toast.makeText(getContext(), "There was a problem when trying to connect to the server", Toast.LENGTH_SHORT).show();
            }
        });
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
