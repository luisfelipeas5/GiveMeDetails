package br.com.luisfelipeas5.gimmedetails.di.components

import br.com.luisfelipeas5.gimmedetails.di.modules.PresenterModule
import dagger.Component

@Component(modules = arrayOf(PresenterModule::class))
interface AppComponent: BaseComponent {
}