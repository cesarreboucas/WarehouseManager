package com.warehousemanager.ui.admin.user;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.warehousemanager.R;
import com.warehousemanager.data.db.entities.User;
import com.warehousemanager.data.internal.ImageHelper;
import com.warehousemanager.data.internal.ImageHelperImpl;
import com.warehousemanager.data.internal.What;
import com.warehousemanager.ui.admin.FragmentInteraction;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

  private List<User> users;
  private Fragment fragment;

  public UserListAdapter(List<User> users, Fragment fragment) {
    this.users = users;
    this.fragment = fragment;
  }

  public void refreshUserRole(String role, String username) {
    for (int i = 0; i < users.size(); i++) {
      if(users.get(i).getUsername().equals(username)) {
        users.get(i).setRole(role);
        break;
      }
    }
    notifyDataSetChanged();
  }

  public void removeUser(int position) {
    users.remove(position);
    notifyItemRemoved(position);
  }

  public int getUserPostion(String username) {
    for (int i = 0; i < users.size(); i++) {
      if(users.get(i).getUsername().equals(username)) {
        return i;
      }
    }
    return -1;
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

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public void onBindViewHolder(@NonNull final UserViewHolder userViewHolder, int i) {
    final String username = users.get(i).getUsername();
    String name = users.get(i).getName();

    userViewHolder.username.setText(username);
    userViewHolder.name.setText(name);

    String role = users.get(i).getRole();
    userViewHolder.role.setText(role);
    if(role.toLowerCase().equals("admin")) {
      userViewHolder.profileImage
              .setBorderColor(fragment.getResources().getColor(R.color.colorPrimaryDark));
      userViewHolder.profileImage.setImageResource(R.drawable.ic_domain_black_24dp);
    } else if(role.toLowerCase().equals("associate")) {
      userViewHolder.profileImage
              .setBorderColor(fragment.getResources().getColor(R.color.colorAccentDark));
      userViewHolder.profileImage.setImageResource(R.drawable.ic_local_shipping_black_24dp);
    } else {
      userViewHolder.profileImage
              .setBorderColor(fragment.getResources().getColor(R.color.darkGray));
      userViewHolder.profileImage.setImageResource(R.drawable.ic_person_black_24dp);
    }

    userViewHolder.btnRemoveUser.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            //v.setAnimation(AnimationUtils.loadAnimation(fragment.getContext(), R.anim.fade_in));
            v.setAlpha(0.6F);
            break;
          case MotionEvent.ACTION_UP:
            v.setAlpha(1F);
            final User user = users.get(userViewHolder.getAdapterPosition());

            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
            builder.setTitle("Are you sure you want to delete " + user.getUsername() + "?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {

              }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                Message m = new Message();
                m.what = What.REMOVE;
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", user.getUsername());
                m.obj = bundle;
                ((FragmentInteraction)fragment).sendMessage(m);
              }
            });
            builder.show();
            break;
        }
        return false;
      }
    });

    userViewHolder.btnMoreOptions.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setAlpha(0.6F);
            break;
          case MotionEvent.ACTION_UP:
            v.setAlpha(1F);
            final String[] roles = fragment.getResources().getStringArray(R.array.roles_entries);

            AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
            builder.setTitle("Pick a role");
            builder.setItems(roles, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                User user = users.get(userViewHolder.getAdapterPosition());
                Message m = new Message();
                m.what = What.UPDATE;
                Bundle bundle = new Bundle();
                bundle.putString("USERNAME", user.getUsername());
                bundle.putString("ROLE", roles[which]);
                m.obj = bundle;
                ((FragmentInteraction)fragment).sendMessage(m);
              }
            });
            builder.show();
            break;
        }
        return false;
      }
    });
  }

  @Override
  public int getItemCount() {
    return users.size();
  }

  public class UserViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    TextView username, name, role;
    ImageButton btnMoreOptions, btnRemoveUser;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);
      profileImage = itemView.findViewById(R.id.profile_image);
      username = itemView.findViewById(R.id.txtUsername);
      name = itemView.findViewById(R.id.txtName);
      btnMoreOptions = itemView.findViewById(R.id.btnMoreOptions);
      btnRemoveUser = itemView.findViewById(R.id.btnRemoveUser);
      role = itemView.findViewById(R.id.txtRole);
    }
  }
}
