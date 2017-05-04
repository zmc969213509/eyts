package com.guojianyiliao.eryitianshi.View.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.guojianyiliao.eryitianshi.Data.entity.Pharmacyremind;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;
import com.guojianyiliao.eryitianshi.Utils.SharedPsaveuser;
import com.guojianyiliao.eryitianshi.page.adapter.PharmacyremindAdapter;

import java.util.List;


/**
 */
public class PharmacyremindActivity extends MyBaseActivity {
    private LinearLayout ll_return;
    PharmacyremindAdapter adapter;
    List<Pharmacyremind> list;
    SharedPsaveuser sp;
    ListView lv_pharmacy_remind;
    private String remindId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacyremind);
        try {

            findView();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void findView() {
        remindId = getIntent().getStringExtra("remindId");
        ll_return = (LinearLayout) findViewById(R.id.ll_return);
        lv_pharmacy_remind = (ListView) findViewById(R.id.lv_pharmacy_remind);
        lv_pharmacy_remind.setVerticalScrollBarEnabled(false);
        ll_return.setOnClickListener(listener);

    }




    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_return:
                    finish();
                    break;
            }
        }
    };


}
