package com.guojianyiliao.eryitianshi.Utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.View.activity.OnekeyLoinActivity;
import com.guojianyiliao.eryitianshi.View.activity.RegisterActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by hasee on 2016/6/27.
 */
public class SmsObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;

    public SmsObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);


        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        getValidateCode();

    }


    private void getValidateCode() {

        try {
            String code = "";
            Uri inboxUri = Uri.parse("content://sms/inbox");
            Cursor c = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
            if (c != null) {
                if (c.moveToFirst()) {
                    String address = c.getString(c.getColumnIndex("address"));
                    String body = c.getString(c.getColumnIndex("body"));

                    if (address.substring(0, 3).equals("106")) {

                        Pattern pattern = Pattern.compile("(\\d{6})");
                        Matcher matcher = pattern.matcher(body);

                        if (matcher.find()) {

                            code = matcher.group(0);

                            mHandler.obtainMessage(OnekeyLoinActivity.MSG, code).sendToTarget();

                            mHandler.obtainMessage(RegisterActivity.RMSG, code).sendToTarget();

                        }

                    }

                }
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "自动获取验证码失败，请手动开启读取短信权限", Toast.LENGTH_SHORT).show();
        }


    }
}
