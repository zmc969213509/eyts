package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.io.Serializable;

/**
 * description:
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/18 16:19
 * update: 2017/4/18
 * version: Administrator
 */

public class ImageInfoGrow implements Serializable {

    String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ImageInfoGrow{" +
                "uri='" + uri + '\'' +
                '}';
    }
}
