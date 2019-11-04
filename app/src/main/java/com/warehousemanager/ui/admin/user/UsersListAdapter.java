package com.warehousemanager.ui.admin.user;

import android.graphics.Bitmap;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

  public UsersListAdapter(List<User> users, Fragment fragment) {
    this.users = users;
    this.fragment = fragment;
  }

  public class UserViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    TextView username, name;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);
      profileImage = itemView.findViewById(R.id.profile_image);
      username = itemView.findViewById(R.id.txtUsername);
      name = itemView.findViewById(R.id.txtName);
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
  public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, final int i) {
    String username = users.get(i).getUsername();
    String name = users.get(i).getName();

    userViewHolder.username.setText(username);
    userViewHolder.name.setText(name);

    userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Message m = new Message();
        m.obj = users.get(i);
        ((FragmentInteraction)fragment).sendMessage(m);
      }
    });
  }

  @Override
  public int getItemCount() {
    return users.size();
  }
}
