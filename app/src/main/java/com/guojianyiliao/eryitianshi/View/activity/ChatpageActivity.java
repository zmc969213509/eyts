package com.guojianyiliao.eryitianshi.View.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.User_Http;
import com.guojianyiliao.eryitianshi.Data.entity.Chatcontent;
import com.guojianyiliao.eryitianshi.Data.entity.Inquiryrecord;
import com.guojianyiliao.eryitianshi.Data.entity.Userinfo;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.guojianyiliao.eryitianshi.Utils.db.ChatpageDao;
import com.guojianyiliao.eryitianshi.Utils.db.InquiryrecordDao;
import com.guojianyiliao.eryitianshi.page.adapter.ChatpageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.soundcloud.android.crop.Crop;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
/*import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;*/
import okhttp3.Call;

/**implements PtrUIHandler*/
public class ChatpageActivity extends MyBaseActivity  {
    private EditText et_chat;
    private ImageView iv_chat;
    private ListView listView;
    private TextView tv_doctorname;
    private RelativeLayout rl_loading;
    ChatpageAdapter adapter;
    List<Chatcontent> list;
    List<Chatcontent> historylist;
    Chatcontent chatcontent;
    String doctorname;
    String doctoricon;
    private File sdcardTempFile;
    private File sdcardTempFile1;
    private File audioFile;
    private Dialog setHeadDialog;
    private View dialogView;
    private LinearLayout ll_consult_return, ll_doctor_particulars;
    ChatpageDao db;
    String doctorID;
    String username;
    SharedPsaveuser sp = new SharedPsaveuser(ChatpageActivity.this);
    InquiryrecordDao inquiryrecorddb;
    List<Chatcontent> data;
    List<Inquiryrecord> ilist;

    private Button btn_chat;

    private LinearLayout ll_bottom_send_case;


   // private PtrClassicFrameLayout ptrClassicFrameLayout;


    int chatstate = 0;

    int chatrecordnumber = 1;

    List<Chatcontent> numberlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatpage);


        try {


            JMessageClient.registerEventReceiver(this);
            inquiryrecorddb = new InquiryrecordDao(this);

            audioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/text/chatprint/");
            audioFile.mkdirs();
            data = new ArrayList<>();


            db = new ChatpageDao(this);
            list = new ArrayList<>();
            historylist = new ArrayList<>();
            numberlist = new ArrayList<>();
            findView();
            init();
            addMyDoctor();

            JMessageClient.enterSingleConversation(username);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void addMyDoctor() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils
                        .post()
                        .url(Http_data.http_data + "/AddMyDoctor")
                        .addParams("userId", sp.getTag().getId() + "")
                        .addParams("doctorId", doctorID)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {

                            }
                        });
            }
        });

        thread.start();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.closedb();

        inquiryrecorddb.closedb();
    }


    private void jmessage() {

        String username = null;
        if (User_Http.user.getPhone() == null) {
            username = sp.getTag().getPhone();
        } else {
            username = User_Http.user.getPhone();
        }


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


    private void findView() {
        doctorID = getIntent().getStringExtra("doctorID");

        doctorname = getIntent().getStringExtra("name");

        doctoricon = getIntent().getStringExtra("icon");
        username = getIntent().getStringExtra("username");

        String page = getIntent().getStringExtra("page");


        iv_chat = (ImageView) findViewById(R.id.iv_chat);
        et_chat = (EditText) findViewById(R.id.et_chat);

        listView = (ListView) findViewById(R.id.listView);
        tv_doctorname = (TextView) findViewById(R.id.tv_doctorname);

        ll_doctor_particulars = (LinearLayout) findViewById(R.id.ll_doctor_particulars);

        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);


        ll_consult_return = (LinearLayout) findViewById(R.id.ll_consult_return);

        btn_chat = (Button) findViewById(R.id.btn_chat);

        ll_bottom_send_case = (LinearLayout) findViewById(R.id.ll_bottom_send_case);

        if (page.equals("1")) {
            ll_bottom_send_case.setVisibility(View.GONE);
        }


        tv_doctorname.setText(doctorname);

        listView.setVerticalScrollBarEnabled(false);

        /*ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptrClassicFrameLayout);
        ptrClassicFrameLayout.setPadding(0, 0, 0, 50);

        ptrClassicFrameLayout.setDurationToCloseHeader(1000);*/


        ll_doctor_particulars.setOnClickListener(doctorlisntener);

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);


        listView.setOnItemClickListener(listvlistener);

        iv_chat.setOnClickListener(lisntener);
        ll_consult_return.setOnClickListener(lisntener);

        btn_chat.setOnClickListener(btnlistener);


    }


    private View.OnClickListener doctorlisntener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ChatpageActivity.this, DoctorparticularsActivity.class);
            intent.putExtra("doctot_id", doctorID);
            startActivity(intent);
        }
    };


    private void init() {
        historylist = db.chatfind(username);
        for (int i = 0; i < historylist.size(); i++) {
            if (historylist.get(i).getMyname().equals(sp.getTag().getPhone())) {
                numberlist.add(historylist.get(i));

            }
        }

        if (numberlist.size() > (chatrecordnumber * 10)) {
            for (int j = numberlist.size() - (chatrecordnumber * 10); j < numberlist.size(); j++) {

                list.add(numberlist.get(j));
            }
        } else {
            for (int i = 0; i < numberlist.size(); i++) {
                list.add(numberlist.get(i));
            }
        }

        adapter = new ChatpageAdapter(this, list, doctoricon);
        listView.setAdapter(adapter);

        listView.setStackFromBottom(true);

        adapter.notifyDataSetChanged();


       /* ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                historylist.clear();
                numberlist.clear();
                list.clear();
                chatrecordnumber++;
                historylist = db.chatfind(username);
                for (int i = 0; i < historylist.size(); i++) {
                    if (historylist.get(i).getMyname().equals(sp.getTag().getPhone())) {
                        numberlist.add(historylist.get(i));
                    }
                }
                ptrClassicFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (numberlist.size() > (chatrecordnumber * 10)) {
                            for (int j = numberlist.size() - (chatrecordnumber * 10); j < numberlist.size(); j++) {
                                list.add(numberlist.get(j));
                            }

                            handler.sendEmptyMessage(4);
                        } else {
                            for (int i = 0; i < numberlist.size(); i++) {
                                list.add(numberlist.get(i));
                            }

                            handler.sendEmptyMessage(5);
                        }


                    }
                    //设定加载更多聊天记录需要的时间
                }, 1000);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, content, header);
            }
        });*/

    }

    private View.OnClickListener btnlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (et_chat.getText().length() == 0 || et_chat.getText().toString().trim().equals("")) {

            } else {

                try {
                    sendmessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ChatpageActivity.this, "发送失败，请重新发送", Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


    private void sendmessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    Conversation c = JMessageClient.getSingleConversation(username);
                    if (c == null) {
                        c = Conversation.createSingleConversation(username);

                    }
                    TextContent textContent = new TextContent(et_chat.getText().toString());

                    Message message = c.createSendMessage(textContent);

                    JMessageClient.sendMessage(message);

                    Date dt = new Date();
                    Long time = dt.getTime();

                    String content = "1" + et_chat.getText().toString();
                    chatcontent = new Chatcontent(content, time, null, null, username, sp.getTag().getPhone());

                    list.add(chatcontent);

                    handler.sendEmptyMessage(6);

                } catch (Exception e) {
                    e.printStackTrace();

                    jmessage();
                    handler.sendEmptyMessage(2);
                }
            }
        }).start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    try {

                        adapter.notifyDataSetChanged();
                        et_chat.setText("");
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    break;
                case 1:

                    break;
                case 2:
                    Toast.makeText(ChatpageActivity.this, "发送失败，请重新发送", Toast.LENGTH_SHORT).show();
                    break;

                case 3:
                    Toast.makeText(ChatpageActivity.this, "发送图片失败，请稍后再试", Toast.LENGTH_SHORT).show();
                    break;

                case 4:
                    adapter.notifyDataSetChanged();

                    listView.setSelection(9);

              //      ptrClassicFrameLayout.refreshComplete();
                    break;
                case 5:
                    adapter.notifyDataSetChanged();
              //      ptrClassicFrameLayout.refreshComplete();
                    Toast.makeText(ChatpageActivity.this, "已经没有消息记录了", Toast.LENGTH_SHORT).show();
                    break;

                case 6:
                    adapter.notifyDataSetChanged();

                    et_chat.setText("");

                    db.addchatcont(chatcontent);

                    chatstate++;
                    break;

            }
        }
    };


    private View.OnClickListener lisntener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.ll_consult_return:

                    JMessageClient.exitConversation();
                    finish();
                    break;

                case R.id.iv_chat:
                    try {
//
//                        sendpictureDialog();

                        selectPrintDialog();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ChatpageActivity.this, "打开图库失败，请检查是否开启权限或稍后再试", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }

        }
    };

    private void selectPrintDialog() {
        jmessage();


        setHeadDialog = new Dialog(this, R.style.CustomDialog);

        setHeadDialog.show();

        dialogView = View.inflate(getApplicationContext(), R.layout.chat_select_print, null);


        setHeadDialog.getWindow().setContentView(dialogView);

        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();

        setHeadDialog.getWindow().setAttributes(lp);

        RelativeLayout rl_confirm = (RelativeLayout) dialogView.findViewById(R.id.rl_confirm);

        RelativeLayout lr_cancel = (RelativeLayout) dialogView.findViewById(R.id.lr_cancel);

        rl_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    setHeadDialog.dismiss();

                    Crop.pickImage(ChatpageActivity.this);

                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        });

        lr_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();

        JMessageClient.exitConversation();
    }


    private void sendpictureDialog() {
        jmessage();

        setHeadDialog = new Dialog(this, R.style.CustomDialog);
        setHeadDialog.show();
        dialogView = View.inflate(getApplicationContext(), R.layout.chatpage_picture_dialog, null);

        setHeadDialog.getWindow().setContentView(dialogView);
        WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();
        setHeadDialog.getWindow().setAttributes(lp);
        sendpictureclick();
    }


    private void sendpictureclick() {

        Button btn_camera = (Button) dialogView.findViewById(R.id.btn_camera);
        Button btn_cutout = (Button) dialogView.findViewById(R.id.btn_cutout);
        Button btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent1, 1);
                    setHeadDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ChatpageActivity.this, "开启相机失败，请检查是否开启权限或稍后再试", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cutout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Crop.pickImage(ChatpageActivity.this);
                    setHeadDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ChatpageActivity.this, "打开图库失败，请检查是否开启权限或稍后再试", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHeadDialog.dismiss();
            }
        });

    }


    private void beginCrop(Uri source) {
        try {
            sdcardTempFile = File.createTempFile("textscreenshot", ".jpg", audioFile);
            Uri destination = Uri.fromFile(sdcardTempFile);

            Crop.of(source, destination).asSquare().start(this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ChatpageActivity.this, "选择图片失败，请稍后再试", Toast.LENGTH_SHORT).show();

        }
    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            if (sdcardTempFile != null) {
                try {
                    Conversation c = JMessageClient.getSingleConversation(username);
                    if (c == null) {
                        c = Conversation.createSingleConversation(username);
                    }

                    ImageContent image = new ImageContent(sdcardTempFile);

                    Message message = c.createSendMessage(image);
                    JMessageClient.sendMessage(message);

                    chatcontent = new Chatcontent("1*1", 0L, sdcardTempFile.getAbsolutePath(), sdcardTempFile.getAbsolutePath(), username, sp.getTag().getPhone());

                    list.add(chatcontent);
                    adapter.notifyDataSetChanged();
                    et_chat.setText("");
                    db.addchatcont(chatcontent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ChatpageActivity.this, "发送图片失败，请重新发送", Toast.LENGTH_SHORT).show();
                }
            } else {
                handler.sendEmptyMessage(3);
            }

            chatstate++;

        } else if (resultCode == Crop.RESULT_ERROR) {
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        JMessageClient.exitConversation();
    }


    private AdapterView.OnItemClickListener listvlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (list.get(position).getMasterfile() != null) {

                setHeadDialog = new Dialog(ChatpageActivity.this, R.style.Dialog_Fullscreen);

                setHeadDialog.show();
                dialogView = View.inflate(getApplicationContext(), R.layout.chat_imgv_dialog, null);

                ImageView iv_chat_dial = (ImageView) dialogView.findViewById(R.id.iv_chat_dial);


                ImageLoader.getInstance().displayImage("file:///" + list.get(position).getMasterfile(), iv_chat_dial);

                setHeadDialog.getWindow().setContentView(dialogView);

                WindowManager.LayoutParams lp = setHeadDialog.getWindow().getAttributes();

                setHeadDialog.getWindow().setAttributes(lp);

                RelativeLayout rl_chat_diss = (RelativeLayout) dialogView.findViewById(R.id.rl_chat_diss);


                rl_chat_diss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setHeadDialog.dismiss();
                    }
                });
            }

        }
    };


    public void onEventMainThread(MessageEvent event) {
        Message msg = event.getMessage();
        switch (msg.getContentType()) {
            case text:

                TextContent textContent = (TextContent) msg.getContent();

                String content = "2" + textContent.getText();
                Date dt = new Date();
                Long time = dt.getTime();

                if (msg.getTargetID().equals(username)) {
                    chatcontent = new Chatcontent(content, time, null, null, msg.getTargetID(), sp.getTag().getPhone());
                    list.add(chatcontent);
                    adapter.notifyDataSetChanged();
                }
                break;

            case image:

                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();
                String file = imageContent.getLocalThumbnailPath();

                Date dt1 = new Date();
                Long time1 = dt1.getTime();

                chatcontent = new Chatcontent("2*2", time1, file, file, msg.getTargetID(), sp.getTag().getPhone());
                if (msg.getTargetID().equals(username)) {
                    list.add(chatcontent);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent result) {

        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());

        } else if (requestCode == Crop.REQUEST_CROP) {
            try {

                handleCrop(resultCode, result);

            } catch (Exception e) {
                Toast.makeText(ChatpageActivity.this, "发送图片失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }

        } else if (resultCode == RESULT_OK && requestCode == 1) {


            Bundle bundle = result.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            FileOutputStream fileOutputStream = null;
            try {
                sdcardTempFile1 = File.createTempFile("textcamera", ".jpg", audioFile);
                fileOutputStream = new FileOutputStream(sdcardTempFile1);
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
                Conversation c = JMessageClient.getSingleConversation(username);

                if (c == null) {
                    c = Conversation.createSingleConversation(username);

                }

                ImageContent image = new ImageContent(sdcardTempFile1);

                Message message = c.createSendMessage(image);

                JMessageClient.sendMessage(message);

                chatcontent = new Chatcontent("1*1", 0L, sdcardTempFile1.getAbsolutePath(), sdcardTempFile1.getAbsolutePath(), username, sp.getTag().getPhone());
                list.add(chatcontent);
                adapter.notifyDataSetChanged();
                et_chat.setText("");
                db.addchatcont(chatcontent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(ChatpageActivity.this, "发送失败，请重新发送", Toast.LENGTH_SHORT).show();
            }

            chatstate++;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            JPushInterface.onPause(this);
            int j = 0;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);

            if (User_Http.user.getPhone() != null) {
                ilist = inquiryrecorddb.chatfind(sp.getTag().getPhone());
            } else {
                SharedPsaveuser sp = new SharedPsaveuser(this);
                Userinfo userinfo = sp.getTag();
                ilist = inquiryrecorddb.chatfind(userinfo.getPhone());
            }


            for (int i = 0; i < ilist.size(); i++) {
                if (username.equals(ilist.get(i).getDoctorid())) {
                    j++;
                }
            }


            if (j == 0 && chatstate != 0) {
                Inquiryrecord inquiryrecord = new Inquiryrecord(sp.getTag().getPhone(), username, doctorID, doctorname, doctoricon, str, "");
                inquiryrecorddb.addInquiryrecord(inquiryrecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    int l = 0;

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);


//        //动态获取相机权限
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//            //申请WRITE_EXTERNAL_STORAGE权限
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);
//        }


        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 7);

        }


    }


    /*@Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        l = list.size();

    }


    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }*/


}
