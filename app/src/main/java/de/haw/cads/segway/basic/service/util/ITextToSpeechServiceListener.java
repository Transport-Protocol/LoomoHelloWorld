package de.haw.cads.segway.basic.service.util;

import de.haw.cads.segway.basic.service.IServiceNeedIntegrationInLoomoStateMachine;
import de.haw.cads.segway.basic.service.IServiceLoomoSpeak;

public interface ITextToSpeechServiceListener extends IServiceNeedIntegrationInLoomoStateMachine {
    public void onTextToSpeechIsReady(IServiceLoomoSpeak s);
}
