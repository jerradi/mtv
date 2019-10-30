package com.jamaal.exoplayer2example;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import butterknife.BindAnim;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Main  extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar bigProgressBar;
    @BindView(R.id.progressBar2)
    ProgressBar smallProgressBar;
    @BindView(R.id.imageView)
    ImageView ivLogo;
    @BindAnim(R.anim.blink)
    Animation blinkAnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        bigProgressBar.setProgress(0);
        ivLogo.startAnimation(blinkAnum);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
smallProgressBar.setProgress(0);

                smallProgressBar.setVisibility(View.VISIBLE);
            /*    Intent i = new Intent(PlayerActivity.this, SampleChooserActivity.class);
                startActivity(i);*/

//                Intent intent = new Intent(getApplicationContext(),  VideoVLCActivity.class);
//                intent.putExtra("videoUrl", "rtmp://178.22.187.253:1935/streaming-tv/mp4:myStream3");
//                startActivity(intent);
            }
        }, 100);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

              Intent i = new Intent(Main.this, ListOfItems.class);
                startActivity(i);

//                Intent intent = new Intent(getApplicationContext(),  VideoVLCActivity.class);
//                intent.putExtra("videoUrl", "rtmp://178.22.187.253:1935/streaming-tv/mp4:myStream3");
//                startActivity(intent);
            }
        }, 500);


    }


}
