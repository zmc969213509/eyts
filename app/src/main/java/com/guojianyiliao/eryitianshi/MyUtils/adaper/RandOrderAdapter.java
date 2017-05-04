package com.guojianyiliao.eryitianshi.MyUtils.adaper;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.MyUtils.bean.RandOrderBean;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.MyLogcat;
import com.guojianyiliao.eryitianshi.MyUtils.utlis.UIUtils;
import com.guojianyiliao.eryitianshi.R;
import com.guojianyiliao.eryitianshi.View.activity.DoctorparticularsActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 首页随机医生
 * jnluo
 */

public class RandOrderAdapter extends RecyclerView.Adapter {

    @BindView(R.id.rand_order_icon)
    ImageButton randOrderIcon;
    @BindView(R.id.rand_order_type)
    TextView randOrderType;
    @BindView(R.id.rand_order_detail)
    TextView randOrderDetail;
    List<RandOrderBean> orderData;

    public RandOrderAdapter(List<RandOrderBean> orderData) {
        this.orderData = orderData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.getinflate(R.layout.a_fragment_rand_order);
        RandOrderHolder holder = new RandOrderHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        try {
            RandOrderHolder viewholder = (RandOrderHolder) holder;
            viewholder.name.setText(orderData.get(position).getName());//NullPointerException，name没抽取变量，擦
            ImageLoader.getInstance().displayImage(orderData.get(position).getIcon(), viewholder.icon);
            viewholder.type.setText(orderData.get(position).getTitle());
            viewholder.detail.setText("擅长：" + orderData.get(position).getAdept());

            viewholder.foot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UIUtils.getContext(), DoctorparticularsActivity.class);
                    intent.putExtra("doctot_id", orderData.get(position).getDoctorid());
                    intent.putExtra("doctor_chatcost", orderData.get(position).getChatcost());
                    UIUtils.getContext().startActivity(intent);
                }
            });
        } catch (Exception e) {
            MyLogcat.jLog().e("Exception：" + e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        MyLogcat.jLog().e("rand size :" + orderData.size());
        return orderData.size();
    }

    class RandOrderHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CircleImageView icon;
        private TextView type;
        private TextView detail;
        private LinearLayout foot;

        public RandOrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(UIUtils.getContext(), itemView);
            name = (TextView) itemView.findViewById(R.id.rand_order_name);
            icon = (CircleImageView) itemView.findViewById(R.id.rand_order_icon);
            type = (TextView) itemView.findViewById(R.id.rand_order_type);
            detail = (TextView) itemView.findViewById(R.id.rand_order_detail);
            foot = (LinearLayout) itemView.findViewById(R.id.ll_foot);

        }
    }
}
