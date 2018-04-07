package com.exercise.template.views.activities.detail.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exercise.template.R;
import com.exercise.template.api.models.Step;
import com.exercise.template.views.activities.detail.viewmodels.DetailViewModel;
import com.exercise.template.views.base.BaseFragment;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * File Created by pandu on 07/04/18.
 */
public class SlidingTheaterFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    Unbinder unbinder;

    DetailViewModel detailViewModel;

    private ViewPagerAdapter pagerAdapter;

    private SimpleExoPlayer simpleExoPlayer;

    public static SlidingTheaterFragment newInstance() {

        Bundle args = new Bundle();

        SlidingTheaterFragment fragment = new SlidingTheaterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding_theater, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        pagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        detailViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);
        detailViewModel.getRecipe().observe(this, recipe -> {
            if(recipe != null) {
                toolbar.setTitle(recipe.getName());
                pagerAdapter.setData(recipe.getSteps());
            }
        });

        detailViewModel.getSelectedStepPos().observe(this, pos -> {
            if (pos != null && pos < pagerAdapter.getCount()) {
                viewPager.setCurrentItem(pos);
            }
        });
    }

    @Override
    public void onPause() {
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class ViewPagerAdapter extends PagerAdapter{

        private List<Step> dtStep = new ArrayList<>();

        public ViewPagerAdapter() {

        }

        public void setData(List<Step> d){
            dtStep = new ArrayList<>();
            dtStep.addAll(d);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.fragment_theater, container, false);

            View toolbar = view.findViewById(R.id.toolbar);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvDesc = view.findViewById(R.id.tv_desc);
            View emptyState = view.findViewById(R.id.video_player_empty);
            SimpleExoPlayerView videoPlayer = view.findViewById(R.id.video_player);

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

            toolbar.setVisibility(View.GONE);

            Step step = dtStep.get(position);
            tvTitle.setText(step.getShortDescription());
            tvDesc.setText(step.getDescription());

            videoPlayer.setPlayer(simpleExoPlayer);
            videoPlayer.setUseController(true);
            videoPlayer.requestFocus();

            simpleExoPlayer.stop();

            tvTitle.setText(step.getShortDescription());
            tvDesc.setText(step.getDescription());

            if (!step.getVideoURL().isEmpty()) {
                videoPlayer.setVisibility(View.VISIBLE);
                emptyState.setVisibility(View.INVISIBLE);

                Handler mainHandler = new Handler();
                Uri uri = Uri.parse(step.getVideoURL());
                DataSource.Factory dataSourceFactory =
                        new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(),
                                "BakingApp"));

                ExtractorMediaSource.Factory factory = new ExtractorMediaSource.Factory(dataSourceFactory);
                MediaSource mediaSource = factory.createMediaSource(uri, mainHandler, null);

                simpleExoPlayer.setPlayWhenReady(false);
                simpleExoPlayer.prepare(mediaSource);
            }
            else{
                videoPlayer.setVisibility(View.GONE);
                emptyState.setVisibility(View.VISIBLE);
            }

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return dtStep.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            if(position < dtStep.size() - 1) return String.valueOf(position + 1);
            else return "Final";
        }
    }
}
