package com.exercise.template.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    private StepListener stepListener;

    private int selectedPosition = 0;

    private int lastSelectedPosition = 0;

    public StepAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<Step> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setStepListener(StepListener listener){
        this.stepListener = listener;
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

        viewHolder.rootView.setOnClickListener(view -> {
            lastSelectedPosition = selectedPosition;
            stepListener.onStepClick(data.get(position), position);
            selectedPosition = position;

            notifyItemChanged(lastSelectedPosition);
            notifyItemChanged(selectedPosition);
        });

        if(selectedPosition == position){
            viewHolder.rootView.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.selectedColorListItem));
        }
        else{
            viewHolder.rootView.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.colorListItem));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root_view)
        RelativeLayout rootView;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.img_play)
        ImageView imgPlay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface StepListener {
        void onStepClick(Step step, int pos);
    }
}
