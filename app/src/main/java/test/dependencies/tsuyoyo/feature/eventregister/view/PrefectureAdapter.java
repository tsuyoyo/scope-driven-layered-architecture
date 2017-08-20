package test.dependencies.tsuyoyo.feature.eventregister.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.dependencies.tsuyoyo.feature.model.Prefecture;
import test.dependencies.tsuyoyo.ui.R;

public class PrefectureAdapter extends RecyclerView.Adapter<PrefectureAdapter.PrefectureViewHolder> {

    final private List<Prefecture> prefectures = new ArrayList<>();
    final private PrefectureAdapterListener listener;

    public interface PrefectureAdapterListener {
        void onPrefectureSelected(Prefecture prefecture);
    }

    public PrefectureAdapter(PrefectureAdapterListener listener) {
        this.listener = listener;
    }

    public void setPrefectures(List<Prefecture> prefectures) {
        this.prefectures.clear();
        this.prefectures.addAll(prefectures);
    }

    @Override
    public PrefectureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PrefectureViewHolder viewHolder = new PrefectureViewHolder(
                inflater.inflate(R.layout.item_prefecture, parent, false));
        viewHolder.itemView.setOnClickListener(v ->
                listener.onPrefectureSelected(viewHolder.prefecture));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PrefectureViewHolder holder, int position) {
        holder.set(prefectures.get(position));
    }

    @Override
    public int getItemCount() {
        return prefectures.size();
    }

    public static class PrefectureViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private Prefecture prefecture;

        PrefectureViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
        }

        void set(Prefecture prefecture) {
            this.prefecture = prefecture;
            name.setText(prefecture.name);
        }
    }
}
