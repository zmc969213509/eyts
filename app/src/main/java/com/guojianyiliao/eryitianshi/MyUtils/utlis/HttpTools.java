package com.guojianyiliao.eryitianshi.MyUtils.utlis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import static com.tencent.tinker.android.dex.util.FileUtils.readStream;

public class HttpTools {

    private HttpCallBack callBack;

    /**
     * 一个执行url 获取数据的方法 需要有一个回调   15828649537
     *
     * @param url
     * @param callBack
     */
    public void execute(String url, HttpCallBack callBack) {
        this.callBack = callBack;
        System.out.println("将callball赋给了局部变量");
        HttpTask task = new HttpTask();
        task.execute(url);
    }

    /**
     * 自定义的回调接口
     *
     * @author Administrator
     */
    public static interface HttpCallBack {
        //获取数据成功方法  并传入获取的数据结果  用于调用回调后重写此方法进行其他操作
        void success(String result);

        //获取数据失败方法   用于调用回调后重写此方法进行其他操作
        void fail();
    }

    /**
     * 自定义类继承AsyncTask 用于执行线程操作
     * AsyncTask<String, Void, String>第一个为传入参数类型  第三个为返回参数类型
     *
     * @author Administrator
     */
    public class HttpTask extends AsyncTask<String, Void, String> {

        //用于开线程执行耗时操作 并将执行后的参数返回到onPostExecute方法中
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            System.out.println("开始执行线程");
            String str_url = params[0];
            if (str_url == null) {
                return null;
            }
            Log.e("LoginSelectActivity~~~","str_url = "+str_url);
            String result = null;

            // 得到HttpClient对象
            HttpClient getClient = new DefaultHttpClient();

            // 得到HttpGet对象
            getClient.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);// 连接时间
            getClient.getParams().setParameter(
                    CoreConnectionPNames.SO_TIMEOUT, 20000);// 数据传
            HttpGet request = new HttpGet(str_url);
            // 客户端使用GET方式执行请教，获得服务器端的回应response
            HttpResponse response;
            try {
                response = getClient.execute(request);
                // 判断请求是否成功
                Log.e("LoginSelectActivity~~~","response.getStatusLine().getStatusCode() = "+response.getStatusLine().getStatusCode());
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(response
                            .getEntity());
                    result = result.trim();
                    result = new String(result.getBytes(), "UTF-8");
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.e("LoginSelectActivity~~~","result = "+result);

            return result;

//            HttpURLConnection urlConn = null;
//            // 新建一个URL对象
//            URL url = null;
//            try {
//                Log.e("LoginSelectActivity~~~","str_url = "+str_url);
//                url = new URL(str_url);
//                // 打开一个HttpURLConnection连接
//                urlConn = (HttpURLConnection) url.openConnection();
//                // 设置连接超时时间
//                urlConn.setConnectTimeout(5 * 1000);
//                // 开始连接
//                urlConn.connect();
//                Log.e("LoginSelectActivity~~~","urlConn.getResponseCode() = "+urlConn.getResponseCode());
//                // 判断请求是否成功
//                if (urlConn.getResponseCode() == 200) {
//                    // 获取返回的数据
//                    byte[] data = readStream(urlConn.getInputStream());
//                    //获得响应信息
//                    String content = new String(data);
//                    String str = new String(content.getBytes(), "UTF-8"); // 设置编码方式
//                    return str;
//                } else {
//
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                Log.e("LoginSelectActivity~~~","MalformedURLException = "+e.getMessage());
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//                Log.e("LoginSelectActivity~~~","UnsupportedEncodingException = "+e.getMessage());
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e("LoginSelectActivity~~~","IOException = "+e.getMessage());
//            } finally {
//                if(urlConn != null){
//                    // 关闭连接
//                    urlConn.disconnect();
//                }
//            }
//            return null;
        }


        //执行结束后 将结果传给UI
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            System.out.println("进入到了onPostExecute方法");
            if (callBack != null) {
                System.out.println("callback不为空");
                if (result == null) {
                    System.out.println("结果集为空调用fail方法");
                    callBack.fail();
                } else {
                    System.out.println("得到结果集：" + result);
                    callBack.success(result);
                }
            }
        }
    }
}
