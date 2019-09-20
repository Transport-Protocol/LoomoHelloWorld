package de.haw.cads.segway.loomohelloworld;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class LoomoMediaPlayer implements INeedIntegrationInLoomoStateMachine, ILoomoPlay{
    private static final String TAG = "LoomoMediaPlayer";
    private static volatile LoomoMediaPlayer instance;
    private static Object mutex = new Object();
    private static Context context;
    // The media player does the magic
    MediaPlayer player = new MediaPlayer();

    private LoomoMediaPlayer() {
    }

    public static LoomoMediaPlayer getInstance(Context ctx) {
        LoomoMediaPlayer result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    context = ctx;
                    instance = result = new LoomoMediaPlayer();
            }
        }
        return result;
    }


    public void play() {
        try {

            String filename = "android.resource://" + context.getPackageName() + "/raw/w_drill";


            try { player.setDataSource(context, Uri.parse(filename)); } catch (Exception e) {}
            try {
                player.prepare();
                player.start();
            } catch (Exception e) {}


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void teardown() {
        if (player.isPlaying()) {
            player.stop();
            player.reset();
        }
    }

    @Override
    public void onBreak() {
        teardown();
    }
}