package br.com.luisfelipeas5.givemedetails.view.di;

import br.com.luisfelipeas5.givemedetails.model.di.ModelModule;
import br.com.luisfelipeas5.givemedetails.presenter.di.PresenterModule;
import br.com.luisfelipeas5.givemedetails.ui.fragments.lists.PopularMoviesFragment;
import dagger.Component;

@Component(modules = {PresenterModule.class, ModelModule.class})
public interface ViewNeedsComponent {
    void inject(PopularMoviesFragment popularMoviesFragment);
}
