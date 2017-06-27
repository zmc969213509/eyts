package com.guojianyiliao.eryitianshi.MyUtils.customView;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmc on 2017/5/15.
 * 自定义显示文本布局
 */

public class ShowTextLinearLayout extends LinearLayout {

    /**
     * 控件整体宽度
     **/
    private int width;
    /**背景**/
    private int BackGround;
    /**字体颜色**/
    private int text_color;
    /**
     * 左右控件magrin
     **/
    private int margin_width;
    /**
     * 最小的左右padding
     **/
    private int min_padding_width;
    /**
     * 最大的左右padding
     **/
    private int max_padding_width;
    /**
     * 上下padding
     **/
    private int padding_height;
    /**
     * textview 字大小
     **/
    private int text_size;
    /**
     * 屏幕宽度
     **/
    private int screenWith;
    /**
     * 当前显示记录的行数
     **/
    int index = 1;
    /**
     * 当前行数显示的个数
     **/
    int num = 0;

    /**
     * 显示内容集合
     **/
    private List<String> showList = new ArrayList<>();
    private onItemClickListener listener;
    private Context context;
    /**
     * 保存当前显示的textview
     **/
    private List<TextView> tv_list = new ArrayList<>();
    /**
     * 保存显示textview的子布局集合
     **/
    private List<LinearLayout> layout_list = new ArrayList<>();

    public ShowTextLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public ShowTextLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShowTextLinearLayout);
        margin_width = typedArray.getDimensionPixelSize(R.styleable.ShowTextLinearLayout_margin_width, 24);
        min_padding_width = typedArray.getDimensionPixelSize(R.styleable.ShowTextLinearLayout_min_padding_width, 24);
        max_padding_width = typedArray.getDimensionPixelSize(R.styleable.ShowTextLinearLayout_max_padding_width, 72);
        padding_height = typedArray.getDimensionPixelSize(R.styleable.ShowTextLinearLayout_padding_height, 17);
//        text_size = typedArray.getDimensionPixelSize(R.styleable.ShowTextLinearLayout_text_size, 13);
        text_size = (int)typedArray.getDimension(R.styleable.ShowTextLinearLayout_text_size, (float) 13.0);
        text_color = typedArray.getColor(R.styleable.ShowTextLinearLayout_text_color, Color.BLACK);
        BackGround = typedArray.getResourceId(R.styleable.ShowTextLinearLayout_text_bg,R.drawable.shape_text_bg);
        Log.e("ShowTextLinearLayout","margin_width:"+margin_width);
        Log.e("ShowTextLinearLayout","min_padding_width:"+min_padding_width);
        Log.e("ShowTextLinearLayout","max_padding_width:"+max_padding_width);
        Log.e("ShowTextLinearLayout","padding_height:"+padding_height);
        Log.e("ShowTextLinearLayout","text_size:"+text_size);
        typedArray.recycle();
        init(context);
    }

    public ShowTextLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.e("onMeasures","width =" + MeasureSpec.getSize(widthMeasureSpec));
//        Log.e("onMeasures","height =" + MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        this.context = context;

    }

    /**
     * 移除所有显示视图
     */
    public void remove(){
        this.removeAllViews();
        showList.removeAll(showList);
        layout_list.removeAll(layout_list);
        tv_list.removeAll(tv_list);
        index = 1;
        num = 0;
    }

    /**
     * 设置所选position 颜色
     */
    public void setColoer(int position,int coloers){
        for (int i = 0; i < tv_list.size(); i++) {
            tv_list.get(i).setTextColor(text_color);
        }
        tv_list.get(position).setTextColor(coloers);
    }


    /**
     * 设置数据源
     * @param lists
     */
    public void setData(List<String> lists) {
        this.showList = lists;
        if (showList.size() == 0) {
            return;
        }
        Calculation();
    }

    public void setData(List<String> lists,Context context){
        this.showList = lists;
        this.context = context;
        if (showList.size() == 0) {
            return;
        }
        Calculation();
    }

    /**
     * 进行计算
     */
    private void Calculation(){
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(0,15,0,15);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,15,0,15);
        linearLayout.setLayoutParams(lp);
        addView(linearLayout);
        layout_list.add(linearLayout);
        /**
         * 计算出需要添加的布局 动态添加子linearLayout
         */
        screenWith = getScreenWith();
        Log.e("setData","获取到宽度为："+screenWith);
        screenWith -= 2 * margin_width;
        TextView tv = new TextView(context);
        // 思路  先以最小左右padding间距计算出一行最多可以显示几个历史  在对字的宽度 屏幕宽度 去确认实际显示的左右padding为多少  然后去显示
        for (int i = 0; i < showList.size(); i++) {
            String name = showList.get(i);
            float textWidth = getTextWidth(name, text_size);
//            tv.setText(name);//设置文本
//            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,text_size);//设置字体大小 像素单位
//            float textWidth = tv.getTextSize();//获取字体大小
            new TextView(context).getTextSize();
            if (screenWith >= textWidth + 2 * min_padding_width + margin_width) {//当前还能继续添加textview视图
                screenWith = (int) (screenWith - (textWidth + 2 * min_padding_width + margin_width));
                num++;
                if (i == showList.size() - 1) {
                    Log.e("test", "满足条件  且没更多数据 显示界面");
                    Log.i("test", "当前剩余宽度为：" + screenWith);
                    addTextView(layout_list.size()-1, num, i);
                    num = 0;
                    index = 1;
                }
            } else {//当前不能再添加textview视图了
                Log.e("test", "不满足条件  添加到下一个视图中");
                Log.v("test", "i = " + i);
                i--;//当前不满足  则i不变  进行下一次循环在添加进去
                addTextView(layout_list.size()-1, num, i);
                LinearLayout linearLayouts = new LinearLayout(context);
                linearLayouts.setOrientation(LinearLayout.HORIZONTAL);
                linearLayouts.setPadding(0,15,0,15);
                LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lps.setMargins(0,15,0,15);
                linearLayouts.setLayoutParams(lps);
                addView(linearLayouts);
                layout_list.add(linearLayouts);
                index++;
                num = 0;
                screenWith = getScreenWith();
                screenWith -= 2 * margin_width;
            }
        }
    }

    /**
     * 实际控件的左右padding
     **/
    private int Actual_padding_width = 0;

    /**
     * 添加textview到布局中
     *
     * @param index 需要添加视图的布局下标
     * @param num   当前布局中添加视图的个数
     * @param i     当前添加数据在数据源中的下标
     */
    private void addTextView(int index, int num, int i) {
        try{
            Log.e("addTextView","index = " + index);
            Log.e("addTextView","num = " + num);
            Log.e("addTextView","i = " + i);
            screenWith = getScreenWith();
            screenWith -= 2 * margin_width;
            i = i + 1 - num;
            for (int j = 0; j < num; j++) {
                String name = showList.get(i);
                if (TextUtils.isEmpty(name))
                    name = "未命名";
                i++;
                float textWidth = getTextWidth(name, text_size);
                screenWith -= textWidth;
                TextView tv = new TextView(context);
                tv.setText(name);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,text_size);//设置字体大小 sp单位
//            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,text_size);//设置字体大小 像素单位
                tv.setTextColor(text_color);
                tv.setGravity(Gravity.CENTER);
                tv_list.add(tv);
                tv.setTag(tv_list.size()-1);
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(v);
                        }
                    }
                });
            }
            screenWith = screenWith - ((num - 1) * margin_width);
            Log.e("addTextView","num = " + num);
            Actual_padding_width = screenWith / (num * 2);
            Actual_padding_width = Actual_padding_width > max_padding_width ?  max_padding_width : Actual_padding_width;
            i = i - num;
            layout_list.get(index).setPadding( margin_width, 0, 0, 0);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.rightMargin =  margin_width;
            for (int j = 0; j < num; j++) {
                tv_list.get(i).setPadding(Actual_padding_width,  padding_height, Actual_padding_width, padding_height);
                tv_list.get(i).setBackgroundResource(BackGround);
                layout_list.get(index).addView(tv_list.get(i), params);
                i++;
            }
        }catch (Exception e){
            Log.e("addTextView","index : "+index);
            Log.e("addTextView","i : "+i);
            Log.e("addTextView","num : "+num);
            Log.e("addTextView","e : "+e.getMessage());
            Toast.makeText(context,e.getMessage()+"",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取屏幕宽度
     */
    private int getScreenWith() {
        Display defaultDisplay = ((Activity)context).getWindow().getWindowManager()
                .getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        return outMetrics.widthPixels;
//        return width;
    }

    /**
     * 获取文字的宽度
     * 这种方法获取到的宽度是sp单位的
     */
    private float getTextWidth(String text, float textSize) {
        TextPaint paint = new TextPaint();
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledDensity * textSize);
        return paint.measureText(text);
    }

    public interface onItemClickListener {
        void onItemClick(View v);
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
