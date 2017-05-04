package com.guojianyiliao.eryitianshi.MyUtils.base;

import java.util.List;

/**
 * Created by Jaeger on 16/2/24.
 * <p>
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public class Post {
    private List<String> mImgUrlList;

    public Post(List<String> imgUrlList) {
        mImgUrlList = imgUrlList;
    }

    public List<String> getImgUrlList() {
        return mImgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        mImgUrlList = imgUrlList;
    }
}
