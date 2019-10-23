package com.warehousemanager.ui.admin.user;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;
import com.warehousemanager.data.internal.model.UserRow;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder>{

  private List<UserRow> userRows;
  private ImageHelper imageHelper = new ImageHelperImpl();

  public static class UserViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    TextView username, name;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);
      profileImage = itemView.findViewById(R.id.profile_image);
      username = itemView.findViewById(R.id.txtUsername);
      name = itemView.findViewById(R.id.txtName);
    }
  }

  public UsersListAdapter(List<UserRow> userRows) {
    this.userRows = userRows;
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
  public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
    Bitmap image = imageHelper.convertBase64ToBitmap(userRows.get(i).getProfileImage());
    String username = userRows.get(i).getUsername();
    String name = userRows.get(i).getName();

    userViewHolder.profileImage.setImageBitmap(image);
    userViewHolder.username.setText(username);
    userViewHolder.name.setText(name);
  }

  @Override
  public int getItemCount() {
    return userRows.size();
  }
}
