package test.dependencies.tsuyoyo.feature.eventregister;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import test.dependencies.tsuyoyo.app.AppComponent;
import test.dependencies.tsuyoyo.di.scope.EventRegisterScope;
import test.dependencies.tsuyoyo.feature.model.AppData;

@EventRegisterScope
@Component(
        modules = {
                EventRegisterComponent.EventRegisterModule.class
        },
        dependencies = {
                AppComponent.class
        }
)
public interface EventRegisterComponent {
    AppData provideAppData();
    EventRegisterCore provideEventRegisterCore();

    void inject(EventRegisterRootActivity activity);

    @Module
    @EventRegisterScope
    class EventRegisterModule {

        @Provides
        @EventRegisterScope
        EventRegisterCore provideEventRegisterCore() {
            return new EventRegisterCore();
        }
    }
}
