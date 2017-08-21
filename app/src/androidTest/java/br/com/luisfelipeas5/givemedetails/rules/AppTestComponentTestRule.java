package br.com.luisfelipeas5.givemedetails.rules;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import br.com.luisfelipeas5.givemedetails.view.MoviesApp;
import br.com.luisfelipeas5.givemedetails.view.di.components.BaseComponent;
import br.com.luisfelipeas5.givemedetails.view.di.components.DaggerAppTestComponent;
import br.com.luisfelipeas5.givemedetails.view.di.modules.model.ModelTestModule;

public class AppTestComponentTestRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        ModelTestModule modelTestModule = new ModelTestModule();
        setAppTestComponent(modelTestModule);
        return base;
    }

    public static void setAppTestComponent(ModelTestModule modelTestModule) {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        MoviesApp moviesApp = (MoviesApp) context.getApplicationContext();
        BaseComponent moviesComponent = DaggerAppTestComponent.builder()
                .modelTestModule(modelTestModule)
                .build();
        moviesApp.setDiComponent(moviesComponent);
    }
}
