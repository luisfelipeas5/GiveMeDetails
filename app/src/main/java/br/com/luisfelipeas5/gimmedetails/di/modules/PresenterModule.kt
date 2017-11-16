package br.com.luisfelipeas5.gimmedetails.di.modules

import br.com.luisfelipeas5.gimmedetails.ui.medias.MediasContract
import br.com.luisfelipeas5.gimmedetails.ui.medias.MediasPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideMediasPresenter(): MediasContract.Presenter {
        return MediasPresenter()
    }

}