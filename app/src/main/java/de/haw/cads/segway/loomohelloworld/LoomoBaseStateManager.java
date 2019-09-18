package de.haw.cads.segway.loomohelloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.segway.robot.algo.Pose2D;
import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.head.Head;
import com.segway.robot.sdk.locomotion.sbv.Base;
import com.segway.robot.sdk.perception.sensor.Sensor;
import com.segway.robot.sdk.vision.Vision;
import com.segway.robot.sdk.voice.Recognizer;
import com.segway.robot.sdk.voice.Speaker;

/**
 * Main System for Loomo Events
 */
// @TODO We should add an Observer Pattern, to create a listener structure
public class LoomoBaseStateManager {
    private static final String TAG = "LoomoBaseStateManager";
    private static volatile LoomoBaseStateManager instance;
    private static Object mutex = new Object();


    // Loomo states -> Hope missed nothing
    // List got from https://developer.segwayrobotics.com/developer/documents/segway-robots-sdk.html
    private static final String BATTERY_CHANGED = "com.segway.robot.action.BATTERY_CHANGED";
    private static final String POWER_DOWN = "com.segway.robot.action.POWER_DOWN";
    private static final String POWER_BUTTON_PRESSED = "com.segway.robot.action.POWER_BUTTON_PRESSED";
    private static final String POWER_BUTTON_RELEASED = "com.segway.robot.action.POWER_BUTTON_RELEASED";
    private static final String SBV_MODE = "com.segway.robot.action.TO_SBV";
    private static final String ROBOT_MODE = "com.segway.robot.action.TO_ROBOT";
    private static final String PITCH_LOCK = "com.segway.robot.action.PITCH_LOCK";
    private static final String PITCH_UNLOCK = "com.segway.robot.action.PITCH_UNLOCK";
    private static final String YAW_LOCK = "com.segway.robot.action.YAW_LOCK";
    private static final String YAW_UNLOCK = "com.segway.robot.action.YAW_UNLOCK";
    private static final String STEP_ON = "com.segway.robot.action.STEP_ON";
    private static final String STEP_OFF = "com.segway.robot.action.STEP_OFF";
    private static final String LIFT_UP = "com.segway.robot.action.LIFT_UP";
    private static final String PUT_DOWN = "com.segway.robot.action.PUT_DOWN";
    private static final String PUSHING = "com.segway.robot.action.PUSHING";
    private static final String PUSH_RELEASE = "com.segway.robot.action.PUSH_RELEASE";
    private static final String BASE_LOCK = "com.segway.robot.action.BASE_LOCK";
    private static final String BASE_UNLOCK = "com.segway.robot.action.BASE_UNLOCK";
    private static final String STAND_UP = "com.segway.robot.action.STAND_UP";


    private LoomoBaseStateManager() {

    }

    public static LoomoBaseStateManager getInstance(Context c) {
        LoomoBaseStateManager result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    IntentFilter filter = new IntentFilter();
                    instance = result = new LoomoBaseStateManager();
                    c.getApplicationContext().registerReceiver(new LoomoBroadcastReceiver(filter), filter);
                }

            }
        }
        return result;
    }

    /**
     * Perhaps there is a better way to do (
     */
    private static class LoomoBroadcastReceiver extends BroadcastReceiver {
        private static final String TAG = "LoomoBroadcastReceiver";
        // First I have to to setup the Events I am interested (We use the Android IntentFilter)
        private IntentFilter filter;

        public LoomoBroadcastReceiver(IntentFilter f) {
            filter = f;
            initListOfEvents();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG,"Get an event " + intent.getAction());
            switch (intent.getAction()) {
                case BATTERY_CHANGED:
                    Log.i(TAG,"Received BATTERY_CHANGED");
                    break;
                case POWER_DOWN:
                    Log.i(TAG,"Received POWER_DOWN");
                    break;
                case POWER_BUTTON_PRESSED:
                    Log.i(TAG,"Received POWER_BUTTON_PRESSED");
                    break;
                case POWER_BUTTON_RELEASED:
                    Log.i(TAG,"Received POWER_BUTTON_RELEASED");
                    break;
                case SBV_MODE:
                    Log.i(TAG,"Received TO_SBV");
                    break;
                case ROBOT_MODE:
                    Log.i(TAG,"Received TO_ROBOT");
                    break;
                case PITCH_LOCK:
                    Log.i(TAG,"Received PITCH_LOCK");
                    break;
                case PITCH_UNLOCK:
                    Log.i(TAG,"Received PITCH_UNLOCK");
                    break;
                case YAW_LOCK:
                    Log.i(TAG,"Received YAW_LOCK");
                    break;
                case YAW_UNLOCK:
                    Log.i(TAG,"Received YAW_UNLOCK");
                    break;
                case STEP_ON:
                    Log.i(TAG,"Received STEP_ON");
                    break;
                case STEP_OFF:
                    Log.i(TAG,"Received STEP_OFF");
                    break;
                case LIFT_UP:
                    Log.i(TAG,"Received LIFT_UP");
                    break;
                case PUT_DOWN:
                    Log.i(TAG,"Received PUT_DOWN");
                    break;
                case PUSHING:
                    Log.i(TAG,"Received PUSHING");
                    break;
                case PUSH_RELEASE:
                    Log.i(TAG,"Received PUSH_RELEASE");
                    break;
                case BASE_LOCK:
                    Log.i(TAG,"Received BASE_LOCK");
                    break;
                case BASE_UNLOCK:
                    Log.i(TAG,"Received BASE_UNLOCK");
                    break;
                case STAND_UP:
                    Log.i(TAG,"Received STAND_UP");
                    break;
                default:
                    Log.e(TAG,"Wow, somting is wrong with the Segway documentation");
                    new IllegalArgumentException("Something strange happend");
            }
        }

        private void initListOfEvents(){
            Log.i(TAG,"Set Filter for Events");
            filter.addAction(BATTERY_CHANGED);
            filter.addAction(POWER_DOWN);
            filter.addAction(POWER_BUTTON_PRESSED);
            filter.addAction(POWER_BUTTON_RELEASED);
            filter.addAction(SBV_MODE);
            filter.addAction(ROBOT_MODE);
            filter.addAction(PITCH_LOCK);
            filter.addAction(PITCH_UNLOCK);
            filter.addAction(YAW_LOCK);
            filter.addAction(YAW_UNLOCK);
            filter.addAction(STEP_ON);
            filter.addAction(STEP_OFF);
            filter.addAction(LIFT_UP);
            filter.addAction(PUT_DOWN);
            filter.addAction(PUSHING);
            filter.addAction(PUSH_RELEASE);
            filter.addAction(BASE_LOCK);
            filter.addAction(BASE_UNLOCK);
            filter.addAction(STAND_UP);
            Log.i(TAG,"Set Filters done");
        }


    }

}