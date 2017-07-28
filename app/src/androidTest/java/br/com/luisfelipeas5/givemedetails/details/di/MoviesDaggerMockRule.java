package br.com.luisfelipeas5.givemedetails.details.di;

import android.support.test.InstrumentationRegistry;

import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.di.AppComponent;
import br.com.luisfelipeas5.givemedetails.view.di.ModelModule;
import br.com.luisfelipeas5.givemedetails.view.di.PresenterModule;
import it.cosenonjaviste.daggermock.DaggerMockRule;

public class MoviesDaggerMockRule extends DaggerMockRule<AppComponent> {

    public MoviesDaggerMockRule() {
        super(AppComponent.class, PresenterModule.class, ModelModule.class);
        set(new ComponentSetter<AppComponent>() {
            @Override
            public void setComponent(AppComponent appComponent) {
                MoviesApp moviesApp = getMoviesApp();
                moviesApp.setAppComponent(appComponent);
            }
        });
    }

    private MoviesApp getMoviesApp() {
        return (MoviesApp) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }
}
