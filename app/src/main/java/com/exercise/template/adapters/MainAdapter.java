package com.exercise.template.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exercise.template.R;
import com.exercise.template.api.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * File Created by pandu on 31/03/18.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<Recipe> data;

    private RecipeListener recipeListener;

    public MainAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<Recipe> data){
        this.data.clear();
        this.data = data;
        notifyDataSetChanged();
    }

    public void setRecipeListener(RecipeListener listener){
        this.recipeListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        View view = from.inflate(R.layout.list_item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Recipe recipe = data.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.tvTitle.setText(recipe.getName());
        viewHolder.tvIngredient.setText(context.getString(R.string.small_info_ingredient, recipe.getIngredients().size()));
        viewHolder.tvServing.setText(context.getString(R.string.small_info_serving, recipe.getServings()));
        viewHolder.tvStep.setText(context.getString(R.string.small_info_step, recipe.getSteps().size()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_ingredient)
        TextView tvIngredient;

        @BindView(R.id.tv_step)
        TextView tvStep;

        @BindView(R.id.tv_serving)
        TextView tvServing;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recipeListener.onRecipeClick(data.get(getAdapterPosition()));
        }
    }

    public interface RecipeListener{
        void onRecipeClick(Recipe selectedRecipe);
    }
}
