package test.dependencies.tsuyoyo.feature.eventregister.step;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import test.dependencies.tsuyoyo.di.scope.EventRegisterStepScope;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterComponent;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterCore;
import test.dependencies.tsuyoyo.feature.eventregister.view.EventRegisterFragment;

@EventRegisterStepScope
@Component(
        modules = {
                EventRegisterTopComponent.EventRegisterModule.class
        },
        dependencies = {
                EventRegisterComponent.class
        }
)
public interface EventRegisterTopComponent {

    void inject(EventRegisterFragment fragment);

    @EventRegisterStepScope
    @Module
    class EventRegisterModule {

        @Provides
        @EventRegisterStepScope
        EventRegisterViewModel provideEventRegisterTopViewModel(
                EventRegisterCore eventRegisterCore) {
            return new EventRegisterViewModel(eventRegisterCore);
        }

    }
}
