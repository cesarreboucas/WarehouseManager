package com.warehousemanager.data.dagger;

import com.warehousemanager.ui.ForgotPasswordActivity;
import com.warehousemanager.ui.admin.AdminHomeActivity;
import com.warehousemanager.ui.SignInActivity;
import com.warehousemanager.ui.SignUpActivity;
import com.warehousemanager.ui.admin.UsersFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

  @ContributesAndroidInjector()
  abstract SignInActivity contributeSignInActivity();

  @ContributesAndroidInjector()
  abstract SignUpActivity contributeSignUpActivity();

  @ContributesAndroidInjector()
  abstract ForgotPasswordActivity contributeForgotPasswordActivity();

  @ContributesAndroidInjector()
  abstract AdminHomeActivity contributeAdminHomeActivity();

  @ContributesAndroidInjector
  abstract UsersFragment contributeUsersFragment();

}
