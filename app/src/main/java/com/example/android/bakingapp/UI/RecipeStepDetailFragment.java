package com.example.android.bakingapp.UI;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.utils.ToastUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lsitec207.neto on 14/11/17.
 */

public class RecipeStepDetailFragment extends Fragment {

    private static String LOG_TAG = RecipeStepDetailFragment.class.getSimpleName();
    public static String KEY_STEP_LIST_DETAIL_BUNDLE = "key-step_list_detail-bundle";
    public static String KEY_STEP_INDEX_DETAIL_BUNDLE = "key-step_index_detail-bundle";
    public static String KEY_RECIPE_NAME_DETAIL_BUNDLE = "key-recipe_name_detail-bundle";
    private ArrayList<Step> mStepList;
    private int mStepListIndex;
    private Step mStep;
    private SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private String mVideoURL;
    private ViewGroup mContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail_step, container, false);

        mContainer = container;
        TextView stepDescriptionTextView = (TextView) rootView.findViewById(R.id.tv_step_description);
        mExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exoplayer_view);

        if (savedInstanceState != null) {
            mStepListIndex = savedInstanceState.getInt(KEY_STEP_INDEX_DETAIL_BUNDLE);
            mStepList = savedInstanceState.getParcelableArrayList(KEY_STEP_LIST_DETAIL_BUNDLE);
        } else {
            mStepListIndex = getArguments().getInt(RecipesActivity.KEY_STEP_INDEX_DETAIL_EXTRA);
            mStepList = getArguments().getParcelableArrayList(RecipesActivity.KEY_STEP_LIST_DETAIL_EXTRA);
        }
        if (mStepList != null) {
            mStep = mStepList.get(mStepListIndex);
            stepDescriptionTextView.setText(mStep.getDescription());
            String thumbnailURL = mStep.getThumbnailURLString();
            if (thumbnailURL != null && !thumbnailURL.equals("")) {
                Uri thumbnailUri = Uri.parse(thumbnailURL).buildUpon().build();
                Bitmap thumbnail = null;
                try {
                    thumbnail = Picasso.with(getActivity()).load(thumbnailUri).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mExoPlayerView.setDefaultArtwork(thumbnail);
            }
            mVideoURL = mStep.getVideoURLString();
            Log.d(LOG_TAG, "video url =" + mVideoURL);
            if (mVideoURL == null || mVideoURL.isEmpty()) {
                mExoPlayerView.setVisibility(View.GONE);
                TextView noVideoAvailableTextView = (TextView) rootView.findViewById(R.id.tv_no_video_available);
                noVideoAvailableTextView.setVisibility(View.VISIBLE);
            }
            Button previousStepButton = (Button) rootView.findViewById(R.id.btn_previous_step);
            Button nextStepButton = (Button) rootView.findViewById(R.id.btn_next_step);
            Button recipeButton = (Button) rootView.findViewById(R.id.btn_return_recipe);
            previousStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mStepListIndex > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(RecipesActivity.KEY_STEP_LIST_DETAIL_EXTRA, mStepList);
                        bundle.putInt(RecipesActivity.KEY_STEP_INDEX_DETAIL_EXTRA,mStepListIndex-1);
                        RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                        recipeStepDetailFragment.setArguments(bundle);
                        Log.d(LOG_TAG,"teste"+recipeStepDetailFragment.getId());
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, recipeStepDetailFragment, RecipeDetailActivity.TAG_RECIPE_DETAIL_STEP_FRAGMENT )
                                .addToBackStack(null)
                                .commit();
                    } else {
                        ToastUtils.createToast(getActivity(), "You're already on the first step.", Toast.LENGTH_SHORT);
                    }
                }
            });

            nextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mStepListIndex < mStepList.size()-1) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(RecipesActivity.KEY_STEP_LIST_DETAIL_EXTRA, mStepList);
                        bundle.putInt(RecipesActivity.KEY_STEP_INDEX_DETAIL_EXTRA,mStepListIndex+1);
                        RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
                        recipeStepDetailFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, recipeStepDetailFragment, RecipeDetailActivity.TAG_RECIPE_DETAIL_STEP_FRAGMENT )
                                .addToBackStack(null)
                                .commit();
                    } else {
                        ToastUtils.createToast(getActivity(), "You're already on the last step.", Toast.LENGTH_SHORT);
                    }
                }
            });

            recipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().popBackStack("recipe", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            });
        }
        return rootView;
    }

    public void initializePlayer() {
        if (mExoPlayer == null && mVideoURL != null && !mVideoURL.isEmpty()) {
            Uri videoUri = Uri.parse(mVideoURL).buildUpon().build();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mExoPlayerView.setPlayer(mExoPlayer);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), "BakingApp"), new DefaultBandwidthMeter());
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource videoSource = new ExtractorMediaSource(videoUri,
                    dataSourceFactory, extractorsFactory, null, null);
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);
            mExoPlayerView.hideController();
        }
    }

    @Override
    public void onResume() {
        initializePlayer();
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mStepList != null) {
            outState.putParcelableArrayList(KEY_STEP_LIST_DETAIL_BUNDLE, mStepList);
            outState.putInt(KEY_STEP_INDEX_DETAIL_BUNDLE, mStepListIndex);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


}
