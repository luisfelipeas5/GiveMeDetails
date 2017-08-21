package br.com.luisfelipeas5.givemedetails.view.di.components;

import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelTestModule;
import br.com.luisfelipeas5.givemedetails.view.di.modules.presenter.PresenterModule;
import dagger.Component;

@Component(modules = {PresenterModule.class, ModelTestModule.class})
interface AppTestComponent extends BaseComponent{
}
