package com.guojianyiliao.eryitianshi.MyUtils.bean;

/**
 * description: 用户发布的说说
 * autour: jnluo jnluo5889@126.com
 * date: 2017/4/28 19:41
 * update: 2017/4/28
 * version: Administrator
*/

public class UserEssaies {

    /**
     * eagrees : 3
     * ecommontcount : 1
     * econtent : android
     * eid : 3e9a691252354ef6b136d52042d70c9d
     * eimages : http://192.168.1.11:10010/AppServer/images/8/0/1267b84271f74f109c7752f1644a6054.jpg;http://192.168.1.11:10010/AppServer/images/4/2/2c5be87e31884807868017ec79a17dea.jpg;
     * ememo :
     * epubtime : 1492678369000
     * etimes : 78
     * userid : b5df631f62cc40e6b932acd997cdc5c9
     */

   /**评论数**/
   public int ecommontcount;
   /**说说内容**/
   public String econtent;
   /**评论id**/
   public String eid;
   /**图片url 以分号相隔**/
   public String eimages;
   /**备注**/
   public String ememo;
   /**发布时间**/
   public long epubtime;
   /**浏览次数**/
   public int etimes;
   /**发布人的id**/
   public String userid;
   /**点赞数量**/
   public int eagrees;

   public int getEcommontcount() {
      return ecommontcount;
   }

   public String getEcontent() {
      return econtent;
   }

   public String getEid() {
      return eid;
   }

   public String getEimages() {
      return eimages;
   }

   public String getEmemo() {
      return ememo;
   }

   public long getEpubtime() {
      return epubtime;
   }

   public int getEtimes() {
      return etimes;
   }

   public String getUserid() {
      return userid;
   }

   public int getEagrees() {
      return eagrees;
   }
}
