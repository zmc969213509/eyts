package com.guojianyiliao.eryitianshi.Data;

/**
 *
 */
public class Http_data {

    //    public static final String http_data = "http://app.eryitianshi.com/Test";

    public static final boolean isnetwork = false;
    public static final String Debug = "http://192.168.1.89:8080/AppServer/";
    public static final String Service = "http://app.eryitianshi.com/Test/";
    public static final String local = "http://192.168.1.11:10010/AppServer/"; //本地节点

    public static final String http_data = isnetwork ? Service : local;


//    public static final String http_data = "http://192.168.1.10:8080/Test";

//    public static final String http_data="http://192.168.1.11:8080/Test";

    public static final String version_number = "1.0.10";

    public static int giveCashState = 1;

}
