package test.dependencies.tsuyoyo.feature.eventregister.step;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import test.dependencies.tsuyoyo.di.scope.EventRegisterStepScope;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterComponent;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterCore;
import test.dependencies.tsuyoyo.feature.eventregister.view.DescriptionFragment;

@EventRegisterStepScope
@Component(
        modules = {
                DescriptionComponent.DescriptionModule.class
        },
        dependencies = {
                EventRegisterComponent.class
        }
)
public interface DescriptionComponent {

    void inject(DescriptionFragment fragment);

    @Module
    @EventRegisterStepScope
    class DescriptionModule {

        @Provides
        @EventRegisterStepScope
        DescriptionViewModel provideDescriptionViewModel(EventRegisterCore eventRegisterCore) {
            return new DescriptionViewModel(eventRegisterCore);
        }

    }
}
