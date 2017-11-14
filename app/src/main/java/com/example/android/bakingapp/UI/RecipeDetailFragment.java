package com.example.android.bakingapp.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipesDetailAdapter;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import java.util.List;

/**
 * Created by lsitec207.neto on 14/11/17.
 */

public class RecipeDetailFragment extends Fragment implements RecipesDetailAdapter.RecipesDetailAdapterOnClickHandler {

    private static final String LOG_TAG = RecipeDetailFragment.class.getSimpleName();
    private RecipesDetailAdapter mAdapter;
    private Recipe mRecipe = null;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        TextView ingredientsTextView = (TextView) rootView.findViewById(R.id.tv_detail_recipe_ingredients);
        RecyclerView recyclerViewSteps = (RecyclerView) rootView.findViewById(R.id.rv_detail_steps);

        mAdapter = new RecipesDetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSteps.setAdapter(mAdapter);
        recyclerViewSteps.setLayoutManager(linearLayoutManager);

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RecipesActivity.KEY_RECIPE_DETAIL_EXTRA);
        } else {
            mRecipe = getArguments().getParcelable(RecipesActivity.KEY_RECIPE_DETAIL_EXTRA);
        }
        if (mRecipe != null) {
            loadIngredientsIntoUI(mRecipe.getIngredientList(), ingredientsTextView);
            loadStepsIntoUI(mRecipe.getStepList());
        }
        return rootView;
    }

    private void loadStepsIntoUI(List<Step> stepsList) {
        if (stepsList != null) {
            mAdapter.setStepsData(stepsList);
        }
    }

    private void loadIngredientsIntoUI(List<Ingredient> ingredientsList, TextView ingredientsTextView) {
        if (ingredientsList != null)
            for (int x=0 ; x<ingredientsList.size()  ; x++) {
                Ingredient i = ingredientsList.get(x);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (x==ingredientsList.size()-1) {
                        ingredientsTextView.append(Html.fromHtml("-<b>" + i.getName() +
                                "</b>" + "<br>" + "    Quantity: " + i.getQuantity() + " " + i.getMeasure(), 0));
                    } else {
                        ingredientsTextView.append(Html.fromHtml("-<b>" + i.getName() +
                                "</b>" + "<br>" + "    Quantity: " + i.getQuantity() + " " + i.getMeasure() + "<br>", 0));
                    }
                } else {
                    if (x==ingredientsList.size()-1) {
                        ingredientsTextView.append(Html.fromHtml("-<b>" + i.getName() +
                                "</b>" + "<br>" + "    Quantity: " + i.getQuantity() + " " + i.getMeasure()));
                    } else {
                        ingredientsTextView.append(Html.fromHtml("-<b>" + i.getName() +
                                "</b>" + "<br>" + "    Quantity: " + i.getQuantity() + " " + i.getMeasure() + "<br>"));
                    }
                }
            }
    }

    @Override
    public void onRecipeClick(Step step) {
        
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RecipesActivity.KEY_RECIPE_DETAIL_EXTRA,mRecipe);
        super.onSaveInstanceState(outState);
    }
}