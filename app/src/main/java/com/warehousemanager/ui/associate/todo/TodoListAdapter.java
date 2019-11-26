package com.warehousemanager.ui.associate.todo;

import android.graphics.Color;
import android.os.Message;
import android.provider.CalendarContract;
import android.support.annotation.ColorInt;
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

    public static final int REPORT_TODO = 1;
    public static final int SCAN_TODO = 2;

    WarehouseDatabase warehouseDatabase;

    public class TodoListViewHolder extends RecyclerView.ViewHolder {
        TextView txtTransferType;
        TextView txtItemCount;
        TextView txtItemName;
        TextView txtOrderNumber;

        ImageButton btnReport;
        ImageButton btnScan;

        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTransferType = itemView.findViewById(R.id.txtTransferType);
            txtItemCount = itemView.findViewById(R.id.txtItemCount);
            txtItemName = itemView.findViewById(R.id.txtItem);
            txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber);

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

        String wh = warehouseDatabase.userDao().getUser().getFavouriteWarehouse();
        String transferType = "";
        if(wh.equals(todoList.get(i).getWarehouseSender())) {
            transferType = "Recieving";
            if(todoList.get(i).getSentStatus())
                todoListViewHolder.itemView.setBackgroundColor(Color.green());
            else
                todoListViewHolder.itemView.setBackgroundColor(Color.yellow());
        } else {
            transferType = "Sending";
            todoListViewHolder.itemView.setBackgroundColor(Color.pink());
        }
        String id = todoList.get(i).getId();
        String itemName = todoList.get(i).getProductName();
        if(itemName.length() > 10) {
            itemName = itemName.substring(0, 9) + "...";
        }
        int itemCount = todoList.get(i).getQuantity();

        todoListViewHolder.btnScan.setOnClickListener(this);
        todoListViewHolder.btnScan.setTag(i);
        todoListViewHolder.btnReport.setOnClickListener(this);
        todoListViewHolder.btnReport.setTag(i);

        todoListViewHolder.txtTransferType.setText(transferType);
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
