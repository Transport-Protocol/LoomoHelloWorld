## History 
20190917 Init project    
20190918 Initial APK Setup     
20190918 Add Base Service    
20190918 Setup an Event Observer  -> ILoomoBaseStateObserver     
20190918 Add initial Voice Service (Text to Speech)    

## Prepare
Follow the steps of the Segway Documentation: [External Segway](https://developer.segwayrobotics.com/developer/documents/setup-developing-environment.html)
Do not miss to add the following lines in your *build.gradle* file (of your app not your project):

```$xslt
    implementation 'com.segway.robot:visionsdk:0.6.547'
    implementation 'com.segway.robot:speech-sdk:0.5.327'
    implementation 'com.segway.robot:headsdk:0.6.746'
    implementation 'com.segway.robot:basesdk:0.6.746'
    implementation 'com.segway.robot:sensorsdk:0.6.746'
    implementation 'com.segway.robot:robot-connectivity-sdk:0.5.104'
    implementation 'com.segway.robot:mobile-connectivity-sdk:0.5.104'
    implementation 'com.segway.robot:emoji:0.1.28'
    implementation 'com.segway.robot:support-lib:1.1.2'
```
Note: Unknown issue: emoji raise an error (Not investigated yet). Current workaround: 
```
 // implementation 'com.segway.robot:emoji:0.1.28'
```
## App Details
### LoomoBaseService     
General bind to the plattform
### LommoBaseState 
General Service to listen for Loomo events     
```$xslt
   LoomoBaseState.BATTERY_CHANGED       // "com.segway.robot.action.BATTERY_CHANGED";
   LoomoBaseState.POWER_DOWN            //"com.segway.robot.action.POWER_DOWN";
   LoomoBaseState.POWER_BUTTON_PRESSED  // "com.segway.robot.action.POWER_BUTTON_PRESSED";
   LoomoBaseState.POWER_BUTTON_RELEASED // "com.segway.robot.action.POWER_BUTTON_RELEASED";
   LoomoBaseState.SBV_MODE              // "com.segway.robot.action.TO_SBV";
   LoomoBaseState.ROBOT_MODE            // "com.segway.robot.action.TO_ROBOT";
   LoomoBaseState.PITCH_LOCK            // "com.segway.robot.action.PITCH_LOCK";
   LoomoBaseState.PITCH_UNLOCK          // "com.segway.robot.action.PITCH_UNLOCK";
   LoomoBaseState.YAW_LOCK              // "com.segway.robot.action.YAW_LOCK";
   LoomoBaseState.YAW_UNLOCK            // "com.segway.robot.action.YAW_UNLOCK";
   LoomoBaseState.STEP_ON               // "com.segway.robot.action.STEP_ON";
   LoomoBaseState.STEP_OFF              // "com.segway.robot.action.STEP_OFF";
   LoomoBaseState.LIFT_UP               // "com.segway.robot.action.LIFT_UP";
   LoomoBaseState.PUT_DOWN              // "com.segway.robot.action.PUT_DOWN";
   LoomoBaseState.PUSHING               // "com.segway.robot.action.PUSHING";
   LoomoBaseState.USH_RELEASE           // "com.segway.robot.action.PUSH_RELEASE";
   LoomoBaseState.BASE_LOCK             // "com.segway.robot.action.BASE_LOCK";
   LoomoBaseState.BASE_UNLOCK           // "com.segway.robot.action.BASE_UNLOCK";
   LoomoBaseState.STAND_UP              // "com.segway.robot.action.STAND_UP";

```
### LoomoVoice 
Basic Loomo Voice, will be extended with general Voice and Sound Actions.   
TextToSpeech and Media Player is used for internal representation.     
The setup of the TextToSpeech is async. Therefore a Runnable is used for initial sound. 



## Troubleshooting 

* Important: Use ANDROID SDK 5.1   

* Gradle Error Message: Execution failed for task ':app:transformClassesWithInstantRunForDebug'
Sometimes after import File-> Invalidate Caches / Restart... is needed. 
Seen in IntellJ after rebase. 

* Gradle Error Message: SDK location not found. Define location with sdk.dir in the local.properties file or with an ANDROID_HOME environment variable.
Sometimes it is needed after a new checkout to create a *local.properties* file (Follow up a Linux example):

```
   sdk.dir=/home/user/Android/Sdk/
```


