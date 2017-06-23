package com.jaeger.ninegridimageview;

import android.content.Context;
import android.widget.ImageView;
import java.util.List;

/**
 * Created by Jaeger on 16/2/24.
 *
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public abstract class NineGridImageViewAdapter<T> {


    /**没有图片时 隐藏控件**/
    protected abstract void onHideView(NineGridImageView<T> view);

    /**加载图片**/
    protected abstract void onDisplayImage(Context context, ImageView imageView, T t,String tag,NineGridImageView<T> view);

    protected void onItemImageClick(Context context, ImageView imageView, int index, List<T> list) {
    }

    protected ImageView generateImageView(Context context) {
        GridImageView imageView = new GridImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}