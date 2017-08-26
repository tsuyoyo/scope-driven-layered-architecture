package test.dependencies.tsuyoyo;


import android.content.Context;

import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterGraph;

public class FeatureScopeComponentManager {

    private Context appContext;

    private EventRegisterGraph eventRegisterComponents;

    FeatureScopeComponentManager(Context appContext) {
        this.appContext = appContext;
    }

    public EventRegisterGraph eventRegister() {
        if (eventRegisterComponents == null) {
            eventRegisterComponents = new EventRegisterGraph(appContext);
        }
        return eventRegisterComponents;
    }

    public void releaseEventRegisterComponents() {
        eventRegisterComponents = null;
    }

}
