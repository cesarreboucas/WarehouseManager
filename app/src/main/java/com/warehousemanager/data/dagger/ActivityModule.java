package com.warehousemanager.data.dagger;

import com.warehousemanager.ui.ForgotPasswordActivity;
import com.warehousemanager.ui.HomeActivity;
import com.warehousemanager.ui.SignInActivity;
import com.warehousemanager.ui.SignUpActivity;

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
  abstract HomeActivity contributeHomeActivity();

}
