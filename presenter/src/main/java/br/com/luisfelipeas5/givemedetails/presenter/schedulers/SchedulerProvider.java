package br.com.luisfelipeas5.givemedetails.presenter.schedulers;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler io();

    Scheduler ui();
}
