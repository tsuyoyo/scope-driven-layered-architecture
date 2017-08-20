package test.dependencies.tsuyoyo;

import android.app.Application;
import android.content.Context;

import test.dependencies.tsuyoyo.app.AppComponent;
import test.dependencies.tsuyoyo.app.DaggerAppComponent;

public class MyApplication extends Application {

    private AppComponent appComponent;

    private FeatureScopeComponentManager featureScopeComponents;

    public static AppComponent appComponent(Context context) {
        return ((MyApplication) context.getApplicationContext()).appComponent;
    }

    public static FeatureScopeComponentManager featureScopeComponents(Context context) {
        return ((MyApplication) context.getApplicationContext()).featureScopeComponents;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().build();
        featureScopeComponents = new FeatureScopeComponentManager(this);
    }
}
