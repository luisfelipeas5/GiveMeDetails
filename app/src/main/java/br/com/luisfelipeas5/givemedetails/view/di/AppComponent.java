package br.com.luisfelipeas5.givemedetails.view.di;

import br.com.luisfelipeas5.givemedetails.view.fragments.details.DetailPosterFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.lists.MoviesFragment;
import dagger.Component;

@Component(modules = {PresenterModule.class, ModelModule.class})
public interface AppComponent {
    void inject(MoviesFragment moviesFragment);

    void inject(DetailPosterFragment detailPosterFragment);
}
