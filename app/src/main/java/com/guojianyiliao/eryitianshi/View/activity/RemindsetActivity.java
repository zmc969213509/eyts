package com.guojianyiliao.eryitianshi.View.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.Utils.MyBaseActivity;

/**
 *
 */
public class RemindsetActivity extends MyBaseActivity implements View.OnClickListener {
    private LinearLayout ll_remidsetreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remindset);
        try {

            findView();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findView() {
        ll_remidsetreturn = (LinearLayout) findViewById(R.id.ll_remidsetreturn);
        ll_remidsetreturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_remidsetreturn:
                finish();
                break;
        }
    }
}
