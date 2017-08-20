package test.dependencies.tsuyoyo.feature.eventregister.step;


import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.processors.PublishProcessor;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterCore;
import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterStep;
import test.dependencies.tsuyoyo.feature.model.Event;
import test.dependencies.tsuyoyo.feature.model.Prefecture;

public class EventRegisterViewModel {

    public interface Input {
        void setTitle(String name);
        void gotoDescriptionForm();
        void gotoPrefectureSelection();
        void apply();
    }

    public interface Output {
        Flowable<String> title();

        Flowable<String> description();

        Flowable<Prefecture> prefecture();

        Flowable<Boolean> completed();

        Flowable<String> error();
    }

    private BehaviorProcessor<String> title = BehaviorProcessor.createDefault("");
    private BehaviorProcessor<String> description = BehaviorProcessor.createDefault("");
    private BehaviorProcessor<Prefecture> prefecture =
            BehaviorProcessor.createDefault(new Prefecture(-1, "no pref"));
    private BehaviorProcessor<Boolean> completed = BehaviorProcessor.createDefault(false);
    private PublishProcessor<String> error = PublishProcessor.create();

    private final EventRegisterCore eventRegisterCore;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public EventRegisterViewModel(EventRegisterCore eventRegisterCore) {
        this.eventRegisterCore = eventRegisterCore;
        disposables.add(
                eventRegisterCore
                        .observeEvent()
                        .subscribe(event -> {
                            title.onNext(event.title);
                            description.onNext(event.description);
                            prefecture.onNext(event.prefecture);
                        })
        );
    }

    public void dispose() {
        disposables.dispose();
    }

    final public Input input = new Input() {
        @Override
        public void setTitle(String name) {
            eventRegisterCore.setTitle(name);
        }

        @Override
        public void apply() {
            Event event = eventRegisterCore.getEvent();
            if (!event.title.isEmpty() &&
                    !event.description.isEmpty() &&
                    event.prefecture.id > 0) {
                completed.onNext(true);
                eventRegisterCore.moveStepTo(EventRegisterStep.FINISH);
            } else {
                error.onNext("Not enough");
            }
        }

        @Override
        public void gotoDescriptionForm() {
            eventRegisterCore.moveStepTo(EventRegisterStep.ENTER_DESCRIPTION);
        }

        @Override
        public void gotoPrefectureSelection() {
            eventRegisterCore.moveStepTo(EventRegisterStep.SELECT_PREFECTURE);
        }
    };

    final public Output output = new Output() {
        @Override
        public Flowable<String> title() {
            return title.hide().distinctUntilChanged();
        }

        @Override
        public Flowable<String> description() {
            return description.hide().distinctUntilChanged();
        }

        @Override
        public Flowable<Prefecture> prefecture() {
            return prefecture.hide().distinctUntilChanged();
        }

        @Override
        public Flowable<Boolean> completed() {
            return completed.hide();
        }

        @Override
        public Flowable<String> error() {
            return error.hide();
        }
    };
}
