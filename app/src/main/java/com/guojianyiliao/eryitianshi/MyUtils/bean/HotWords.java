package com.guojianyiliao.eryitianshi.MyUtils.bean;

import java.util.List;

/**
 * Created by zmc on 2017/5/8.
 *
 * 热词实体类
 */
public class HotWords {

        /**热词id**/
        String sid;
        /**热词内容**/
        String scontent;
        /**热词时间**/
        Long sdate;
        /**热词搜索次数**/
        int stimes;
        /**备注**/
        String memo;

        public String getSid() {
            return sid;
        }

        public String getScontent() {
            return scontent;
        }

        public Long getSdate() {
            return sdate;
        }

        public int getStimes() {
            return stimes;
        }

        public String getMemo() {
            return memo;
        }
}
