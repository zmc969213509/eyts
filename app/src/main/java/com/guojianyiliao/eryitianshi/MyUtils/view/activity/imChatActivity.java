package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.guojianyiliao.eryitianshi.MyUtils.adaper.zmc_ImChatRecycleviewAdapter;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_ChatBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_DocChat;
import com.guojianyiliao.eryitianshi.MyUtils.db.ChatDao;
import com.guojianyiliao.eryitianshi.MyUtils.db.DoctorDao;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.SharedPreferencesTools;
import com.guojianyiliao.eryitianshi.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by zmc on 2017/5/24.
 * 利用极光im 实现医生与病患聊天
 */

public class imChatActivity extends AppCompatActivity{



    /**保存聊天医生信息**/
    private DoctorDao docDao;
    /**聊天操作类**/
    private ChatDao dao;
    /**当前显示的聊天列表数据**/
    private List<zmc_ChatBean> currentList = new ArrayList<>();
    /**历史聊天记录**/
    private List<zmc_ChatBean> historyList = new ArrayList<>();
    /**是否有更多历史数据标识   false：还有历史消息   true：没有更多历史消息**/
    private boolean noMore = false;
    /**当前已经加载了历史记录的条数   倒数**/
    private int loadNum = 0;

    /**医生名字**/
    private String docName;
    /**医生id**/
    private String docID;
    /**头像**/
    private String docIcon;
    /**医生在极光对应的id**/
    private String userName;
    /**保存聊天图片文件夹*/
    private File saveChatPicPath;
    /**保存聊天图片路径**/
    private File sdFilePath;

    UserInfoLogin user ;

    @BindView(R.id.tv_doctorname)
    TextView tv_docname;
    @BindView(R.id.et_chat)
    EditText msg_et;
    @BindView(R.id.swlayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.chat_layout)
    LinearLayout chat_layout;

    zmc_ImChatRecycleviewAdapter adapter;

    /**医生是否对用户提问进行了回复   有医生聊天退出时就进入医生评价接口**/
    private boolean isReply = false;

    Gson gson;
    /**标记   1：聊天  2：查看记录**/
    private String flag;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 1://消息发送成功  更新适配器
                    adapter.UpdateItemInserted(currentList,currentList.size());
                    recyclerView.smoothScrollToPosition(currentList.size()+1);
                    msg_et.setText("");
                    break;
                case 2://发送消息失败
                    jmessage();
                    Toast.makeText(imChatActivity.this,"消息发送失败,请重试",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    currentList.get(msg.arg1).setMsgFlag(1+"");
                    adapter.UpdateItemChange(currentList,msg.arg1);
                    dao.changeStatus(currentList.get(msg.arg1));
                    break;
                case 4:
                    currentList.get(msg.arg1).setMsgFlag(-1+"");
                    adapter.UpdateItemChange(currentList,msg.arg1);
                    dao.changeStatus(currentList.get(msg.arg1));
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_im_chat);

        ButterKnife.bind(this);

        /**
         * 聊天界面不需要HomeAcitivtyMy进行消息接受
         */
        HomeAcitivtyMy.isNeedReceive = false;

        flag = getIntent().getStringExtra("flag");

        if(flag.equals("1")){
            //开启消息接受广播
            JMessageClient.registerEventReceiver(this);
        }else if(flag.equals("2")){
            chat_layout.setVisibility(View.GONE);
        }
        gson = new Gson();
        String s = SharedPreferencesTools.GetUsearInfo(this, "userSave", "userInfo");
        user = gson.fromJson(s, UserInfoLogin.class);
        init();

        getChatData();

    }

    /**
     * 初始化数据 数据赋值等
     */
    private void init(){
        saveChatPicPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/eyts/chatPic");
        if(!saveChatPicPath.exists()){
            saveChatPicPath.mkdirs();
        }

        docName = getIntent().getStringExtra("name");
        docID = getIntent().getStringExtra("doctorID");
        docIcon = getIntent().getStringExtra("icon");
        userName = getIntent().getStringExtra("username");
        JMessageClient.enterSingleConversaion(userName);//设置单聊模式时  不通知

        docDao = new DoctorDao(this);
        docDao.add(new zmc_DocChat(docName,docID,docIcon,userName));

        tv_docname.setText(docName);

        refreshLayout.setColorSchemeResources(R.color.backgroundblue,R.color.view);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getMoreData();
            }
        });
//        //为recycleview设置滑动监听  获取当前最后显示的position
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//                //判断是当前layoutManager是否为LinearLayoutManager
//                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//                if (layoutManager instanceof LinearLayoutManager) {
//                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//                    //获取最后一个可见view的位置
//                    lastItemPosition = linearManager.findLastVisibleItemPosition();
//                    Log.e("GrowUpFragment","lastItemPosition = " +lastItemPosition);
//                }
//
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
    }

    /**
     * 获取聊天记录并进行显示
     */
    private void getChatData() {
        dao = new ChatDao(this);

        List<zmc_ChatBean> all = dao.read(userName);
        Log.e("imChatActivity","all.size() = "+all.size());
        try{
            for (int i = 0; i < all.size() ; i++) {
                if(all.get(i).getDocName().equals(userName) && all.get(i).getDisName().equals(user.getPhone())){
                    Log.e("imChatActivity",all.get(i).toString());
                    historyList.add(all.get(i));
                }
            }
        }catch (NullPointerException e){
            Log.e("imChatActivity","getChatData  e : "+e.getMessage());
        }
        Log.e("imChatActivity","historyList.size() = "+historyList.size());
        if(historyList.size() > 10){
            for (int i = historyList.size() - 10 - loadNum; i < historyList.size()-loadNum ; i++) {
                currentList.add(historyList.get(i));
            }
            loadNum += 10;
        }else{
            currentList.addAll(historyList);
            noMore = true;
        }

        adapter = new zmc_ImChatRecycleviewAdapter(this,currentList,docIcon,user.getIcon());
        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setStackFromEnd(true);//设置recycleview 从底部开始显示
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.smoothScrollToPosition(currentList.size()+1);
        recyclerView.getItemAnimator().setChangeDuration(0);// 通过设置动画执行时间为0来解决闪烁问题
    }

//    /**最后一个可见view的位置**/
//    int lastItemPosition = 0;
    /**
     * 加载更多数据
     */
    private void getMoreData(){
        if(noMore){
            Toast.makeText(imChatActivity.this,"没有数据了...",Toast.LENGTH_SHORT).show();
            refreshLayout.setRefreshing(false);
        }else{
            try {
                Thread.sleep(500);
                if(historyList.size() - loadNum > 5){
                    for (int i = historyList.size() - 5 - loadNum; i < historyList.size()-loadNum ; i++) {
                        currentList.add(0,historyList.get(i));
                    }
                    loadNum += 5;
//                    lastItemPosition += 10;
                }else{
                    for (int i = 0; i < historyList.size()-loadNum ; i++) {
                        currentList.add(0,historyList.get(i));
                    }
//                    lastItemPosition += historyList.size()-loadNum;
                    loadNum += historyList.size()-loadNum;
                    noMore = true;
                }
                refreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
//                recyclerView.smoothScrollToPosition(lastItemPosition);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     */
    @OnClick(R.id.btn_chat)
    public void send(){
        String s = msg_et.getText().toString();
        if(s.isEmpty() || s.trim().equals("")){
            Toast.makeText(imChatActivity.this,"消息不能为空",Toast.LENGTH_SHORT).show();
        }else{
            try{
                sendmessage();
            }catch (Exception e){

            }
        }
    }
    /**
     * 返回键
     */
    @OnClick(R.id.ll_consult_return)
    public void back(){
        onBackPressed();
    }
    /**
     * 选择图片
     */
    @OnClick(R.id.iv_chat)
    public void selectPic(){
        try {
            Crop.pickImage(imChatActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(imChatActivity.this, "打开图库失败，请检查是否开启权限或稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {//选择图片回调
            Log.e("onActivityResult","beginCrop");
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {//裁剪后回调
            try {
                Log.e("onActivityResult","handleCrop");
                handleCrop(resultCode, result);
            } catch (Exception e) {
                handler.sendEmptyMessage(2);
            }
        } else if (resultCode == RESULT_OK && requestCode == 1) { //照相后回调
            Log.e("onActivityResult","resultCode == RESULT_OK && requestCode == 1");
            Bundle bundle = result.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            FileOutputStream fileOutputStream = null;
            try {
                sdFilePath = File.createTempFile("camera", ".jpg", saveChatPicPath);
                fileOutputStream = new FileOutputStream(sdFilePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Log.e("onActivityResult","try");
                Conversation c = JMessageClient.getSingleConversation(userName);
                if (c == null) {
                    c = Conversation.createSingleConversation(userName);
                }
                ImageContent image = new ImageContent(sdFilePath);
                Message message = c.createSendMessage(image);
                JMessageClient.sendMessage(message);
                Date date = new Date();
                long time = date.getTime();
                zmc_ChatBean chat = new zmc_ChatBean(userName,user.getPhone(),"",sdFilePath.getAbsolutePath(),time+"","2",0+"");
                currentList.add(chat);
                dao.add(chat);
                adapter.UpdateItemInserted(currentList,currentList.size());
                recyclerView.smoothScrollToPosition(currentList.size()+1);
            } catch (Exception e) {
                e.printStackTrace();
                handler.sendEmptyMessage(2);
            }
        }
    }

    /**
     * 开始裁剪
     * @param source
     */
    private void beginCrop(Uri source) {
        Log.e("onActivityResult","beginCrop(Uri source)");
        try {
            sdFilePath = File.createTempFile("textscreenshot", ".jpg", saveChatPicPath);
            Uri destination = Uri.fromFile(sdFilePath);
            Crop.of(source, destination).asSquare().start(this);
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendEmptyMessage(2);
        }
    }

    /**
     * 将裁剪的数据进行处理
     * @param resultCode
     * @param result
     */
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            if (sdFilePath != null) {

                try {
                    Conversation c = JMessageClient.getSingleConversation(userName);
                    if (c == null) {
                        c = Conversation.createSingleConversation(userName);
                    }
                    ImageContent image = new ImageContent(sdFilePath);
                    Message message = c.createSendMessage(image);

                    Date date = new Date();
                    long time = date.getTime();
                    zmc_ChatBean chat = new zmc_ChatBean(userName,user.getPhone(),"",sdFilePath.getAbsolutePath(),time+"","1",0+"");
                    currentList.add(chat);
                    dao.add(chat);

                    msgSending.add(currentList.size()-1);
                    message.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseDesc) {
                            Log.e("BasicCallback","responseCode :"+responseCode);
                            try {
                                int index = msgSending.get(0);
                                msgSending.remove(0);
//                              position.clear();
                                if (responseCode == 0) {
                                    // 消息发送成功
                                    android.os.Message msg = handler.obtainMessage();
                                    msg.what = 3;
                                    msg.arg1 = index;
                                    handler.sendMessage(msg);
//                                  currentList.get(index).setMsgFlag(1);
                                } else {
                                    // 消息发送失败
                                    android.os.Message msg = handler.obtainMessage();
                                    msg.what = 4;
                                    msg.arg1 = index;
                                    handler.sendMessage(msg);
                                }
                            }catch (IndexOutOfBoundsException e){
                            }
                        }
                    });
                    JMessageClient.sendMessage(message);
                    handler.sendEmptyMessage(1);

                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(2);
                }
            } else {
                handler.sendEmptyMessage(2);
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
        }
    }

    /**正在发送的消息  消息未发送成功的下标集合**/
    List<Integer> msgSending = new ArrayList<>();
    /**
     * 发送消息
     */
    private void sendmessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //获取与医生的单聊会话
                    Conversation conversation = JMessageClient.getSingleConversation(userName);
                    if(conversation == null){
                        conversation = Conversation.createSingleConversation(userName);
                    }
                    //获取聊天信息
                    TextContent tc = new TextContent(msg_et.getText().toString());
                    //创建一条发送的消息，同时将消息保存至本地数据库
                    Message message = conversation.createSendMessage(tc);

                    Date date = new Date();
                    long time = date.getTime();

                    zmc_ChatBean chat = new zmc_ChatBean(userName,user.getPhone(),msg_et.getText().toString(),"",time+"","1",0+"");
                    currentList.add(chat);
                    dao.add(chat);
                    //当前发送的消息下标
                    msgSending.add(currentList.size()-1);
//                    final WeakReference<Integer> position = new WeakReference<Integer>(currentList.size()-1);
                    message.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseDesc) {
                            Log.e("BasicCallback","responseCode :"+responseCode);
                            try {
                                int index = msgSending.get(0);
                                msgSending.remove(0);
//                              position.clear();
                                if (responseCode == 0) {
                                    // 消息发送成功
                                    android.os.Message msg = handler.obtainMessage();
                                    msg.what = 3;
                                    msg.arg1 = index;
                                    handler.sendMessage(msg);
//                                  currentList.get(index).setMsgFlag(1);
                                } else {
                                    // 消息发送失败
                                    android.os.Message msg = handler.obtainMessage();
                                    msg.what = 4;
                                    msg.arg1 = index;
                                    handler.sendMessage(msg);
                                }
                            }catch (IndexOutOfBoundsException e){
                            }
                        }
                    });
                    //发送
                    JMessageClient.sendMessage(message);
                    handler.sendEmptyMessage(1);
                }catch (Exception e){
                    Log.e("imChatActivity","sendmessage  e : "+e.getMessage());
                }

            }
        }).start();
    }

    /**
     * 极光接受消息广播回调
     * @param event
     */
    public void onEventMainThread(MessageEvent event) {
        Message msg = event.getMessage();
        if(!isReply){
            isReply = true;
        }
        switch (msg.getContentType()) {
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                UserInfo info = (UserInfo) msg.getTargetInfo();
                Log.e("imChatActivity","userName = "+info.getUserName());

                if(info.getUserName().equals(userName)){//确定当前接受的消息是当前对话医生发的
                    Date date = new Date();
                    long time = date.getTime();
                    zmc_ChatBean chat = new zmc_ChatBean(userName,user.getPhone(),textContent.getText(),"",time+"","2",1+"");
                    currentList.add(chat);
                    dao.add(chat);
                    adapter.UpdateItemInserted(currentList,currentList.size());
                    recyclerView.smoothScrollToPosition(currentList.size()+1);
                }
                break;

            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
                break;
            case custom:
                //处理自定义消息
                CustomContent customContent = (CustomContent) msg.getContent();
                customContent.getNumberValue("custom_num"); //获取自定义的值
                customContent.getBooleanValue("custom_boolean");
                customContent.getStringValue("custom_string");
                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent)msg.getContent();
                switch (eventNotificationContent.getEventNotificationType()){
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件
                        break;
                    case group_member_exit:
                        //群成员退群事件
                        break;
                }
                break;
        }
    }

    /**
     * 登录
     */
    private void jmessage() {
        String username = user.getPhone();
        JMessageClient.login(username, "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    Log.e("jmessage", "登录成功");
                } else {
                    Log.e("jmessage", "登录失败");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //推荐在退出聊天会话界面时调用，清除当前正在聊天的对象，用于判断notification是否需要展示
        JMessageClient.exitConversation();
        HomeAcitivtyMy.isNeedReceive = true;
        if(isReply){ //进入评价
            Intent intent = new Intent(this,AddCommentActivity.class);
            intent.putExtra("docId",docID);
            startActivity(intent);
            finish();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
        dao.close();
        docDao.close();
    }
}
