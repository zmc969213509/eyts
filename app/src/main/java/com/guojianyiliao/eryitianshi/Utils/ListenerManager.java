package com.guojianyiliao.eryitianshi.Utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListenerManager
{

    public static ListenerManager listenerManager;
    

    private List<IListener> iListenerList = new CopyOnWriteArrayList<IListener>();


    public static ListenerManager getInstance()
    {
        if(listenerManager == null)
        {
            listenerManager = new ListenerManager();
        }
        return listenerManager;
    }

    public void registerListtener(IListener iListener)
    {
        iListenerList.add(iListener);
    }

    public void unRegisterListener(IListener iListener)
    {
        if(iListenerList.contains(iListener))
        {
            iListenerList.remove(iListener);
        }
    }

    public void sendBroadCast(String str)
    {
        for (IListener iListener : iListenerList)
        {
            iListener.notifyAllActivity(str);
        }
    }
    
}
