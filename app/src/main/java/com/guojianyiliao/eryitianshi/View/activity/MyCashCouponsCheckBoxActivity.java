package com.guojianyiliao.eryitianshi.View.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.guojianyiliao.eryitianshi.Data.Http_data;
import com.guojianyiliao.eryitianshi.Data.entity.CouponsBean;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.IListener;
import com.guojianyiliao.eryitianshi.Utils.ListenerManager;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;


public class MyCashCouponsCheckBoxActivity extends AppCompatActivity implements IListener {
    private LinearLayout ll_rut;
    private ListView lv_mycash;
    private TextView tv_checkbox;
    MyCashCouponsCheckBoxAdapter adapter;
    List<String> list = new ArrayList<>();
    private List<CouponsBean> couponsdata;

    ArrayList<String> myid = new ArrayList<>();
    ArrayList<String> mymoney = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cash_coupons);
        try {
            ListenerManager.getInstance().registerListtener(this);
            findView();
            init();
            myid.clear();
            mymoney.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void findView() {
        //
        ll_rut = (LinearLayout) findViewById(R.id.ll_rut);

        lv_mycash = (ListView) findViewById(R.id.lv_mycash);
        tv_checkbox = (TextView) findViewById(R.id.tv_checkbox);

        ll_rut.setOnClickListener(listener);
        tv_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                try {
                    ArrayList myid1 = new ArrayList();
                    for (Iterator it = myid.iterator(); it.hasNext(); ) {
                        Object obj = it.next();
                        if (!myid1.contains(obj))
                            myid1.add(obj);
                    }
                    ArrayList mymoney1 = new ArrayList();
                    intent.putStringArrayListExtra("id", myid1);
                    intent.putStringArrayListExtra("money", mymoney);
                    MyLogcat.jLog().e("myid1:" + myid1.size());
                } catch (Exception e) {
                    MyLogcat.jLog().e("" + e);
                }
                setResult(200, intent);
                finish();
            }
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_rut:
                    finish();
                    break;
            }
        }
    };

    private List<CouponsBean> couponsBeen;

    private void init() {
        OkHttpUtils.
                get()
                .url(Http_data.http_data + "/getMyVoucher")
                .addParams("userId", new SharedPsaveuser(MyCashCouponsCheckBoxActivity.this).getTag().getId() + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyLogcat.jLog().e("onError onError");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Type listtype = new TypeToken<LinkedList<CouponsBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        couponsBeen = new ArrayList<>();
                        LinkedList<CouponsBean> leclist = gson.fromJson(response, listtype);
                        for (Iterator it = leclist.iterator(); it.hasNext(); ) {
                            CouponsBean coupons = (CouponsBean) it.next();
                            couponsBeen.add(coupons);
                        }
                        adapter = new MyCashCouponsCheckBoxAdapter(MyCashCouponsCheckBoxActivity.this, couponsBeen);
                        lv_mycash.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void notifyAllActivity(String str) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ListenerManager.getInstance().unRegisterListener(this);

    }

    class MyCashCouponsCheckBoxAdapter extends BaseAdapter {
        private Context context;
        private List<CouponsBean> list;

        public MyCashCouponsCheckBoxAdapter(Context context, List<CouponsBean> list) {
            this.context = context;
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CouponsBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.mycashcoupons_listview_checkbox_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    try {
                        if (b) {
                            myid.add(list.get(position).getId() + "");
                            mymoney.add(list.get(position).getMoney() + "");
                            MyLogcat.jLog().e("put ID size:" + myid.size());
                            MyLogcat.jLog().e("put ID:" + list.get(position).getId());
                            MyLogcat.jLog().e("put money:" + list.get(position).getMoney());
                        } else {
                            myid.remove(list.get(position).getId() + "");
                            mymoney.remove(list.get(position).getMoney() + "");
                            MyLogcat.jLog().e("remove ID size:" + myid.size());
                            MyLogcat.jLog().e("remove ID:" + list.get(position).getId());
                        }
                    } catch (Exception e) {
                        MyLogcat.jLog().e(e);
                    }

                }
            });
            viewHolder.money.setText("￥" + (int) list.get(position).getMoney());
            String s = list.get(position).getEndTime().substring(0, 10);
            // String substring = list.get(position).getEndTime().substring(8, 10);
            viewHolder.time.setText("请在" + s + "前使用");
            return convertView;
        }

        class ViewHolder {
            TextView money;
            TextView time;
            CheckBox cb;

            ViewHolder(View v) {
                money = (TextView) v.findViewById(R.id.tv_money);
                time = (TextView) v.findViewById(R.id.tv_valid_time);
                cb = (CheckBox) v.findViewById(R.id.cb_item);

            }
        }

    }
}
