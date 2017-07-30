package br.com.luisfelipeas5.givemedetails.view;

import android.app.Application;

import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;
import br.com.luisfelipeas5.givemedetails.view.di.components.DaggerAppComponent;
import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelModule;

public class MoviesApp extends Application{

    private BaseComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        BaseComponent baseComponent = DaggerAppComponent.builder()
                .modelModule(new ModelModule(this))
                .build();
        setDiComponent(baseComponent);
    }

    public BaseComponent getDiComponent() {
        return appComponent;
    }

    public void setDiComponent(BaseComponent appComponent) {
        this.appComponent = appComponent;
    }
}
