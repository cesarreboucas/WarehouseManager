package com.warehousemanager.ui.admin.user;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder>{

  private List<User> users;
  private ImageHelper imageHelper = new ImageHelperImpl();
  private Fragment fragment;

  public static final int CHANGE_USER_ROLE = 2;

  public UsersListAdapter(List<User> users, Fragment fragment) {
    this.users = users;
    this.fragment = fragment;
  }

  public class UserViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    TextView username, name;
    ImageButton btnMoreOptions;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);
      profileImage = itemView.findViewById(R.id.profile_image);
      username = itemView.findViewById(R.id.txtUsername);
      name = itemView.findViewById(R.id.txtName);
      btnMoreOptions = itemView.findViewById(R.id.btnMoreOptions);
    }
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater
      .from(viewGroup.getContext())
      .inflate(R.layout.fragment_admin_users_list_row, viewGroup, false);
    UserViewHolder userViewHolder = new UserViewHolder(view);
    return userViewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull final UserViewHolder userViewHolder, int i) {
    String username = users.get(i).getUsername();
    String name = users.get(i).getName();

    userViewHolder.username.setText(username);
    userViewHolder.name.setText(name);

    userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int i = userViewHolder.getAdapterPosition();
        Message m = new Message();
        m.obj = users.get(i);
        ((FragmentInteraction)fragment).sendMessage(m);
      }
    });

    userViewHolder.btnMoreOptions.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final String[] roles = fragment.getResources().getStringArray(R.array.roles_entries);

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle("Pick a role");
        builder.setItems(roles, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Message m = new Message();
            m.what = CHANGE_USER_ROLE;
            Bundle bundle = new Bundle();
            bundle.putString("USERNAME", users.get(userViewHolder.getAdapterPosition()).getUsername());
            bundle.putString("ROLE", roles[which]);
            m.obj = bundle;
            ((FragmentInteraction)fragment).sendMessage(m);
          }
        });
        builder.show();
      }
    });
  }

  @Override
  public int getItemCount() {
    return users.size();
  }
}
