package com.warehousemanager.ui.associate.todo;

import android.graphics.Color;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.WarehouseDatabase;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> implements View.OnClickListener {

    private List<MovementOrder> todoList;
    private Fragment fragment;

    WarehouseDatabase warehouseDatabase;

    public static final int REPORT_TODO = 1;
    public static final int SCAN_TODO = 2;

    public class TodoListViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemCount;
        TextView txtItemName;
        TextView txtOrderNumber;
        TextView txtWarehouseSender;
        TextView txtWarehouseReceiverLabel;
        TextView txtWarehouseReceiver;

        ImageButton btnReport;
        ImageButton btnScan;

        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemCount = itemView.findViewById(R.id.txtItemCount);
            txtItemName = itemView.findViewById(R.id.txtItem);
            txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber);
            txtWarehouseSender = itemView.findViewById(R.id.txtWarehouseSender);
            txtWarehouseReceiverLabel = itemView.findViewById(R.id.txtWarehouseReceiverLabel);
            txtWarehouseReceiver = itemView.findViewById(R.id.txtWarehouseReceiver);

            btnReport = itemView.findViewById(R.id.btnReport);
            btnScan = itemView.findViewById(R.id.btnScan);
        }
    }

    public TodoListAdapter(List<MovementOrder> todoList, Fragment fragment) {
        this.todoList = todoList;
        this.fragment = fragment;

        warehouseDatabase = WarehouseDatabase.getAppDatabase(fragment.getActivity().getApplicationContext());
    }

    @NonNull
    @Override
    public TodoListAdapter.TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_associate_todo_list_row, viewGroup, false);
        TodoListAdapter.TodoListViewHolder todoListViewHolder = new TodoListAdapter.TodoListViewHolder(view);
        return todoListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.TodoListViewHolder todoListViewHolder, int i) {
        int color;
        String warehouseSender = " ";
        String warehouseReceiverLbl = " ";
        String warehouseReceiver = "  ";

        String wh = warehouseDatabase.userDao().getUser().getFavouriteWarehouse();

        if(wh.equals(todoList.get(i).getWarehouseReceiver()))
        {
            if(todoList.get(i).getReceived()) {
                color = Color.LTGRAY;
                warehouseSender = "COMPLETED";
                warehouseReceiverLbl = todoList.get(i).getWarehouseSender() + " > ";
                warehouseReceiver = todoList.get(i).getWarehouseReceiver();
            } else {
                color = Color.parseColor("#77dd77");
                warehouseSender = "RECEIVE";
                warehouseReceiverLbl = "Receiving From: ";
                warehouseReceiver = todoList.get(i).getWarehouseSender();
                if(warehouseReceiver.trim() == null);
                    warehouseReceiver = "Outside Source";
            }
        } else {
            if(todoList.get(i).getSent()) {
                color = Color.LTGRAY;
                warehouseSender = "COMPLETED";
                warehouseReceiverLbl = todoList.get(i).getWarehouseSender() + " > ";
                warehouseReceiver = todoList.get(i).getWarehouseReceiver();
            } else {
                color = Color.parseColor("#ff6961");
                warehouseSender = "SEND";
                warehouseReceiverLbl = "Sending To: ";
                warehouseReceiver = todoList.get(i).getWarehouseReceiver();
            }
        }


        String id = todoList.get(i).getId();
        String itemName = todoList.get(i).getProductName();
        if(itemName.length() > 10) {
            itemName = itemName.substring(0, 9) + "...";
        }
        int itemCount = todoList.get(i).getQuantity();

        todoListViewHolder.itemView.setBackgroundColor(color);

        todoListViewHolder.btnScan.setOnClickListener(this);
        todoListViewHolder.btnScan.setTag(i);
        todoListViewHolder.btnReport.setOnClickListener(this);
        todoListViewHolder.btnReport.setTag(i);

        todoListViewHolder.txtWarehouseReceiverLabel.setText(warehouseReceiverLbl);
        todoListViewHolder.txtWarehouseReceiver.setText(warehouseReceiver);
        todoListViewHolder.txtWarehouseSender.setText(warehouseSender);
        todoListViewHolder.txtOrderNumber.setText(id);
        todoListViewHolder.txtItemCount.setText(itemCount + "");
        todoListViewHolder.txtItemName.setText(itemName);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDetailedReport:
                onBtnReportClicked(v);
                break;
            case R.id.btnScan:
                onBtnScanClicked(v);
                break;
        }
    }

    private void onBtnReportClicked(View v){
        int i = (int) v.getTag();

        Message m = new Message();
        m.obj = todoList.get(i);
        m.what = REPORT_TODO;
        ((FragmentInteraction)fragment).sendMessage(m);
    }

    private void onBtnScanClicked(View v){
        int i = (int) v.getTag();

        Message m = new Message();
        m.obj = todoList.get(i);
        m.what = SCAN_TODO;
        ((FragmentInteraction)fragment).sendMessage(m);
    }

}
