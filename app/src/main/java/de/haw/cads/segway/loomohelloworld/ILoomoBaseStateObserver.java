package de.haw.cads.segway.loomohelloworld;

public interface ILoomoBaseStateObserver {
    public void notifyListener(String e);
    public void registerLoomoBaseStateListener(ILoomoBaseStateListener l, String e);
    public void unregisterLoomoBaseStateListener(ILoomoBaseStateListener l, String e);

}
