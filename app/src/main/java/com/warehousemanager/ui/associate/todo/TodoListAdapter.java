package com.warehousemanager.ui.associate.todo;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.MovementOrder;
import com.warehousemanager.data.db.entities.Product;
import com.warehousemanager.ui.admin.FragmentInteraction;
import com.warehousemanager.ui.associate.pending.PendingListAdapter;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> implements View.OnClickListener {

    private List<MovementOrder> todoList;
    private Fragment fragment;

    public static final int REPORT_TODO = 1;
    public static final int SCAN_TODO = 2;

    public TodoListAdapter(List<MovementOrder> todoList, Fragment fragment) {
        this.todoList = todoList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public TodoListAdapter.TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_associate_todo_list_row, viewGroup, false);
        TodoListAdapter.TodoListViewHolder todoListViewHolder = new TodoListAdapter.TodoListViewHolder(view);
        return todoListViewHolder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReport:
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

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.TodoListViewHolder todoListViewHolder, int i) {
        /*
        String transferType = todoList.get(i).getTransferType();
        Product item = todoList.get(i).getItem();
        String itemName = item.getName();
        int itemCount = item.getQuantity();

        todoListViewHolder.btnScan.setOnClickListener(this);
        todoListViewHolder.btnScan.setTag(i);
        todoListViewHolder.btnReport.setOnClickListener(this);
        todoListViewHolder.btnReport.setTag(i);

        todoListViewHolder.txtTransferType.setText(transferType);
        todoListViewHolder.txtItemCount.setText(itemCount);
        todoListViewHolder.txtItemName.setText(itemName);
        */
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class TodoListViewHolder extends RecyclerView.ViewHolder {
        TextView txtTransferType;
        TextView txtItemCount;
        TextView txtItemName;

        Button btnReport;
        Button btnScan;

        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTransferType = itemView.findViewById(R.id.txtTransferType);
            txtItemCount = itemView.findViewById(R.id.txtItemCount);
            txtItemName = itemView.findViewById(R.id.txtItem);

            btnReport = itemView.findViewById(R.id.btnReport);
            btnScan = itemView.findViewById(R.id.btnScan);
        }
    }
}
