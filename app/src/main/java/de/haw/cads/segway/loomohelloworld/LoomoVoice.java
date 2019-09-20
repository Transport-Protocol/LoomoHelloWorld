package de.haw.cads.segway.loomohelloworld;

import android.content.Context;
import android.util.Log;

/**
 * It is not needed to setup a singleton, anyway the txt to speech is
 */
public class LoomoVoice implements ITextToSpeechServiceListener,Runnable {
    private static final String TAG = "LoomoVoice";
    private boolean isNotReady = true;
    private Context context;

    ILoomoSpeak speaker;
    LoomoTxtToSpeech txtToSpeechManager;

    @Override
    public synchronized void onTextToSpeechIsReady(ILoomoSpeak s) {
            Log.i(TAG, "Set Speaker");
            speaker = s;
            isNotReady = false;
            notifyAll();
            Log.i(TAG, "The Speaker is initialized");
    }

    public LoomoVoice(Context ctx){
        context = ctx;
        // Add Text to Speech to Voice
        txtToSpeechManager = LoomoTxtToSpeech.getInstance(context);
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
        LoomoMediaPlayer.getInstance(context).play();
        this.speakStart("Los geht es...!");
    }
}
