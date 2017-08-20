package test.dependencies.tsuyoyo.feature.eventregister.step;


import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterCore;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterStep;

public class DescriptionViewModel {

    public interface Input {
        void apply(String description);
        void cancel();
    }

    public interface Output {
        Flowable<String> description();
        Flowable<Boolean> complete();
    }

    private BehaviorProcessor<String> description = BehaviorProcessor.create();
    private BehaviorProcessor<Boolean> complete = BehaviorProcessor.create();

    private final EventRegisterCore eventRegisterCore;

    DescriptionViewModel(EventRegisterCore eventRegisterCore) {
        this.eventRegisterCore = eventRegisterCore;
        description.onNext(eventRegisterCore.getEvent().description);
    }

    public final Input input = new Input() {
        @Override
        public void apply(String description) {
            eventRegisterCore.setDescription(description);
            eventRegisterCore.moveStepTo(EventRegisterStep.TOP);
            complete.onNext(true);
        }

        @Override
        public void cancel() {
            eventRegisterCore.moveStepTo(EventRegisterStep.TOP);
            complete.onNext(true);
        }
    };

    public final Output output = new Output() {
        @Override
        public Flowable<String> description() {
            return description.hide();
        }

        @Override
        public Flowable<Boolean> complete() {
            return complete.hide();
        }
    };

}
