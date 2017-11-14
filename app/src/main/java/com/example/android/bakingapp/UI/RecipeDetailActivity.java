package com.example.android.bakingapp.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;

import java.util.List;

/**
 * Created by lsitec207.neto on 13/11/17.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    private Recipe mRecipe;
    private static final String TAG_RECIPE_DETAIL_FRAGMENT = "recipe-detail-fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        if (savedInstanceState == null) {
            mRecipe = getIntent().getExtras().getParcelable(RecipesActivity.KEY_RECIPE_DETAIL_EXTRA);
        } else {
            mRecipe = savedInstanceState.getParcelable(RecipesActivity.KEY_RECIPE_DETAIL_EXTRA);
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
        outState.putParcelable(RecipesActivity.KEY_RECIPE_DETAIL_EXTRA, mRecipe);
        super.onSaveInstanceState(outState);
    }
}
