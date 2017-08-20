package test.dependencies.tsuyoyo.feature.eventregister.step;


import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterCore;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterStep;
import test.dependencies.tsuyoyo.feature.model.Prefecture;

public class PrefectureViewModel {

    private BehaviorProcessor<List<Prefecture>> prefectures = BehaviorProcessor.createDefault(
            Arrays.asList(
                    new Prefecture(1, "Tokyo"),
                    new Prefecture(2, "Chiba"),
                    new Prefecture(3, "Kanagawa"),
                    new Prefecture(4, "Saitama"),
                    new Prefecture(5, "Ibaraki")
            )
    );
    private BehaviorProcessor<Boolean> complete = BehaviorProcessor.create();

    private final EventRegisterCore eventRegisterCore;

    public PrefectureViewModel(EventRegisterCore eventRegisterCore) {
        this.eventRegisterCore = eventRegisterCore;
    }

    public interface Input {
        void apply(Prefecture prefecture);
        void cancel();
    }

    public interface Output {
        Flowable<List<Prefecture>> prefectures();
        Flowable<Boolean> complete();
    }

    public final Input input = new Input() {
        @Override
        public void apply(Prefecture prefecture) {
            eventRegisterCore.setPrefecture(prefecture);
            eventRegisterCore.moveStepTo(EventRegisterStep.TOP);
        }

        @Override
        public void cancel() {
            eventRegisterCore.moveStepTo(EventRegisterStep.TOP);
        }
    };

    public final Output output = new Output() {
        @Override
        public Flowable<List<Prefecture>> prefectures() {
            return prefectures.hide();
        }

        @Override
        public Flowable<Boolean> complete() {
            return complete.hide();
        }
    };

}
