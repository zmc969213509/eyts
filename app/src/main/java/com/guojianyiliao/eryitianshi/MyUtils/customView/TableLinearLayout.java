package com.guojianyiliao.eryitianshi.MyUtils.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmc on 2017/5/17.
 * 医生排班表格
 */

public class TableLinearLayout extends LinearLayout{
    /**标题文本**/
    List<String> title;
    /**排班信息  0：上午 1：下午 2：全天 3：休息**/
    List<Integer> data;

    /**控件高**/
    int height;
    /**控件宽**/
    int width;

    /**线段颜色**/
    int line_color;
    /**视图背景颜色**/
    int view_bg;
    /**字体颜色**/
    int text_color;
    /**字体大小**/
    int text_size;

    /**三个子linearLayout视图**/
    List<LinearLayout> layouts = new ArrayList<>();
    /**title视图的显示的子textview  8个 **/
    List<TextView> title_list = new ArrayList<>();
    /**上午下午视图显示的子imageview 14个**/
    List<ImageView> img_list = new ArrayList<>();

    public TableLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public TableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TableLinearLayout);
        line_color = typedArray.getColor(R.styleable.TableLinearLayout_line_color, Color.BLACK);
        view_bg = typedArray.getColor(R.styleable.TableLinearLayout_line_color, Color.GRAY);
        text_size = typedArray.getDimensionPixelSize(R.styleable.TableLinearLayout_table_text_size, 15);
        text_color = typedArray.getColor(R.styleable.TableLinearLayout_table_text_color, Color.BLACK);
        init(context);
    }

    public TableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp_layout = new LayoutParams(LayoutParams.MATCH_PARENT, 0 , 1.0f);
        LinearLayout.LayoutParams lp_view = new LayoutParams(LayoutParams.MATCH_PARENT, 1);



        for (int i = 0; i < 3 ; i++) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(lp_layout);
            //水平线段分割线
            View view_horizontal = new View(context);
            view_horizontal.setBackgroundColor(line_color);
            view_horizontal.setLayoutParams(lp_view);
            layouts.add(linearLayout);
            this.addView(view_horizontal);
            this.addView(linearLayout);
        }
        //水平线段分割线
        View view_horizontal = new View(context);
        view_horizontal.setBackgroundColor(line_color);
        view_horizontal.setLayoutParams(lp_view);
        this.addView(view_horizontal);


        lp_view = new LayoutParams( 1,LayoutParams.MATCH_PARENT);

        LinearLayout.LayoutParams lp_tv = new LayoutParams( 0,LayoutParams.MATCH_PARENT,1.0f);
        //为title_linearlayout 填充视图
        for (int i = 0; i < 8 ; i++) {
            //竖直线段分割线
            View view_vertical = new View(context);
            view_vertical.setBackgroundColor(line_color);
            view_vertical.setLayoutParams(lp_view);
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundResource(R.color.grays);
//            tv.setBackgroundColor(view_bg);
            tv.setLayoutParams(lp_tv);
            title_list.add(tv);
            layouts.get(0).addView(view_vertical);
            layouts.get(0).addView(tv);
            if(i == 0){
                //竖直线段分割线
                View view_vertical1 = new View(context);
                view_vertical1.setBackgroundColor(line_color);
                view_vertical1.setLayoutParams(lp_view);
                TextView tv1 = new TextView(context);
                tv1.setGravity(Gravity.CENTER);
                tv1.setLayoutParams(lp_tv);
                layouts.get(1).addView(view_vertical1);
                layouts.get(1).addView(tv1);
                //竖直线段分割线
                TextView tv2 = new TextView(context);
                tv2.setGravity(Gravity.CENTER);
                tv2.setLayoutParams(lp_tv);
                View view_vertical2 = new View(context);
                view_vertical2.setBackgroundColor(line_color);
                view_vertical2.setLayoutParams(lp_view);
                layouts.get(2).addView(view_vertical2);
                layouts.get(2).addView(tv2);
            }
        }
        //竖直线段分割线
        View view_vertical1 = new View(context);
        view_vertical1.setBackgroundColor(line_color);
        view_vertical1.setLayoutParams(lp_view);
        layouts.get(0).addView(view_vertical1);

        //为显示排班信息控件填充视图
        for (int i = 0; i < 7 ; i++) {
            //竖直线段分割线
            View view_vertical = new View(context);
            view_vertical.setBackgroundColor(line_color);
            view_vertical.setLayoutParams(lp_view);
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(lp_tv);
            img_list.add(iv);
            layouts.get(1).addView(view_vertical);
            layouts.get(1).addView(iv);
        }
        View view_vertical2 = new View(context);
        view_vertical2.setBackgroundColor(line_color);
        view_vertical2.setLayoutParams(lp_view);
        layouts.get(1).addView(view_vertical2);
        //为显示排班信息控件填充视图
        for (int i = 0; i < 7 ; i++) {
            //竖直线段分割线
            View view_vertical = new View(context);
            view_vertical.setBackgroundColor(line_color);
            view_vertical.setLayoutParams(lp_view);
            ImageView iv = new ImageView(context);
            iv.setLayoutParams(lp_tv);
            img_list.add(iv);
            layouts.get(2).addView(view_vertical);
            layouts.get(2).addView(iv);
        }
        View view_vertical3 = new View(context);
        view_vertical3.setBackgroundColor(line_color);
        view_vertical3.setLayoutParams(lp_view);
        layouts.get(2).addView(view_vertical3);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    public void setData(List<String> title , List<Integer> data){
        this.data = data;
        this.title = title;

        for (int i = 0; i < title.size() ; i++) {
            if(title_list.size() > i+1){
                title_list.get(i+1).setTextColor(text_color);
                title_list.get(i+1).setTextSize(TypedValue.COMPLEX_UNIT_PX,text_size);
                title_list.get(i+1).setText(title.get(i));
            }
        }
        ((TextView)layouts.get(1).getChildAt(1)).setText("上午");
        ((TextView)layouts.get(1).getChildAt(1)).setTextColor(text_color);
        ((TextView)layouts.get(1).getChildAt(1)).setTextSize(TypedValue.COMPLEX_UNIT_PX,text_size);
        ((TextView)layouts.get(2).getChildAt(1)).setText("下午");
        ((TextView)layouts.get(2).getChildAt(1)).setTextColor(text_color);
        ((TextView)layouts.get(2).getChildAt(1)).setTextSize(TypedValue.COMPLEX_UNIT_PX,text_size);

        for (int i = 0; i < data.size() ; i++) {
            if(data.get(i) == 0){//上午
                img_list.get(i).setImageResource(R.drawable.work_iocn);
            }else if(data.get(i) == 1){//下午
                img_list.get(i+7).setImageResource(R.drawable.work_iocn);
            }else if(data.get(i) == 2){//全天
                img_list.get(i).setImageResource(R.drawable.work_iocn);
                img_list.get(i+7).setImageResource(R.drawable.work_iocn);
            }
        }
    }
}
