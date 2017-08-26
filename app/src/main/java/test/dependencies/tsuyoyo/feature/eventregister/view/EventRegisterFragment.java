package test.dependencies.tsuyoyo.feature.eventregister.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import test.dependencies.tsuyoyo.MyApplication;
import test.dependencies.tsuyoyo.feature.eventregister.step.EventRegisterViewModel;
import test.dependencies.tsuyoyo.ui.R;

public class EventRegisterFragment extends Fragment {

    @Inject
    EventRegisterViewModel viewModel;

    private EditText titleForm;
    private TextView title;
    private TextView description;
    private TextView prefecture;

    private CompositeDisposable disposables = new CompositeDisposable();

    static public EventRegisterFragment createInstance() {
        return new EventRegisterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.featureScopeComponents(getContext())
                .eventRegister()
                .viewModelComponents()
                .eventRegisterTopComponent()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_event_register, container, false);
        title = view.findViewById(R.id.title);
        titleForm = view.findViewById(R.id.title_form);
        description = view.findViewById(R.id.description);
        prefecture = view.findViewById(R.id.prefecture);

        view.findViewById(R.id.goto_description)
                .setOnClickListener(v -> viewModel.input.gotoDescriptionForm());

        view.findViewById(R.id.goto_prefecture_select)
                .setOnClickListener(v -> viewModel.input.gotoPrefectureSelection());

        view.findViewById(R.id.apply_title)
                .setOnClickListener(v -> viewModel.input.setTitle(titleForm.getText().toString()));

        view.findViewById(R.id.apply_event_btn)
                .setOnClickListener(v -> viewModel.input.apply());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        disposables.addAll(
                viewModel.output
                        .completed()
                        .doOnNext(isCompleted -> {
                            if (isCompleted) {
                                // Exit @EventRegisterStep scope.
                                viewModel.dispose();
                                MyApplication.featureScopeComponents(getContext())
                                        .eventRegister()
                                        .viewModelComponents()
                                        .releaseEventRegisterTopComponent();
                            }
                        })
                        .subscribe(),

                viewModel.output
                        .error()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(msg ->
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show())
                        .subscribe(),

                viewModel.output
                        .title()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(t -> {
                            titleForm.setText(t);
                            title.setText(t);
                        })
                        .subscribe(),

                viewModel.output
                        .description()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(description::setText)
                        .subscribe(),

                viewModel.output
                        .prefecture()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(p -> prefecture.setText(p.name))
                        .subscribe()
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
