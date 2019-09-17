## History 
20190917 Init project    
20190918 Initial APK Setup     

## Prepare
Follow the steps of the Segway Documentation: [External Segway](https://developer.segwayrobotics.com/developer/documents/setup-developing-environment.html)
Do not miss to add the following lines in your *build.gradle* file of you app (not project):

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
TBD

## Troubleshooting 

* Important: Use ANDROID SDK 5.1   

* Gradle Error Message: Execution failed for task ':app:transformClassesWithInstantRunForDebug'
Somethimes after import File-> Invalidate Caches / Restart... is needed. 
Seen in IntellJ after rebase. 

* Gradle Error Message: SDK location not found. Define location with sdk.dir in the local.properties file or with an ANDROID_HOME environment variable.
Sometimes it is needed after a new checkout to create a *local.properties* file (Follow up a Linux example):

```
   sdk.dir=/home/user/Android/Sdk/
```


