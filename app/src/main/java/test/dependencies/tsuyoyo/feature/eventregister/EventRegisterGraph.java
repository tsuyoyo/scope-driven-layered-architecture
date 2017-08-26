package test.dependencies.tsuyoyo.feature.eventregister;

import android.content.Context;

import test.dependencies.tsuyoyo.MyApplication;
import test.dependencies.tsuyoyo.feature.eventregister.step.DaggerDescriptionComponent;
import test.dependencies.tsuyoyo.feature.eventregister.step.DaggerEventRegisterTopComponent;
import test.dependencies.tsuyoyo.feature.eventregister.step.DaggerPrefectureComponent;
import test.dependencies.tsuyoyo.feature.eventregister.step.DescriptionComponent;
import test.dependencies.tsuyoyo.feature.eventregister.step.EventRegisterTopComponent;
import test.dependencies.tsuyoyo.feature.eventregister.step.PrefectureComponent;

public class EventRegisterGraph {

    private final EventRegisterComponent eventRegisterComponent;

    private final ViewModelComponentsHolder viewModelComponents;

    public EventRegisterGraph(Context context) {
        eventRegisterComponent = DaggerEventRegisterComponent.builder()
                .appComponent(MyApplication.appComponent(context))
                .build();
        viewModelComponents = new ViewModelComponentsHolder(eventRegisterComponent);
    }

    public EventRegisterComponent eventRegisterComponent() {
        return eventRegisterComponent;
    }

    public ViewModelComponentsHolder viewModelComponents() {
        return viewModelComponents;
    }

    public static class ViewModelComponentsHolder {

        private final EventRegisterComponent eventRegisterComponent;

        private EventRegisterTopComponent eventRegisterTopComponent;
        private DescriptionComponent descriptionComponent;
        private PrefectureComponent prefectureComponent;

        private ViewModelComponentsHolder(EventRegisterComponent eventRegisterComponent) {
            this.eventRegisterComponent = eventRegisterComponent;
        }

        // Top view
        public EventRegisterTopComponent eventRegisterTopComponent() {
            if (eventRegisterTopComponent == null) {
                eventRegisterTopComponent = DaggerEventRegisterTopComponent.builder()
                        .eventRegisterComponent(eventRegisterComponent)
                        .build();
            }
            return eventRegisterTopComponent;
        }

        public void releaseEventRegisterTopComponent() {
            eventRegisterTopComponent = null;
        }

        // Description view
        public DescriptionComponent descriptionComponent() {
            if (descriptionComponent == null) {
                descriptionComponent = DaggerDescriptionComponent.builder()
                        .eventRegisterComponent(eventRegisterComponent)
                        .build();
            }
            return descriptionComponent;
        }

        public void releaseDescriptionComponent() {
            descriptionComponent = null;
        }

        // Prefecture view
        public PrefectureComponent prefectureComponent() {
            if (prefectureComponent == null) {
                prefectureComponent = DaggerPrefectureComponent.builder()
                        .eventRegisterComponent(eventRegisterComponent)
                        .build();
            }
            return prefectureComponent;
        }

        public void releasePrefectureComponent() {
            prefectureComponent = null;
        }
    }

}
