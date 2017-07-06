package br.com.luisfelipeas5.givemedetails.view.di;

import br.com.luisfelipeas5.givemedetails.ui.fragments.lists.MoviesFragment;
import dagger.Component;

@Component(modules = {PresenterModule.class, ModelModule.class})
public interface AppComponent {
    void inject(MoviesFragment moviesFragment);
}
