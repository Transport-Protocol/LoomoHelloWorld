/**
 * (c) 2019  HAW Hamburg CaDS Martin Becke Martin.Becke@HAW-Hamburg.de
 *
 */
package de.haw.cads.segway.loomohelloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ILoomoBaseStateObserver stateObserver;
    private LoomoTxtToSpeechManager txtToSpeechManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Start app");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Start Loomo Base Service Control");

        // Setup Listener for Loomo Events
        LoomoBaseServiceControl.getInstance(this.getApplicationContext());
        stateObserver = LoomoBaseStateManager.getInstance(this.getApplicationContext());

        // Create Loomos Voice
        LoomoVoice voice = new LoomoVoice();
        // Add Text to Speech to Voice
        txtToSpeechManager = LoomoTxtToSpeechManager.getInstance(this.getApplicationContext());
        txtToSpeechManager.registerListener(voice);


        // Say some text ->
        new Thread(voice).start();    // Perhaps it is a good idea to use a thread for Speech output
        // --> voice.speak("Hallo Du!"); // Text to Speech is an asynchron service. Here is the proof.

        Log.i(TAG, "Loomo Started..............................................");
    }
}
