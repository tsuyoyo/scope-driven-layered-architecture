package test.dependencies.tsuyoyo;


import android.content.Context;

import test.dependencies.tsuyoyo.feature.eventregister.EventRegisterComponentsManager;

public class FeatureScopeComponentManager {

    private Context appContext;

    private EventRegisterComponentsManager eventRegisterComponents;

    FeatureScopeComponentManager(Context appContext) {
        this.appContext = appContext;
    }

    public EventRegisterComponentsManager eventRegister() {
        if (eventRegisterComponents == null) {
            eventRegisterComponents = new EventRegisterComponentsManager(appContext);
        }
        return eventRegisterComponents;
    }

    public void releaseEventRegisterComponents() {
        eventRegisterComponents = null;
    }

}
