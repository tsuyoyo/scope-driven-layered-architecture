package test.dependencies.tsuyoyo.app;

import dagger.Module;
import dagger.Provides;
import test.dependencies.tsuyoyo.di.scope.AppScope;
import test.dependencies.tsuyoyo.feature.model.AppData;

@AppScope
@Module
public class AppModule {

    @Provides
    @AppScope
    public AppData provideAppData() {
        return new AppData();
    }

}
