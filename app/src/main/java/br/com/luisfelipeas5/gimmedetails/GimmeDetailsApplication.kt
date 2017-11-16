package br.com.luisfelipeas5.gimmedetails

import android.app.Application
import br.com.luisfelipeas5.gimmedetails.di.components.BaseComponent
import br.com.luisfelipeas5.gimmedetails.di.components.DaggerAppComponent

class GimmeDetailsApplication: Application() {

    var diComponent: BaseComponent? = null

    override fun onCreate() {
        super.onCreate()
        diComponent = DaggerAppComponent.create()
    }

}
