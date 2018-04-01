package com.exercise.template.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.template.R;
import com.exercise.template.api.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File Created by pandu on 01/04/18.
 */
public class StepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<Step> data;

    public StepAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<Step> data) {
        this.data.clear();
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        View view = from.inflate(R.layout.list_item_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Step step = data.get(position);
        ViewHolder viewHolder = ((ViewHolder) holder);

        viewHolder.tvTitle.setText(context.getString(R.string.info_short_step_desc,
                position + 1,
                step.getShortDescription()));

        String videoURL = step.getVideoURL();

        if(!videoURL.isEmpty()) viewHolder.imgPlay.setVisibility(View.VISIBLE);
        else viewHolder.imgPlay.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.img_play)
        ImageView imgPlay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
