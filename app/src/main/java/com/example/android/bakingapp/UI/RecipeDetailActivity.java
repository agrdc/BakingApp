package com.example.android.bakingapp.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipesDetailAdapter;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsitec207.neto on 13/11/17.
 */

public class RecipeDetailActivity extends AppCompatActivity implements RecipesDetailAdapter.RecipesDetailAdapterOnClickHandler {

    private String LOG_TAG = RecipeDetailActivity.class.getSimpleName();
    private Recipe mRecipe;
    private static final String TAG_RECIPE_DETAIL_FRAGMENT = "recipe-detail-fragment";
    public static final String TAG_RECIPE_DETAIL_STEP_FRAGMENT = "recipe-detail-step-fragment";
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        isTablet = getResources().getBoolean(R.bool.isTablet);
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
            if (isTablet) {
                if (getSupportFragmentManager().findFragmentByTag(TAG_RECIPE_DETAIL_STEP_FRAGMENT) == null && mRecipe != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(RecipesActivity.KEY_STEP_LIST_DETAIL_EXTRA, (ArrayList<Step>) mRecipe.getStepList());
                    bundle.putInt(RecipesActivity.KEY_STEP_INDEX_DETAIL_EXTRA, 0);
                    RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                    recipeStepDetailFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.right_container, recipeStepDetailFragment, TAG_RECIPE_DETAIL_STEP_FRAGMENT)
                            .commit();
                }
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
        if (mRecipe != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(RecipesActivity.KEY_STEP_LIST_DETAIL_EXTRA, stepsList);
            bundle.putInt(RecipesActivity.KEY_STEP_INDEX_DETAIL_EXTRA, stepIndex);
            int containerId = R.id.container;
            if (isTablet) {
                containerId = R.id.right_container;
                RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                recipeStepDetailFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(containerId, recipeStepDetailFragment, TAG_RECIPE_DETAIL_STEP_FRAGMENT)
                        .commit();
                return;
            }
            if (getSupportFragmentManager().findFragmentByTag(TAG_RECIPE_DETAIL_STEP_FRAGMENT) == null) {
                RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                recipeStepDetailFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(containerId, recipeStepDetailFragment, TAG_RECIPE_DETAIL_STEP_FRAGMENT)
                        .addToBackStack("recipe")
                        .commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
        }
        super.onBackPressed();
    }
}
