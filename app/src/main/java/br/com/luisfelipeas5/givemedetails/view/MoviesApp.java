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

        AppComponent appComponent = DaggerAppComponent.builder()
                .modelModule(new ModelModule(this))
                .build();
        setAppComponent(appComponent);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}
