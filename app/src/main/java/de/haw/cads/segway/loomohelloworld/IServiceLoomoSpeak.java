package de.haw.cads.segway.loomohelloworld;

public interface IServiceLoomoSpeak extends IServiceNeedIntegrationInLoomoStateMachine {
    public void speakText(String txt) throws InterruptedException;
}
