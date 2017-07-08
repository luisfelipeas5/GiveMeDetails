package br.com.luisfelipeas5.givemedetails.view;

import android.app.Application;

import br.com.luisfelipeas5.givemedetails.view.di.AppComponent;
import br.com.luisfelipeas5.givemedetails.view.di.DaggerAppComponent;
import br.com.luisfelipeas5.givemedetails.view.di.ModelModule;

public class MoviesApp extends Application{

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .modelModule(new ModelModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
