package br.com.luisfelipeas5.gimmedetails

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner

class MvpContract {

    interface Presenter<in V: View>: LifecycleObserver {
        fun attach(view: V)
        fun detach()
    }

    interface View:LifecycleOwner

}