package com.guojianyiliao.eryitianshi.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Chen on 2016/9/9.
 */


public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String str = bundle.getString(JPushInterface.EXTRA_MESSAGE);

            System.out.println("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            MyLogcat.jLog().e("收到了自定义消息。消息内容是："+ bundle.getString(JPushInterface.EXTRA_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {



        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {



        } else {

        }
    }
}
