/**
 * (c) 2019  HAW Hamburg CaDS Martin Becke Martin.Becke@HAW-Hamburg.de
 *
 */
package de.haw.cads.segway.loomohelloworld;

import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ILoomoBaseStateObserver stateObserver;
    private LoomoTxtToSpeech txtToSpeechManager;

    private void teardown(){
        txtToSpeechManager.teardown();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Start app");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Start Loomo Base Service Control");


        // On Of Switch
        Switch sw = (Switch) findViewById(R.id.onoffswitch);
        sw.setChecked(true);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    // // Turn off the app
                    // code to push it in the back
                    // Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    // homeIntent.addCategory( Intent.CATEGORY_HOME );
                    // homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // startActivity(homeIntent);
                    teardown();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            }
        });


        // Setup Listener for Loomo Events
        LoomoBaseService.getInstance(this.getApplicationContext());
        stateObserver = LoomoBaseState.getInstance(this.getApplicationContext());

        // Create Loomos Voice
        LoomoVoice voice = new LoomoVoice(this.getApplicationContext());

        // Say some text ->
        new Thread(voice).start();    // Perhaps it is a good idea to use a thread for Speech output
        // --> voice.speak("Hallo Du!"); // Text to Speech is an asynchron service. Here is the proof.

        Log.i(TAG, "Loomo Started..............................................");
    }
}
