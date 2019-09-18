package de.haw.cads.segway.loomohelloworld;

import android.util.Log;

public class LoomoVoice implements ITextToSpeechServiceListener,Runnable {
    private static final String TAG = "LoomoVoice";
    private boolean isNotReady = true;
    ILoomoSpeak speaker;

    @Override
    public synchronized void onTextToSpeechIsReady(ILoomoSpeak s) {
            Log.i(TAG, "Set Speaker");
            speaker = s;
            isNotReady = false;
            notifyAll();
            Log.i(TAG, "The Speaker is initialized");
    }

    public LoomoVoice(){

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
        speakStart("Los geht es...!");
    }
}
