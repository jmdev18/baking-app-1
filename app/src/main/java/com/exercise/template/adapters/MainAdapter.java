package com.exercise.template.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private Cursor cursor;

    private RecipeListener recipeListener;

    private Gson gson;

    public MainAdapter(Context context) {
        this.context = context;
        gson = new Gson();
    }

    public void setCursor(Cursor c){
        cursor = c;
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

        if(!cursor.isClosed())cursor.moveToPosition(position);
        bindCursor(viewHolder);
    }

    private void bindCursor(ViewHolder viewHolder){
        if(cursor != null && !cursor.isClosed()) {
            viewHolder.tvTitle.setText(cursor.getString(RecipeContract.COL_NUM_NAME));
            viewHolder.tvIngredient.setText(context.getString(R.string.small_info_ingredient, cursor.getInt(RecipeContract.COL_NUM_INGREDIENTS_SIZE)));
            viewHolder.tvServing.setText(context.getString(R.string.small_info_serving, cursor.getInt(RecipeContract.COL_NUM_SERVINGS)));
            viewHolder.tvStep.setText(context.getString(R.string.small_info_step, cursor.getInt(RecipeContract.COL_NUM_STEPS_SIZE)));

            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_favorite_border);
            if (cursor.getInt(RecipeContract.COL_NUM_DESIRED) == 1) {
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_favorite);
            }

            viewHolder.imgFavourite.setImageDrawable(
                    drawable);
        }
    }

    private void bindData(ViewHolder viewHolder, Recipe recipe){
        viewHolder.tvTitle.setText(recipe.getName());
        viewHolder.tvIngredient.setText(context.getString(R.string.small_info_ingredient, recipe.getIngredients().size()));
        viewHolder.tvServing.setText(context.getString(R.string.small_info_serving, recipe.getServings()));
        viewHolder.tvStep.setText(context.getString(R.string.small_info_step, recipe.getSteps().size()));
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.root_view)
        CardView rootView;

        @BindView(R.id.iv_favourite)
        ImageView imgFavourite;

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
            rootView.setOnClickListener(this);
            imgFavourite.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.iv_favourite){
                cursor.moveToPosition(getAdapterPosition());
                recipeListener.onFavouriteClick(
                        cursor.getString(RecipeContract.COL_NUM_RECIPE_ID),
                        cursor.getString(RecipeContract.COL_NUM_NAME)
                );
            }
            else {
                Recipe selectedRecipe;
                cursor.moveToPosition(getAdapterPosition());

                selectedRecipe = new Recipe();
                selectedRecipe.setId(cursor.getString(RecipeContract.COL_NUM_RECIPE_ID));
                selectedRecipe.setName(cursor.getString(RecipeContract.COL_NUM_NAME));
                selectedRecipe.setServings(cursor.getInt(RecipeContract.COL_NUM_SERVINGS));
                selectedRecipe.setImage(cursor.getString(RecipeContract.COL_NUM_IMAGE));

                Type ingredientType = new TypeToken<List<Ingredient>>() {
                }.getType();
                List<Ingredient> dtIngredient = gson.fromJson(cursor.getString(RecipeContract.COL_NUM_INGREDIENTS), ingredientType);

                Type stepType = new TypeToken<List<Step>>() {
                }.getType();
                List<Step> dtStep = gson.fromJson(cursor.getString(RecipeContract.COL_NUM_STEPS), stepType);

                selectedRecipe.setIngredients(dtIngredient);
                selectedRecipe.setSteps(dtStep);

                recipeListener.onRecipeClick(selectedRecipe);
            }
        }
    }

    public interface RecipeListener{
        void onRecipeClick(Recipe selectedRecipe);
        void onFavouriteClick(String recipeId, String recipeName);
    }
}
