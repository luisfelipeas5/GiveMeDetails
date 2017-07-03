package br.com.luisfelipeas5.givemedetails.presenter;

public interface MvpPresenter<T> {
    void attach(T t);

    void detachView();
}
