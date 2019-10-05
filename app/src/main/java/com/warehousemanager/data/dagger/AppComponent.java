package com.warehousemanager.data.dagger;

import android.app.Application;

import com.warehousemanager.AppController;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
  ActivityModule.class,
  ApiModule.class,
  DbModule.class,
  AndroidSupportInjectionModule.class
})
@Singleton
public interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(Application application);
    AppComponent build();
  }

  void inject(AppController appController);
}
