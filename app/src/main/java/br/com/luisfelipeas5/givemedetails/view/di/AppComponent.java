package br.com.luisfelipeas5.givemedetails.view.di;

import br.com.luisfelipeas5.givemedetails.adapters.MoviesAdapter;
import br.com.luisfelipeas5.givemedetails.view.fragments.details.PosterFragment;
import br.com.luisfelipeas5.givemedetails.view.fragments.lists.MoviesFragment;
import dagger.Component;

@Component(modules = {PresenterModule.class, ModelModule.class})
public interface AppComponent {
    void inject(MoviesFragment moviesFragment);

    void inject(PosterFragment posterFragment);

    void inject(MoviesAdapter.ViewHolder viewHolder);
}
