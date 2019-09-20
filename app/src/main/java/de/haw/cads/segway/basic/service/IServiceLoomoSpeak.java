package de.haw.cads.segway.basic.service;

import de.haw.cads.segway.basic.service.IServiceNeedIntegrationInLoomoStateMachine;

public interface IServiceLoomoSpeak extends IServiceNeedIntegrationInLoomoStateMachine {
    public void speakText(String txt) throws InterruptedException;
}
