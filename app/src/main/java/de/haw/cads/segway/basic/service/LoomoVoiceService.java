package de.haw.cads.segway.basic.service;

import android.content.Context;
import android.util.Log;
import de.haw.cads.segway.basic.util.ITextToSpeechServiceListener;
import de.haw.cads.segway.basic.util.LoomoMediaPlayerService;
import de.haw.cads.segway.basic.util.LoomoTxtToSpeechService;

/**
 * It is not needed to setup a singleton, anyway the txt to speech is
 */
public class LoomoVoiceService implements IServiceNeedIntegrationInLoomoStateMachine, ITextToSpeechServiceListener,Runnable {
    private static final String TAG = "LoomoVoiceService";
    private boolean isNotReady = true;
    private Context context;

    IServiceLoomoSpeak speaker;
    LoomoTxtToSpeechService txtToSpeechManager;

    @Override
    public synchronized void onTextToSpeechIsReady(IServiceLoomoSpeak s) {
            Log.i(TAG, "Set Speaker");
            speaker = s;
            isNotReady = false;
            notifyAll();
            Log.i(TAG, "The Speaker is initialized");
    }

    public LoomoVoiceService(Context ctx){
        context = ctx;
        // Add Text to Speech to Voice
        txtToSpeechManager = LoomoTxtToSpeechService.getInstance(context);
        txtToSpeechManager.registerListener(this);
    }

    public synchronized void speak(String txt){
        if(isNotReady) {
            Log.e(TAG, "Not ready, we can not say " + txt);
            return;
        }
        try {
            speaker.speakText(txt);
        } catch (InterruptedException ex) {
            Log.e(TAG, ex.toString());
        }
    }

    public synchronized void speakStart(String txt)  {
        while(isNotReady) {
            try {
                Log.i(TAG, "Wait for Text to Speech");
                wait();
                Log.i(TAG, "We can read");
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                Log.e(TAG, ex.toString());
            }
        }
        try {
            speaker.speakText(txt);
        } catch (InterruptedException ex) {
            Log.e(TAG, ex.toString());
        }
    }

    @Override
    public void run() {
        LoomoMediaPlayerService.getInstance(context).play();
        this.speakStart("Los geht es...!");
    }

    @Override
    public void teardown() {
        LoomoMediaPlayerService.getInstance(context).teardown();
        txtToSpeechManager.teardown();
        speaker.teardown();
    }

    @Override
    public void onBreak() {
        LoomoMediaPlayerService.getInstance(context).onBreak();
        txtToSpeechManager.onBreak();
        speaker.onBreak();
    }
}
