package com.guojianyiliao.eryitianshi.MyUtils.manager;

import com.squareup.otto.Bus;

/**
 * otto
 */

public class BusProvider {
    private volatile static Bus bus = null;

    private BusProvider() {
    }

    public static Bus getInstance() {
        if (bus == null) {
            synchronized (BusProvider.class) {
                bus = new Bus();
            }
        }
        return bus;
    }
}
