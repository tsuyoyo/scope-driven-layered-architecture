package test.dependencies.tsuyoyo.feature.eventregister.step;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import test.dependencies.tsuyoyo.di.scope.EventRegisterStepScope;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterComponent;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterCore;
import test.dependencies.tsuyoyo.feature.eventregister.view.PrefectureFragment;

@EventRegisterStepScope
@Component(
        modules = {
                PrefectureComponent.PrefectureModule.class
        },
        dependencies = {
                EventRegisterComponent.class
        }
)
public interface PrefectureComponent {

    void inject(PrefectureFragment fragment);

    @Module
    @EventRegisterStepScope
    class PrefectureModule {

        @Provides
        @EventRegisterStepScope
        PrefectureViewModel providePrefectureViewModel(EventRegisterCore eventRegisterCore) {
            return new PrefectureViewModel(eventRegisterCore);
        }
    }
}
