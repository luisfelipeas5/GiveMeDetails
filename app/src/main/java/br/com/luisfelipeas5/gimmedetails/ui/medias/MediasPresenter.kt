package br.com.luisfelipeas5.gimmedetails.ui.medias

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import br.com.luisfelipeas5.gimmedetails.model.medias.Media

class MediasPresenter: MediasContract.Presenter {

    private var view: MediasContract.View? = null
    private var mMedias: List<Media>? = null

    override fun attach(view: MediasContract.View) {
        this.view = view
        view.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getMedias() {
        if (mMedias == null) {

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun detach() {
        if (view != null) {
            view!!.lifecycle.removeObserver(this)
            view = null
        }
    }

}