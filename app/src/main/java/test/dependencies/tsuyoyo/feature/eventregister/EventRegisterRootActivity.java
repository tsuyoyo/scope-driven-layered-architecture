package test.dependencies.tsuyoyo.feature.eventregister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import test.dependencies.tsuyoyo.MyApplication;
import test.dependencies.tsuyoyo.feature.eventregister.view.DescriptionFragment;
import test.dependencies.tsuyoyo.feature.eventregister.view.EventRegisterFragment;
import test.dependencies.tsuyoyo.feature.eventregister.view.EventRegisterStepView;
import test.dependencies.tsuyoyo.feature.eventregister.view.PrefectureFragment;
import test.dependencies.tsuyoyo.ui.R;

public class EventRegisterRootActivity extends AppCompatActivity {

    private static final String TAG_TOP = "top";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PREFECTURE = "prefecture";

    @Inject
    EventRegisterCore eventRegisterCore;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.featureScopeComponents(this)
                .eventRegister()
                .eventRegisterComponent()
                .inject(this);
        setContentView(R.layout.activity_event_register);

        if (savedInstanceState == null) {
            gotoTop();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        disposables.add(
                eventRegisterCore.observeCurrentStep()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(this::onStepChanged)
                        .subscribe()
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposables.clear();
    }

    @Override
    public void finish() {
        super.finish();

        // Exit from @EventRegister scope
        MyApplication.featureScopeComponents(this).releaseEventRegisterComponents();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        FragmentManager fm = getSupportFragmentManager();
        if (keyCode == KeyEvent.KEYCODE_BACK && fm.getBackStackEntryCount() > 0) {
            String tag = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();
            ((EventRegisterStepView) fm.findFragmentByTag(tag)).onStepCancelled();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onStepChanged(EventRegisterStep step) {
        switch (step) {
            case TOP:
                gotoTop();
                break;
            case ENTER_DESCRIPTION:
                gotoDescriptionForm();
                break;
            case SELECT_PREFECTURE:
                gotoPrefectureSelect();
                break;
            case FINISH:
                finish();
                break;
        }
    }

    private void gotoTop() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
        } else {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragment_area, EventRegisterFragment.createInstance(), TAG_TOP);
            ft.commit();
        }
    }

    private void gotoDescriptionForm() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_DESCRIPTION) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_area, DescriptionFragment.createInstance(), TAG_DESCRIPTION);
            ft.addToBackStack(TAG_DESCRIPTION);
            ft.commit();
        }
    }

    private void gotoPrefectureSelect() {
        if (getSupportFragmentManager().findFragmentByTag(TAG_PREFECTURE) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_area, PrefectureFragment.createInstance(), TAG_PREFECTURE);
            ft.addToBackStack(TAG_PREFECTURE);
            ft.commit();
        }
    }
}
