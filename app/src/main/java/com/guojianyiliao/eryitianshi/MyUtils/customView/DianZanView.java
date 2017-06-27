package com.guojianyiliao.eryitianshi.MyUtils.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zmc on 2017/5/12.
 * 显示说说点赞列表
 */

public class DianZanView extends ViewGroup {

    /**图片宽**/
    private float img_width;
    /**图片高**/
    private float img_height;
    /**图片间隔**/
    private float img_padding;
    /**行数**/
    private int rowNum;
    /**列数**/
    private int columnsNum;
    /**控件高**/
    int height;
    /**控件宽**/
    int width;
    /**图片资源**/
    List<String> img_url = new ArrayList<>();
    mListener mListener;

    public void setmListener(mListener mListener) {
        this.mListener = mListener;
        Log.e("DianZanView","setmListener");
    }
    public DianZanView(Context context) {
        super(context);
        Log.e("DianZanView","DianZanView(context)");
    }

    public DianZanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DianZanView);
        img_width = typedArray.getDimensionPixelOffset(R.styleable.DianZanView_img_width,0);
        img_height = typedArray.getDimensionPixelOffset(R.styleable.DianZanView_img_height,0);
        img_padding = typedArray.getDimensionPixelOffset(R.styleable.DianZanView_dz_img_padding,0);
        typedArray.recycle();
        Log.e("DianZanView","DianZanView(context, attrs)");
        Log.e("DianZanView","img_width = "+img_width);
        Log.e("DianZanView","img_height = "+img_height);
        Log.e("DianZanView","img_padding = "+img_padding);
    }

    public DianZanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("DianZanView","DianZanView(context, attrs, defStyleAttr)");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);//此处获取到的高度为0  我们需要自己进行计算
        if(img_url.size() != 0){
            height = (int) ((rowNum * (img_height + img_padding)) - img_padding);
        }
        setMeasuredDimension(width, height);

        Log.e("DianZanView","onMeasure");
        Log.e("DianZanView","width = "+width);
        Log.e("DianZanView","height = "+height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("DianZanView","onLayout");
        drawChildView();
    }

    /**
     * 在容器中绘画子视图
     */
    private void drawChildView() {
        Log.e("DianZanView","drawChildView");
        if(img_url.size() == 0){
            return;
        }
        for (int i = 0; i <img_url.size() ; i++) {
            //依次获取出容器中的子view  并对其设置显示位置
            ImageView iv = (ImageView) getChildAt(i);
            if(mListener != null){
                mListener.onDisplayImage(getContext(),iv,img_url.get(i));
            }
            int rowNum = i / columnsNum;//当前view所在行行数
            int columnNum = i % columnsNum;//当前view在该行的第几列
            int left = (int) ((img_width + img_padding) * columnNum);
            int top = (int) ((img_height + img_padding) * rowNum);
            int right = (int) (left + img_width);
            int bottom = (int) (top + img_height);
            iv.layout(left, top, right, bottom);
        }
    }

    /**
     * 添加一个图片
     * @param url
     */
    public void addImageView(String url){
        img_url.add(url);
        //计算出容器一行最多显示的列数
        columnsNum = (int)(width / (img_width + img_padding));
        if(width - columnsNum * (img_width + img_padding) > img_width){
            columnsNum ++ ;
        }
        rowNum = ((int)(img_url.size() / columnsNum)) + 1;
        addView(getImageView());

    }

    /**
     * 移除一个图片
     * @param url
     */
    public void removeImg(String url){
        rowNum = 0;
        columnsNum = 0;
        this.removeAllViews();
        for (int i = 0; i < img_url.size() ; i++) {
            if(img_url.get(i).equals(url)){
                img_url.remove(i);
            }
        }
        setImageResources(img_url);
        requestFocus(img_url.size()-1);
    }


    public void setImageResources(List<String> list){
        Log.e("Test----","list.size = " + list.size());
        Log.e("DianZanView","setImageResources");
        img_url = list;

        //计算出容器一行最多显示的列数
        columnsNum = (int)(width / (img_width + img_padding));
        if(width - columnsNum * (img_width + img_padding) > img_width){
            columnsNum ++ ;
        }
        rowNum = ((int)(list.size() / columnsNum)) + 1;
        Log.e("DianZanView","rowNum = "+rowNum);
        Log.e("DianZanView","columnsNum = "+columnsNum);
        for (int i = 0; i <list.size() ; i++) {
            addView(getImageView());
        }
        requestLayout();
    }

//    private ImageView getImageView(){
//        ImageView imageview = new ImageView(getContext());
//        LayoutParams layoutParams = imageview.getLayoutParams();
//        layoutParams.width = (int) img_width;
//        layoutParams.height = (int) img_height;
//        imageview.setLayoutParams(layoutParams);
//        return imageview;
//    }

    /**
     * 获取圆形图片
     * @return
     */
    private CircleImageView getImageView(){
        Log.e("DianZanView","getImageView");
        CircleImageView imageview = new CircleImageView(getContext());
//        LayoutParams layoutParams = imageview.getLayoutParams();
//        layoutParams.width = (int) img_width;
//        layoutParams.height = (int) img_height;
//        imageview.setLayoutParams(layoutParams);
        return imageview;
    }

    public interface mListener{
        void onDisplayImage(Context context, ImageView imageView, String url);
    }

}
