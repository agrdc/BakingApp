package com.example.android.bakingapp.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipesDetailAdapter;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsitec207.neto on 13/11/17.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipesDetailAdapter.RecipesDetailAdapterOnClickHandler {

    private Recipe mRecipe;
    private static final String TAG_RECIPE_DETAIL_FRAGMENT = "recipe-detail-fragment";
    private static final String TAG_RECIPE_DETAIL_STEP_FRAGMENT = "recipe-detail-step-fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        if (savedInstanceState == null) {
            mRecipe = getIntent().getExtras().getParcelable(RecipesActivity.KEY_RECIPE_DETAIL_EXTRA);
        } else {
            mRecipe = savedInstanceState.getParcelable(RecipesActivity.KEY_RECIPES_BUNDLE);
        }
        if (mRecipe != null) {
            String recipeName = mRecipe.getName();
            setTitle(recipeName);
            if (getSupportFragmentManager().findFragmentByTag(TAG_RECIPE_DETAIL_FRAGMENT) == null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(RecipesActivity.KEY_RECIPE_DETAIL_EXTRA, mRecipe);
                RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
                recipeDetailFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, recipeDetailFragment, TAG_RECIPE_DETAIL_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mRecipe != null) ;
        outState.putParcelable(RecipesActivity.KEY_RECIPES_BUNDLE, mRecipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepClick(ArrayList<Step> stepsList, int stepIndex) {
        if (getSupportFragmentManager().findFragmentByTag(TAG_RECIPE_DETAIL_STEP_FRAGMENT) == null && mRecipe != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RecipesActivity.KEY_STEP_LIST_DETAIL_EXTRA, (ArrayList<Step>) mRecipe.getStepList());
            RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
            recipeStepDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, recipeStepDetailFragment, TAG_RECIPE_DETAIL_STEP_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
