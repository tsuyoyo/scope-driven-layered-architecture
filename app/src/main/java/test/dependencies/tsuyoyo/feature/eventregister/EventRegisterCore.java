package test.dependencies.tsuyoyo.feature.eventregister;

import io.reactivex.Flowable;
import io.reactivex.processors.BehaviorProcessor;
import test.dependencies.tsuyoyo.feature.model.Event;
import test.dependencies.tsuyoyo.feature.model.Prefecture;

public class EventRegisterCore {

    private final BehaviorProcessor<Event> event =
            BehaviorProcessor.createDefault(new Event("", "",  new Prefecture(-1, "no pref")));

    private final BehaviorProcessor<EventRegisterStep> currentStep =
            BehaviorProcessor.createDefault(EventRegisterStep.TOP);

    public Flowable<Event> observeEvent() {
        return event.hide();
    }

    public Event getEvent() {
        return event.getValue();
    }

    public void setTitle(String title) {
        Event current = getEvent();
        event.onNext(new Event(title, current.description, current.prefecture));
    }

    public void setDescription(String description) {
        Event current = getEvent();
        event.onNext(new Event(current.title, description, current.prefecture));
    }

    public void setPrefecture(Prefecture prefecture) {
        Event current = getEvent();
        event.onNext(new Event(current.title, current.description, prefecture));
    }

    public Flowable<EventRegisterStep> observeCurrentStep() {
        return currentStep.hide();
    }

    public void moveStepTo(EventRegisterStep step) {
        currentStep.onNext(step);
    }

}
