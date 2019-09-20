package de.haw.cads.segway.loomohelloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Main System for Loomo Events
 */
// @TODO We should add an Observer Pattern, to create a listener structure
public class LoomoBaseState implements ILoomoBaseStateObserver,INeedIntegrationInLoomoStateMachine {
    private static final String TAG = "LoomoBaseState";
    private static volatile ILoomoBaseStateObserver instance;
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

    private static List<ILoomoBaseStateListener> channel_BATTERY_CHANGED = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_POWER_DOWN = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_POWER_BUTTON_PRESSED = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_POWER_BUTTON_RELEASED = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_SBV_MODE = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_ROBOT_MODE = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_PITCH_LOCK = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_PITCH_UNLOCK = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_YAW_LOCK= new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_YAW_UNLOCK = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_STEP_ON = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_STEP_OFF = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_LIFT_UP = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_PUT_DOWN = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_PUSHING = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_PUSH_RELEASE = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_BASE_LOCK = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_BASE_UNLOCK = new ArrayList<>();
    private static List<ILoomoBaseStateListener> channel_STAND_UP = new ArrayList<>();

    private LoomoBaseState() {
        // Perhaps we need something here
    }

    public static ILoomoBaseStateObserver getInstance(Context c) {
        ILoomoBaseStateObserver result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    IntentFilter filter = new IntentFilter();
                    instance = result = new LoomoBaseState();
                    c.getApplicationContext().registerReceiver(new LoomoBroadcastReceiver(instance,filter), filter);
                }

            }
        }
        return result;
    }

    public void registerLoomoBaseStateListener(ILoomoBaseStateListener l, String e){

        Log.i(TAG,"Register Listener to event " + e);
        switch (e) {
            case BATTERY_CHANGED:
                channel_BATTERY_CHANGED.add(l);
                break;
            case POWER_DOWN:
                channel_POWER_DOWN.add(l);
                break;
            case POWER_BUTTON_PRESSED:
                channel_POWER_BUTTON_PRESSED.add(l);
                break;
            case POWER_BUTTON_RELEASED:
                channel_POWER_BUTTON_RELEASED.add(l);
                break;
            case SBV_MODE:
                channel_SBV_MODE.add(l);
                break;
            case ROBOT_MODE:
                channel_ROBOT_MODE.add(l);
                break;
            case PITCH_LOCK:
                channel_PITCH_LOCK.add(l);
                break;
            case PITCH_UNLOCK:
                channel_PITCH_UNLOCK.add(l);
                break;
            case YAW_LOCK:
                channel_YAW_LOCK.add(l);
                break;
            case YAW_UNLOCK:
                channel_YAW_UNLOCK.add(l);
                break;
            case STEP_ON:
                channel_STEP_ON.add(l);
                break;
            case STEP_OFF:
                channel_STEP_OFF.add(l);
                break;
            case LIFT_UP:
                channel_LIFT_UP.add(l);
                break;
            case PUT_DOWN:
                channel_PUT_DOWN.add(l);
                break;
            case PUSHING:
                channel_PUSHING.add(l);
                break;
            case PUSH_RELEASE:
                channel_PUSH_RELEASE.add(l);
                break;
            case BASE_LOCK:
                channel_BASE_LOCK.add(l);
                break;
            case BASE_UNLOCK:
                channel_BASE_UNLOCK.add(l);
                break;
            case STAND_UP:
                channel_STAND_UP.add(l);
                break;
            default:
                Log.e(TAG,"Wow, somting is wrong with the Segway documentation");
                new IllegalArgumentException("Something strange happend");
        }
    }

    public void unregisterLoomoBaseStateListener(ILoomoBaseStateListener l, String e){

        Log.i(TAG,"Un-Register Listener to event " + e);
        switch (e) {
            case BATTERY_CHANGED:
                channel_BATTERY_CHANGED.remove(l);
                break;
            case POWER_DOWN:
                channel_POWER_DOWN.remove(l);
                break;
            case POWER_BUTTON_PRESSED:
                channel_POWER_BUTTON_PRESSED.remove(l);
                break;
            case POWER_BUTTON_RELEASED:
                channel_POWER_BUTTON_RELEASED.remove(l);
                break;
            case SBV_MODE:
                channel_SBV_MODE.remove(l);
                break;
            case ROBOT_MODE:
                channel_ROBOT_MODE.remove(l);
                break;
            case PITCH_LOCK:
                channel_PITCH_LOCK.remove(l);
                break;
            case PITCH_UNLOCK:
                channel_PITCH_UNLOCK.remove(l);
                break;
            case YAW_LOCK:
                channel_YAW_LOCK.remove(l);
                break;
            case YAW_UNLOCK:
                channel_YAW_UNLOCK.remove(l);
                break;
            case STEP_ON:
                channel_STEP_ON.remove(l);
                break;
            case STEP_OFF:
                channel_STEP_OFF.remove(l);
                break;
            case LIFT_UP:
                channel_LIFT_UP.remove(l);
                break;
            case PUT_DOWN:
                channel_PUT_DOWN.remove(l);
                break;
            case PUSHING:
                channel_PUSHING.remove(l);
                break;
            case PUSH_RELEASE:
                channel_PUSH_RELEASE.remove(l);
                break;
            case BASE_LOCK:
                channel_BASE_LOCK.remove(l);
                break;
            case BASE_UNLOCK:
                channel_BASE_UNLOCK.remove(l);
                break;
            case STAND_UP:
                channel_STAND_UP.remove(l);
                break;
            default:
                Log.e(TAG,"Wow, somting is wrong with the Segway documentation");
                new IllegalArgumentException("Something strange happend");
        }
    }

    public void notifyListener(String e) {
        Log.i(TAG,"Inform all Listeners of Event " + e);
        switch (e) {
            case BATTERY_CHANGED:
                for (ILoomoBaseStateListener l : channel_BATTERY_CHANGED) {
                    l.onEvent(e);
                }
                break;
            case POWER_DOWN:
                for (ILoomoBaseStateListener l : channel_POWER_DOWN) {
                    l.onEvent(e);
                }
                break;
            case POWER_BUTTON_PRESSED:
                for (ILoomoBaseStateListener l : channel_POWER_BUTTON_PRESSED) {
                    l.onEvent(e);
                }
                break;
            case POWER_BUTTON_RELEASED:
                for (ILoomoBaseStateListener l : channel_POWER_BUTTON_RELEASED) {
                    l.onEvent(e);
                }
                break;
            case SBV_MODE:
                for (ILoomoBaseStateListener l : channel_SBV_MODE) {
                    l.onEvent(e);
                }
                break;
            case ROBOT_MODE:
                for (ILoomoBaseStateListener l : channel_ROBOT_MODE) {
                    l.onEvent(e);
                }
                break;
            case PITCH_LOCK:
                for (ILoomoBaseStateListener l : channel_PITCH_LOCK) {
                    l.onEvent(e);
                }
                break;
            case PITCH_UNLOCK:
                for (ILoomoBaseStateListener l : channel_PITCH_UNLOCK) {
                    l.onEvent(e);
                }
                break;
            case YAW_LOCK:
                for (ILoomoBaseStateListener l : channel_YAW_LOCK) {
                    l.onEvent(e);
                }
                break;
            case YAW_UNLOCK:
                for (ILoomoBaseStateListener l : channel_YAW_UNLOCK) {
                    l.onEvent(e);
                }
                break;
            case STEP_ON:
                for (ILoomoBaseStateListener l : channel_STEP_ON) {
                    l.onEvent(e);
                }
                break;
            case STEP_OFF:
                for (ILoomoBaseStateListener l : channel_STEP_OFF) {
                    l.onEvent(e);
                }
                break;
            case LIFT_UP:
                for (ILoomoBaseStateListener l : channel_LIFT_UP) {
                    l.onEvent(e);
                }
                break;
            case PUT_DOWN:
                for (ILoomoBaseStateListener l : channel_PUT_DOWN) {
                    l.onEvent(e);
                }
                break;
            case PUSHING:
                for (ILoomoBaseStateListener l : channel_PUSHING) {
                    l.onEvent(e);
                }
                break;
            case PUSH_RELEASE:
                for (ILoomoBaseStateListener l : channel_PUSH_RELEASE) {
                    l.onEvent(e);
                }
                break;
            case BASE_LOCK:
                for (ILoomoBaseStateListener l : channel_BASE_LOCK) {
                    l.onEvent(e);
                }
                break;
            case BASE_UNLOCK:
                for (ILoomoBaseStateListener l : channel_BASE_UNLOCK) {
                    l.onEvent(e);
                }
                break;
            case STAND_UP:
                for (ILoomoBaseStateListener l : channel_STAND_UP) {
                    l.onEvent(e);
                }
                break;
            default:
                Log.e(TAG,"Wow, somting is wrong with the Segway documentation");
                new IllegalArgumentException("Something strange happend");
        }
    }

    @Override
    public void teardown() {

    }

    @Override
    public void onBreak() {

    }

    //****************************************************************************************************

    /**
     * Perhaps there is a better way to do (
     */
    private static class LoomoBroadcastReceiver extends BroadcastReceiver {
        private static final String TAG = "LoomoBroadcastReceiver";
        // First I have to to setup the Events I am interested (We use the Android IntentFilter)
        private IntentFilter filter;
        private ILoomoBaseStateObserver mgr;

        public LoomoBroadcastReceiver(ILoomoBaseStateObserver m,IntentFilter f) {
            filter = f;
            mgr = m;
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
            mgr.notifyListener(intent.getAction());
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