package com.warehousemanager.data.internal.model;

import com.google.gson.annotations.SerializedName;

public class UserRow {
  @SerializedName("profile_image")
  private String profileImage;
  private String username;
  private String name;

  public UserRow(String profileImage, String username, String name) {
    this.profileImage = profileImage;
    this.username = username;
    this.name = name;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
