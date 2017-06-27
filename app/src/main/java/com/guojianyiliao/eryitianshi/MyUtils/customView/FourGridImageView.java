package com.guojianyiliao.eryitianshi.MyUtils.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guojianyiliao.eryitianshi.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zmc on 2017/5/10.
 * 自定义说说图片显示容器
 */

public class FourGridImageView<T> extends ViewGroup{


    /**
     * 常规显示图片风格
     *     *      * *    * *
     *                   * *
     */
    private static int type1 = 0;
    /**
     * 图片为三张时特殊显示风格
     *       * *
     *         *
     */
    private static int type2 = 1;
    /**当前显示的风格**/
    private int currentStyle = -1;
    /**常规显示时的行数**/
    private int rowNum;
    /**常规显示时的列数**/
    private int columnsNum;
    /**照片间间距**/
    private int mGap;
    /**图片资源**/
    List<String> data_list;
    /**图片集合**/
    private List<ImageView> img_list = new ArrayList<>();
    /**常规显示图片的宽度**/
    private float img_height;
    /**常规显示图片的高度**/
    private float img_width;

    String tag;
    mListener mListener;

    public void setmListener(FourGridImageView.mListener mListener) {
        this.mListener = mListener;
    }

    public FourGridImageView(Context context) {
        super(context);
    }

    public FourGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FourGridImageView);
        mGap = typedArray.getDimensionPixelOffset(R.styleable.FourGridImageView_img_padding,0);
    }

    public FourGridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if(data_list != null && data_list.size() > 0){
            if(data_list.size() == 1){
                img_height = height;
                img_width = width;
            }else if(data_list.size() == 2 || data_list.size() == 3){
                img_height = height;
                img_width = (width - mGap)/2;
            }else {
                img_height = (height - mGap)/2;
                img_width = (width - mGap)/2;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        drawChildView();
    }

    /**
     * 在容器中进行子view的绘画
     */
    private void drawChildView() {
        if (data_list == null) {
            return;
        }
        if(currentStyle == type1){//常规宫格显示风格
            int needShowNum = getNeedShowNum(data_list);
            for (int i = 0; i <needShowNum ; i++) {
                //依次获取出容器中的子view  并对其设置显示位置
                ImageView iv = (ImageView) getChildAt(i);
                if(mListener != null){
                    mListener.onDisplayImage(getContext(),iv,data_list.get(i),tag,this);
                }
                int rowNum = i / columnsNum;//当前view所在行行数
                int columnNum = i % columnsNum;//当前view在该行的第几列
                int left = (int) ((img_width + mGap) * columnNum);
                int top = (int) ((img_height + mGap) * rowNum);
                int right = (int) (left + img_width);
                int bottom = (int) (top + img_height);
                iv.layout(left, top, right, bottom);
            }
        }else if(currentStyle == type2){//3张图片时的特殊显示风格
            if(data_list.size() == 3){
                List<ImageView> iv_list = new ArrayList<>();
                iv_list.add((ImageView) getChildAt(0));
                iv_list.add((ImageView) getChildAt(1));
                iv_list.add((ImageView) getChildAt(2));
                for (int i = 0; i <3 ; i++) {
                    if(mListener != null){
                        mListener.onDisplayImage(getContext(),iv_list.get(i),data_list.get(i),tag,this);
                    }
                    if(i == 0){
                        iv_list.get(0).layout(0, 0, (int)img_width, (int)img_height);
                    }else if(i == 1){
                        iv_list.get(1).layout((int)img_width+mGap, 0, (int)(2*img_width+mGap), (int)img_height/2);
                    }else if(i == 2){
                        iv_list.get(2).layout((int)img_width+mGap, (int)img_height/2+mGap, (int)(2*img_width+mGap), (int)img_height+mGap);
                    }
                }
            }
        }
    }

    /**
     * 设置显示图片的资源
     * @param list
     */
    public void setImagesData(List<String> list,String tag){
        if(list == null || list.size() == 0){
            return;
        }
        this.tag = tag;
        int needShowNum = getNeedShowNum(list);
        getShowStyle(needShowNum);

        //代表当前的 图片显示控件还没有被使用  我们对其进行一次初始化
        if(data_list == null){
            for (int i = 0; i <needShowNum ; i++) {
                ImageView imageView = getImageView(i);
                if (imageView == null) {
                    return;
                }
                addView(imageView, generateDefaultLayoutParams());
            }
        }else{//当前的 图片显示控件已经使用过  我们对其进行复用
            int oldShowCount = getNeedShowNum(data_list);//获取上一次使用时的显示图片个数
            if(needShowNum > oldShowCount){//添加多的imageview
                for (int i = oldShowCount; i < needShowNum; i++) {
                    ImageView iv = getImageView(i);
                    if (iv == null) {
                        return;
                    }
                    addView(iv, generateDefaultLayoutParams());
                }
            }else if(needShowNum < oldShowCount){//将多余的imageview进行移除
                removeViews(needShowNum, oldShowCount - needShowNum);
            }
        }
        data_list = list;
        requestLayout();
    }

    /**
     * 获取imageview
     * 保证imageview的可复用性
     * @param i
     */
    private ImageView getImageView(int i) {
        if(i < img_list.size()){
            return  img_list.get(i);
        }else{
            ImageView iv = new ImageView(getContext());
            //TODO 设置iv的监听事件
            img_list.add(iv);
            return iv;
        }
    }

    /**
     * 根据最大显示图片数 设置图片显示风格
     * @param needShowNum
     */
    private void getShowStyle(int needShowNum) {
        if(needShowNum == 3){
            currentStyle = type2;
        }else{
            currentStyle = type1;
            if(needShowNum == 1){
                rowNum = 1;
                columnsNum = 1;
            }else if(needShowNum == 2){
                rowNum = 1;
                columnsNum = 2;
            }else if(needShowNum == 4){
                rowNum = 2;
                columnsNum = 2;
            }
        }
    }

    /**
     * 获取实际显示的图片数量
     * @param list
     * @return
     */
    private int getNeedShowNum(List<String> list){
        return list.size()>4?4:list.size();
    }

    public interface mListener{
        void onDisplayImage(Context context, ImageView imageView, String url,String tag,FourGridImageView view);
    }
}
