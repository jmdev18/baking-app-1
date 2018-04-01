package com.exercise.template.views.activities.detail.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exercise.template.R;
import com.exercise.template.views.activities.detail.viewmodels.DetailViewModel;
import com.exercise.template.views.base.BaseFragment;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * File Created by pandu on 01/04/18.
 */
public class TheaterFragment extends BaseFragment {

    public static final String TAG = TheaterFragment.class.getSimpleName();

    DetailViewModel detailViewModel;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    Unbinder unbinder;
    @BindView(R.id.video_player)
    SimpleExoPlayerView videoPlayer;
    private SimpleExoPlayer simpleExoPlayer;

    public static TheaterFragment newInstance() {

        Bundle args = new Bundle();

        TheaterFragment fragment = new TheaterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theater, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        videoPlayer.setPlayer(simpleExoPlayer);
        videoPlayer.setUseController(true);
        videoPlayer.requestFocus();

        detailViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);

        detailViewModel.getSelectedStep().observe(this, step -> {
            if (step != null) {
                tvDesc.setText(step.getDescription());

                if(!step.getVideoURL().isEmpty()) {
                    Uri uri = Uri.parse(step.getVideoURL());
                    DataSource.Factory dataSourceFactory =
                            new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(),
                                    "BakingApp"));
                    MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, new DefaultExtractorsFactory(),
                            mainHandler, null);

                    simpleExoPlayer.setPlayWhenReady(true);
                    simpleExoPlayer.prepare(mediaSource);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}