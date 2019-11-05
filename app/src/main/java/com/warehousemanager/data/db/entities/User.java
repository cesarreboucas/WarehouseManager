package com.warehousemanager.data.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "user_table")
public class User implements Serializable {

  private static final long CURRENT_USER = 0;

  @SerializedName("id")
  @PrimaryKey(autoGenerate = false)
  private Long id = CURRENT_USER;

  @SerializedName("name")
  private String name;

  @SerializedName("username")
  private String username;

  @SerializedName("password")
  private String password;

  @SerializedName("role")
  private String role;

  //Warehouse Assigned to user, by warehouse name (If user is Worker/Admin)
  @SerializedName("assignment")
  private String assignment;

  @SerializedName("question")
  private String question;

  @SerializedName("answer")
  private String answer;

  @SerializedName("image")
  private String profileImage;

  public User(String name, String username, String password, String role, String assignment, String question, String answer, String profileImage) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.role = role;
    this.assignment = assignment;
    this.question = question;
    this.answer = answer;
    this.profileImage = profileImage;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getAssignment() {
    return assignment;
  }

  public void setAssignment(String assignment) {
    this.assignment = assignment;
  }

  public String getQuestion() { return question; }

  public void setQuestion(String question) { this.question = question; }

  public String getAnswer() { return answer; }

  public void setAnswer(String answer) { this.answer = answer; }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  @NonNull
  @Override
  public String toString() {
    return String.format("Name: %s | Username %s | Password: %s | Role: %s",
      name, username, password, role);
  }
}
