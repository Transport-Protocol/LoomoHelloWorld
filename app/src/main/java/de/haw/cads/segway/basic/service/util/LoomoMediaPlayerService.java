package de.haw.cads.segway.basic.service.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import de.haw.cads.segway.basic.service.IServiceNeedIntegrationInLoomoStateMachine;

public class LoomoMediaPlayerService implements IServiceNeedIntegrationInLoomoStateMachine, ILoomoPlay {
    private static final String TAG = "LoomoMediaPlayerService";
    private static volatile LoomoMediaPlayerService instance;
    private static Object mutex = new Object();
    private static Context context;
    // The media player does the magic
    MediaPlayer player;

    private LoomoMediaPlayerService() {
        player = new MediaPlayer();
    }

    public static LoomoMediaPlayerService getInstance(Context ctx) {
        LoomoMediaPlayerService result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    context = ctx;
                    instance = result = new LoomoMediaPlayerService();
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