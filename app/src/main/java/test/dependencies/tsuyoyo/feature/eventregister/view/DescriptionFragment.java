package test.dependencies.tsuyoyo.feature.eventregister.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import test.dependencies.tsuyoyo.MyApplication;
import test.dependencies.tsuyoyo.feature.eventregister.step.DescriptionViewModel;
import test.dependencies.tsuyoyo.ui.R;

public class DescriptionFragment extends Fragment {

    static DescriptionFragment createInstance() {
        return new DescriptionFragment();
    }

    @Inject
    DescriptionViewModel viewModel;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.featureScopeComponents(getContext())
                .eventRegister()
                .viewModelComponents()
                .descriptionComponent()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        view.findViewById(R.id.apply).setOnClickListener(v -> {
            String d = ((EditText) view.findViewById(R.id.description)).getText().toString();
            viewModel.input.apply(d);
        });

        view.setFocusableInTouchMode(true);
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                viewModel.input.cancel();
                return true;
            }
            return false;
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        disposables.addAll(
                viewModel.output.complete()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(signal -> {
                            // Exit @EventRegisterStep scope
                            MyApplication.featureScopeComponents(getContext())
                                    .eventRegister()
                                    .viewModelComponents()
                                    .releaseDescriptionComponent();
                        })
                        .subscribe()
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
