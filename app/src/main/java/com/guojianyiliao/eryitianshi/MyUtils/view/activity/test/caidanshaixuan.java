package com.guojianyiliao.eryitianshi.MyUtils.view.activity.test;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zxl.library.DropDownMenu;
import com.guojianyiliao.eryitianshi.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 *
 */

public class caidanshaixuan  extends AppCompatActivity {

    DropDownMenu mDropDownMenu;
    private String headers[] = {"科室","职称","挂号"};
    //private int[] types = new int[]{DropDownMenu.TYPE_LIST_CITY, DropDownMenu.TYPE_LIST_SIMPLE, DropDownMenu.TYPE_CUSTOM, DropDownMenu.TYPE_GRID};
    //private int[] types = new int[]{DropDownMenu.TYPE_LIST_CITY,DropDownMenu.TYPE_CUSTOM};
    private String keshi[] = {"不限", "科室1", "科室2", "科室3", "科室4", "科室5", "科室6", "科室7", "科室8", "科室9"};
    private String zhicheng[] = {"不限","职称1","职称2","职称3","职称4"};
    private String guahao[] = {"不限", "挂号1", "挂号2", "挂号3", "挂号4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_tt_activity_main);
        mDropDownMenu= (DropDownMenu) findViewById( R.id.dropDownMenu);
      //   contentView = getLayoutInflater().inflate(R.layout.contentview, null);
        initView();

    }

    private void initView() {
        View contentView = getLayoutInflater().inflate(R.layout.aa_tt_contentview, null);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), initViewData(), contentView);
        //该监听回调只监听默认类型，如果是自定义view请自行设置，参照demo

    }

    /**
     * 设置类型和数据源：
     * DropDownMenu.KEY对应类型（DropDownMenu中的常量，参考上述核心源码）
     * DropDownMenu.VALUE对应数据源：key不是TYPE_CUSTOM则传递string[],key是TYPE_CUSTOM类型则传递对应view
     */
    private List<HashMap<String, Object>> initViewData() {
        List<HashMap<String, Object>> viewDatas = new ArrayList<>();
        HashMap<String, Object> map;
        for (int i = 0; i < headers.length; i++) {
            map = new HashMap<String, Object>();
            map.put(DropDownMenu.KEY, DropDownMenu.TYPE_CUSTOM);

            if (i == 0) {
                map.put(DropDownMenu.VALUE, keshi);
                map.put(DropDownMenu.SELECT_POSITION, 0);
            } else if (i == 1) {
                map.put(DropDownMenu.VALUE, zhicheng);
                map.put(DropDownMenu.SELECT_POSITION, 0);
            } else {
                map.put(DropDownMenu.VALUE, guahao);
                map.put(DropDownMenu.SELECT_POSITION, 0);
            }

            viewDatas.add(map);
        }
        return viewDatas;
    }

    private View getCustomView() {
        View v = getLayoutInflater().inflate(R.layout.aa_tt_custom, null);
        TextView btn = (TextView) v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDropDownMenu.setTabText(2,"自定义");//设置tab标签文字
                mDropDownMenu.closeMenu();//关闭menu
            }
        });
        return v;
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}