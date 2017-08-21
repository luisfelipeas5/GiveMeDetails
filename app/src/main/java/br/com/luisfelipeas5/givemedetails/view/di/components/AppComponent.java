package br.com.luisfelipeas5.givemedetails.view.di.components;

import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelModule;
import br.com.luisfelipeas5.givemedetails.view.di.modules.presenter.PresenterModule;
import dagger.Component;

@Component(modules = {PresenterModule.class, ModelModule.class})
interface AppComponent extends BaseComponent {

}
