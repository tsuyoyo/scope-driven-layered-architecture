package test.dependencies.tsuyoyo.app;

import dagger.Component;
import test.dependencies.tsuyoyo.app.AppModule;
import test.dependencies.tsuyoyo.di.scope.AppScope;
import test.dependencies.tsuyoyo.feature.model.AppData;

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {

    AppData provideAppData();
}
