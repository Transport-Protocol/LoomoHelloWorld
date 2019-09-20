package de.haw.cads.segway.loomohelloworld;

public interface ITextToSpeechServiceListener extends IServiceNeedIntegrationInLoomoStateMachine{
    public void onTextToSpeechIsReady(IServiceLoomoSpeak s);
}
