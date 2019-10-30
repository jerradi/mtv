package com.jamaal.exoplayer2example;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Surface;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;



/*
Created by: Ayal Fieldust
Date: 8/2017

Description:
This Example app was created to show a simple example of ExoPlayer Version 2.8.4.
There is an option to play mp4 files or live stream content.
Exoplayer provides options to play many different formats, so the code can easily be tweaked to play the requested format.
Scroll down to "ADJUST HERE:" I & II to change between sources.
Keep in mind that m3u8 files might be stale and you would need new sources.
 */

public class PlayerActivity extends AppCompatActivity implements VideoRendererEventListener {


    private static final String TAG = "PlayerActivity";
    private PlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private TextView resolutionTextView;
    private DataSource.Factory dataSourceFactory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        resolutionTextView = new TextView(this);
        resolutionTextView = (TextView) findViewById(R.id.resolution_textView);

        String mp4VideoUri = getIntent().getStringExtra("url");

        if(isYtbLink(mp4VideoUri)){
            @SuppressLint("StaticFieldLeak") YouTubeExtractor mExtractor = new YouTubeExtractor(this) {
                @Override
                protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
                    if (sparseArray != null) {
                        YtFile x = null ;
                        for(int i =0; i<sparseArray.size();i++){
                            x= sparseArray.valueAt(i);
                            at.huber.youtubeExtractor.Format f = x.getFormat();
                            if(f.getExt().equals("mp4") && f.getHeight()>80 && f.getAudioBitrate()>0  ) {
                                break;
                            }
                        }

                        String u = x.getUrl();
                        playVideo(u);
                    }
                }
            };
            mExtractor.extract(mp4VideoUri, true, true);
        }
        else
            playVideo(mp4VideoUri);





    }

    private   boolean isYtbLink(String mp4VideoUri) {
        //String pattern = ^(http\:\/\/)?(www\.)?(youtube\.com|youtu\.?be)\/.+$;
        String pattern = "^(http(s)?\\:\\/\\/)?(www\\.)?(youtube\\.com|youtu\\.?be)\\/.+$";
        return  mp4VideoUri.matches(pattern);


    }
      LoopingMediaSource loopingSource = null;
    private void playVideo(String mp4VideoUri) {
        Uri mp4Uri = Uri.parse(getIntent().getStringExtra("url")); //CNBC
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(); //test

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        // simpleExoPlayerView = new SimpleExoPlayerView(this);
        simpleExoPlayerView = (PlayerView) findViewById(R.id.player_view);

        int h = simpleExoPlayerView.getResources().getConfiguration().screenHeightDp;
        int w = simpleExoPlayerView.getResources().getConfiguration().screenWidthDp;
        Log.v(TAG, "height : " + h + " weight: " + w);
        ////Set media controller
       // simpleExoPlayerView.setUseController(false);//set to true or false to see controllers
        simpleExoPlayerView.requestFocus();
        // Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);

        dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), bandwidthMeter);

        MediaSource videoSource  = null;


        player.addListener(new ExoPlayer.EventListener() {


            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.v(TAG, "Listener-onTracksChanged... ");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState + "|||isDrawingCacheEnabled():" + simpleExoPlayerView.isDrawingCacheEnabled());
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG, "Listener-onPlayerError...");
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        player.setPlayWhenReady(true); //run file/link when ready to play.
        player.setVideoDebugListener(this);




        Uri mp4VideoUrii = Uri.parse(mp4VideoUri);
        if (mp4VideoUri.toUpperCase().contains("M3U8")) {
            videoSource = new HlsMediaSource(mp4VideoUrii, dataSourceFactory, null, null);
        } else {

            videoSource = new ExtractorMediaSource(mp4VideoUrii, dataSourceFactory, new DefaultExtractorsFactory(),
                    null, null);
        }

            loopingSource = new LoopingMediaSource(videoSource);
        // Prepare the player with the source.
        player.prepare(videoSource);
    }


    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {

    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        Log.v(TAG, "onVideoSizeChanged [" + " width: " + width + " height: " + height + "]");
        resolutionTextView.setText("RES:(WxH):" + width + "X" + height + "\n           " + height + "p");//shows video info
    }

    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }
//-------------------------------------------------------ANDROID LIFECYCLE---------------------------------------------------------------------------------------------

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop()...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()...");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()...");
        player.release();
    }


}
