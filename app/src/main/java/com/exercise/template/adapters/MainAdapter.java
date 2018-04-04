package com.exercise.template.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exercise.template.R;
import com.exercise.template.api.models.Ingredient;
import com.exercise.template.api.models.Recipe;
import com.exercise.template.api.models.Step;
import com.exercise.template.db.RecipeContract;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * File Created by pandu on 31/03/18.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<Recipe> data;

    private Cursor cursor;

    private RecipeListener recipeListener;

    private boolean isUsingCursor;

    private Gson gson;

    public MainAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
        isUsingCursor = false;
        gson = new Gson();
    }

    public void setData(List<Recipe> data){
        this.data = data;
        isUsingCursor = false;
        notifyDataSetChanged();
    }

    public void setCursor(Cursor c){
        cursor = c;
        isUsingCursor = true;
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
        ViewHolder viewHolder = (ViewHolder) holder;

        if(isUsingCursor){
            cursor.moveToPosition(position);
            bindCursor(viewHolder);
        }
        else{
            bindData(viewHolder, data.get(position));
        }
    }

    private void bindCursor(ViewHolder viewHolder){
        viewHolder.tvTitle.setText(cursor.getString(RecipeContract.COL_NUM_NAME));
        viewHolder.tvIngredient.setText(context.getString(R.string.small_info_ingredient, cursor.getInt(RecipeContract.COL_NUM_INGREDIENTS_SIZE)));
        viewHolder.tvServing.setText(context.getString(R.string.small_info_serving, cursor.getInt(RecipeContract.COL_NUM_SERVINGS)));
        viewHolder.tvStep.setText(context.getString(R.string.small_info_step, cursor.getInt(RecipeContract.COL_NUM_STEPS_SIZE)));

    }

    private void bindData(ViewHolder viewHolder, Recipe recipe){
        viewHolder.tvTitle.setText(recipe.getName());
        viewHolder.tvIngredient.setText(context.getString(R.string.small_info_ingredient, recipe.getIngredients().size()));
        viewHolder.tvServing.setText(context.getString(R.string.small_info_serving, recipe.getServings()));
        viewHolder.tvStep.setText(context.getString(R.string.small_info_step, recipe.getSteps().size()));
    }

    @Override
    public int getItemCount() {
        return isUsingCursor ? cursor.getCount() : data.size();
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
            Recipe selectedRecipe;
            if(isUsingCursor){
                selectedRecipe = new Recipe();
                selectedRecipe.setId(cursor.getString(RecipeContract.COL_NUM_ID));
                selectedRecipe.setName(cursor.getString(RecipeContract.COL_NUM_NAME));
                selectedRecipe.setServings(cursor.getInt(RecipeContract.COL_NUM_SERVINGS));
                selectedRecipe.setImage(cursor.getString(RecipeContract.COL_NUM_IMAGE));

                Type ingredientType = new TypeToken<List<Ingredient>>(){}.getType();
                List<Ingredient> dtIngredient = gson.fromJson(cursor.getString(RecipeContract.COL_NUM_INGREDIENTS), ingredientType);

                Type stepType = new TypeToken<List<Step>>(){}.getType();
                List<Step> dtStep = gson.fromJson(cursor.getString(RecipeContract.COL_NUM_STEPS), stepType);

                selectedRecipe.setIngredients(dtIngredient);
                selectedRecipe.setSteps(dtStep);
            }
            else{
                selectedRecipe = data.get(getAdapterPosition());
            }

            recipeListener.onRecipeClick(selectedRecipe);
        }
    }

    public interface RecipeListener{
        void onRecipeClick(Recipe selectedRecipe);
    }
}
