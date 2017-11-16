package br.com.luisfelipeas5.gimmedetails.di.components

import br.com.luisfelipeas5.gimmedetails.ui.medias.MediasActivity

interface BaseComponent {
    fun inject(mediasActivity: MediasActivity)
}