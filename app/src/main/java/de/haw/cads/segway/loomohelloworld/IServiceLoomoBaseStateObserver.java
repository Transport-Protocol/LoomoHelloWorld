package de.haw.cads.segway.loomohelloworld;

public interface IServiceLoomoBaseStateObserver extends IServiceNeedIntegrationInLoomoStateMachine {
    public void notifyListener(String e);
    public void registerLoomoBaseStateListener(ILoomoBaseStateListener l, String e);
    public void unregisterLoomoBaseStateListener(ILoomoBaseStateListener l, String e);

}
