/**
 * (c) 2019  HAW Hamburg CaDS Martin Becke Martin.Becke@HAW-Hamburg.de
 *
 */
package de.haw.cads.segway.loomohelloworld;

import android.content.Context;
import android.util.Log;

import com.segway.robot.algo.Pose2D;
import com.segway.robot.algo.minicontroller.CheckPoint;
import com.segway.robot.algo.minicontroller.CheckPointStateListener;
import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.locomotion.sbv.Base;

/**
 * An Singleton to focus on an Control App System
 */
public class LoomoBaseServiceService implements IServiceNeedIntegrationInLoomoStateMachine {
    // Singleton tuff
    private static final String TAG = "LoomoBaseServiceService";

    // Options
    private static boolean ControlModeNavigationSwitch = false;

    private static volatile LoomoBaseServiceService instance;
    private static Object mutex = new Object();

    // Loomo stuff
    private Base loomoBase = null;
    private Context context;

    // Position stuff -> important to organize movement
    private float lastXPosition = 0f;
    private float lastYPosition = 0f;

    private LoomoBaseServiceService(Context c) {
        this.context = c;
        initLoomo();
    }

    private void initLoomo(){
        Log.i(TAG, "Try to init the Loomo Base System");
        loomoBase = Base.getInstance();

        // Demo Source of the Loomo Project
        loomoBase.bindService(context, new ServiceBinder.BindStateListener() { //  @Note Bind need unbind
            @Override
            public void onBind() {
                Log.d(TAG, "Base bind successful");

                if(ControlModeNavigationSwitch) {
                    setControlModeNavigation();
                }
            }

            @Override
            public void onUnbind(String reason) {
                Log.d(TAG, "Base bind failed");
            }
        });
        Log.i(TAG, "Init the Loomo Base System done");
    }

    private void setControlModeNavigation(){
        Log.i(TAG, "Try to set the Control Mode to Navigation");
        if (loomoBase == null) {
            throw new IllegalStateException("loomoBase not initialized yet");
        }
        
        Log.d(TAG, "Set CONTROL_MODE_NAVIGATION");
        loomoBase.setControlMode(Base.CONTROL_MODE_NAVIGATION);

        loomoBase.setOnCheckPointArrivedListener(new CheckPointStateListener() {
            @Override
            public void onCheckPointArrived(CheckPoint checkPoint, final Pose2D realPose, boolean isLast) {
                Log.i(TAG, "Position before moving: " + lastXPosition + " / " + lastYPosition);
                lastXPosition = checkPoint.getX();
                lastYPosition = checkPoint.getY();
                Log.i(TAG, "Position after moving: " + lastXPosition + " / " + lastYPosition);
            }

            @Override
            public void onCheckPointMiss(CheckPoint checkPoint, Pose2D realPose, boolean isLast, int reason) {
                lastXPosition = checkPoint.getX();
                lastYPosition = checkPoint.getY();
                Log.i(TAG, "Missed checkpoint: " + lastXPosition + " " + lastYPosition);
            }
        });
        Log.i(TAG, "Setting Control Mode to Navigation done");

    }
    public static LoomoBaseServiceService getInstance(Context ctx) {
        Log.i(TAG, "Try to catch instance...need context");
        LoomoBaseServiceService result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new LoomoBaseServiceService(ctx);
            }
        }
        Log.i(TAG, "Leave get Instance");
        return result;
    }
    @Override
    public void teardown() {
        Log.i(TAG, "Try to Teardown");
        if (instance == null) {
            throw new IllegalStateException("LoomoBaseServiceService instance not initialized yet");
        }
        this.loomoBase.unbindService(); // @note a unbind need an bind
        this.loomoBase = null;
        instance = null;
        Log.i(TAG, "Teardown done");
    }

    @Override
    public void onBreak() {

    }

}