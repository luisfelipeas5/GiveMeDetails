package br.com.luisfelipeas5.gimmedetails.ui.medias

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.luisfelipeas5.gimmedetails.GimmeDetailsApplication
import br.com.luisfelipeas5.gimmedetails.R
import javax.inject.Inject

class MediasActivity : AppCompatActivity(), MediasContract.View {

    private var presenter: MediasContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val gimmeDetailsApplication = application as GimmeDetailsApplication
        gimmeDetailsApplication.diComponent?.inject(this)
    }

    @Inject
    fun setPresenter(presenter: MediasContract.Presenter) {
        this.presenter = presenter
        presenter.attach(this)
    }
}
