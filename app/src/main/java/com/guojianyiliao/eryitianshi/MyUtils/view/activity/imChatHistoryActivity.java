package com.guojianyiliao.eryitianshi.MyUtils.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.zmc_DocChat;
import com.guojianyiliao.eryitianshi.MyUtils.db.DoctorDao;
import com.guojianyiliao.eryitianshi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zmc on 2017/6/2.
 * 我的问诊记录
 */

public class imChatHistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.chat_history_listview)
    ListView listView;
    myAdapter adapter;

    DoctorDao dao;
    List<zmc_DocChat> allDoc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zmc_activity_chat_history);
        ButterKnife.bind(this);
        getData();
    }

    public void getData() {
        dao = new DoctorDao(this);
        allDoc = dao.getAllDoc();
        adapter = new myAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @OnClick(R.id.chat_history_back)
    public void back(){
        onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(imChatHistoryActivity.this, imChatActivity.class);
        intent.putExtra("name", allDoc.get(position).getDocName());
        intent.putExtra("icon", allDoc.get(position).getDocIcon());
        intent.putExtra("doctorID", allDoc.get(position).getDocId());
        intent.putExtra("username", allDoc.get(position).getUserName());
        intent.putExtra("flag", "2");
        startActivity(intent);
    }


    class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return allDoc.size();
        }

        @Override
        public Object getItem(int position) {
            return allDoc.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            viewHolder vh ;
            if(convertView == null){
                vh = new viewHolder();
                convertView = LayoutInflater.from(imChatHistoryActivity.this).inflate(R.layout.zmc_item_chat_history,null);
                vh.icon = (ImageView) convertView.findViewById(R.id.item_chat_history_img);
                vh.name = (TextView) convertView.findViewById(R.id.item_chat_history_name);
                convertView.setTag(vh);
            }else{
                vh = (viewHolder) convertView.getTag();
            }
            ImageLoader.getInstance().displayImage(allDoc.get(position).getDocIcon(), vh.icon );
            vh.name.setText(allDoc.get(position).getDocName()+"");
            return convertView;
        }
        class viewHolder{
            ImageView icon;
            TextView name;
        }
    }
}
