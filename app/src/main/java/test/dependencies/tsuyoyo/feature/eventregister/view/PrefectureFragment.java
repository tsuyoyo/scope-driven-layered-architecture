package test.dependencies.tsuyoyo.feature.eventregister.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import test.dependencies.tsuyoyo.MyApplication;
import test.dependencies.tsuyoyo.feature.eventregister.step.PrefectureViewModel;
import test.dependencies.tsuyoyo.feature.model.Prefecture;
import test.dependencies.tsuyoyo.ui.R;

public class PrefectureFragment extends Fragment
        implements PrefectureAdapter.PrefectureAdapterListener {

    static PrefectureFragment createInstance() {
        return new PrefectureFragment();
    }

    private RecyclerView prefecturesList;
    private PrefectureAdapter prefectureAdapter;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    PrefectureViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.featureScopeComponents(getContext())
                .eventRegister()
                .viewModelComponents()
                .prefectureComponent()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_prefecture, container, false);
        prefecturesList = view.findViewById(R.id.prefectures);
        prefectureAdapter = new PrefectureAdapter(this);
        prefecturesList.setAdapter(prefectureAdapter);
        prefecturesList.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        disposables.addAll(
                viewModel.output.prefectures()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(p -> {
                            prefectureAdapter.setPrefectures(p);
                            prefectureAdapter.notifyDataSetChanged();
                        })
                        .subscribe(),

                viewModel.output.complete()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(signal -> {
                            // Exit @EventRegisterStep scope
                            MyApplication.featureScopeComponents(getContext())
                                    .eventRegister()
                                    .viewModelComponents()
                                    .releasePrefectureComponent();
                        })
                        .subscribe()
        );
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }

    @Override
    public void onPrefectureSelected(Prefecture prefecture) {
        viewModel.input.apply(prefecture);
    }
}
