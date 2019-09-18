package de.haw.cads.segway.loomohelloworld;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import android.widget.Toast;
import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.voice.Speaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.speech.tts.TextToSpeech.*;

/**
 * Note: That is interesting: At Least Loomo use Android TextToSpeech Function. In the end it is just a question of output.
 * Use a example from https://www.tutorialspoint.com/android/android_text_to_speech.htm as first setup
 */
public class LoomoTxtToSpeechManager implements OnInitListener, INeedIntegrationInLoomoStateMachine, ILoomoSpeak {
    private static final String TAG = "LoomoTxtToSpeechManager";
    private static volatile LoomoTxtToSpeechManager instance;
    private static Object mutex = new Object();
    private static Object condition = new Object();

    private static Context context;
    private TextToSpeech tts_context;
    private static boolean init_done = false;

    private static List<ITextToSpeechServiceListener> listenerList = new ArrayList<>();

    private LoomoTxtToSpeechManager() {
        init();
    }

    public void registerListener(ITextToSpeechServiceListener l){
        Log.i(TAG,"Registered Listener..");
        listenerList.add(l);

        if(init_done) {
            l.onTextToSpeechIsReady(this);
            Log.i(TAG,"Still ready..");
        }
    }


    public static LoomoTxtToSpeechManager getInstance(Context ctx) {
        LoomoTxtToSpeechManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    context = ctx;
                    instance = result = new LoomoTxtToSpeechManager();

                    Log.i(TAG, "Created instance");
                }
            }
        }
        return result;
    }

    @Override
    public void onInit(int status) {
        // What else to do? Best to set language support
        Log.i(TAG,"Set TTS Engine...Try to setup");
        if (status == TextToSpeech.SUCCESS) {
            if (tts_context.isLanguageAvailable(Locale.GERMAN) == TextToSpeech.LANG_AVAILABLE)
                tts_context.setLanguage(Locale.GERMAN);
            else if (tts_context.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                tts_context.setLanguage(Locale.US);


            // Notify all
            for (ITextToSpeechServiceListener l : listenerList) {
                l.onTextToSpeechIsReady(this);
            }
            init_done = true;
        } else if (status == ERROR) {
           Log.e(TAG,"Text To Speech not functional");
        }

    }

    public void onBreak(){
        if(tts_context !=null){
            tts_context.stop();
            tts_context.shutdown();
        }
    }

    @Override
    public void teardown() {
        Speaker.getInstance().unbindService();
    }

    private void init(){
        // Loomo has a Speaker singleton, where we have to bin the listener and create onBind and onUbind, needs ctx
        if (context == null) {
            throw new IllegalStateException("context instance not initialized yet");
        }

        tts_context = new TextToSpeech(context, this);
        Speaker.getInstance().bindService(context, new ServiceBinder.BindStateListener() {
            @Override
            public void onBind() {
                Log.i(TAG, "Speaker onBind");
            }

            @Override
            public void onUnbind(String reason) {
                Log.i(TAG, "Speaker onUnbind");
            }
        });
    }

    public synchronized void speakText(String txt) {
            Log.d(TAG, "Speaker try to say: " + txt);
            tts_context.speak(txt, QUEUE_FLUSH, null);
    }
}

