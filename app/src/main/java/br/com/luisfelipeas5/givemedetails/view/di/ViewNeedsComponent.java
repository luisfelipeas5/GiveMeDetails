package br.com.luisfelipeas5.givemedetails.view.di;

import br.com.luisfelipeas5.givemedetails.model.di.ModelModule;
import br.com.luisfelipeas5.givemedetails.ui.fragments.lists.MoviesFragment;
import dagger.Component;

@Component(modules = {ModelModule.class})
public interface ViewNeedsComponent {
    void inject(MoviesFragment moviesFragment);
}
