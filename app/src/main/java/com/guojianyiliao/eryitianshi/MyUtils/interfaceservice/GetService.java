package com.guojianyiliao.eryitianshi.MyUtils.interfaceservice;

import com.guojianyiliao.eryitianshi.Data.entity.DiseaseBanner;
import com.guojianyiliao.eryitianshi.Data.entity.User;
import com.guojianyiliao.eryitianshi.MyUtils.bean.AllLecturesBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.AllTypesSecListBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeHotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.BaikeVideoData;
import com.guojianyiliao.eryitianshi.MyUtils.bean.CommonDisData;
import com.guojianyiliao.eryitianshi.MyUtils.bean.DrugRemindBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.EcommentsBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.EssayInfoBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.GetAllTypesTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.HotTalkBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.MyFocus;
import com.guojianyiliao.eryitianshi.MyUtils.bean.RandOrderBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.RemidBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserCollectBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserEssaies;
import com.guojianyiliao.eryitianshi.MyUtils.bean.UserInfoLogin;
import com.guojianyiliao.eryitianshi.MyUtils.bean.myCasesBean;
import com.guojianyiliao.eryitianshi.MyUtils.bean.myCashCouponsBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * 接口
 * jnluo，jnluo5889@126.com
 * <p>
 * <p>
 * RetrofitClient.getinstance(this).create(GetService.class)
 * <p>
 * String userid = SpUtils.getInstance(this).get("userid",null);
 * b5df631f62cc40e6b932acd997cdc5c9
 * <p>
 * if (response.isSuccessful()){MyLogcat.jLog().e("onResponse:"+response.body().toString());}
 * <p>
 * <p>id://2017-04-20 10:59//0aba812c191a428788a2f86e6434c356
 * MyLogcat.jLog().e("onFailure:");
 */

public interface GetService {


    @FormUrlEncoded
    @POST("user/getVerification")
    Call<String> sendCode(@Field("phone") String phonge);//发送验证码

    @FormUrlEncoded
    @POST("user/registUser")
    Call<String> HttpRegist(@Field("phone") String username, @Field("password") String pas, @Field("code") String code);//注册接口

    @FormUrlEncoded
    @POST("user/Login")
    Call<User> HttpLogin(@Field("phone") String phone, @Field("password") String pas);

    @FormUrlEncoded
    @POST("http://192.168.1.11:10010/AppServer/user/umLogin")
    Call<String> BindingPhone(@Field("phone") String phone, @Field("name") String name, @Field("gender") String gender, @Field("icon") String icon, @Field("uid") String uid, @Field("type") String type);


    @GET("user/updateUser")
    Call<String> BindingInfoData(@Query("userid") String userId, @Query("name") String name, @Query("gender") String gender);

    @FormUrlEncoded
    @POST("user/registUser")
    Call<String> HttpFindPas(@Field("phone") String username, @Field("code") String code, @Field("password") String pas);//注册接口

    @GET("banner/getHomeBanners")
    Call<List<DiseaseBanner>> BannerData();//广告条

    @GET("doctor/getRanDomDr")
    Call<List<RandOrderBean>> RanDomDrData();//首页随机医生

    @GET("cyclopedia/getTwoCycl")
    Call<List<HotTalkBean>> HotTalkData();//首页热门文章

    @GET("disease/getCommonDis")
    Call<List<CommonDisData>> getCommonDis();//常见疾病

    @GET("section/getSecList")
    Call<List<AllTypesSecListBean>> AllTypesDecList();//百科疾病 科室疾病，动态获取

    @GET("cyclType/getAllTypes")
    Call<List<GetAllTypesTalkBean>> GetAllTypesTalk();//百科文章分类

    @FormUrlEncoded
    @POST("cyclopedia/getCyclByType")
    Call<List<BaikeHotTalkBean>> TypesTalkId(@Field("typeId") String typeId, @Field("pageNum") String pageNum);//文章,根据id获取

    @GET("lecture/getAllLectures")
    Call<List<BaikeVideoData>> BaikeVideoData();//百科获取视屏列表

    @Multipart
    @POST("upload/uploadIcon")
    Call<UserInfoLogin> upLoadAgree(@PartMap Map<String, RequestBody> params);//上传头像

    @FormUrlEncoded
    @POST("user/updateUser")
    Call<String> updateUser(@Field("userid") String userid, @Field("name") String name);//更新昵称

    @FormUrlEncoded
    @POST("remind/getRemind")
    Call<List<DrugRemindBean>> DrugRemind(@Field("userid") String userid, @Field("startDate") String startDate);//用药提醒


    /**
     * 添加用药提醒 reminddate,time1,content,userid,enddate reminddate:精确到 yyyy-MM-dd的前一天;
     * time和content顺序往后加
     */
    @FormUrlEncoded
    @POST("remind/addRemind")
    Call<String> addRemind(@Field("reminddate") String reminddate, @Field("enddate") String enddate, @Field("time1") String time1, @Field("time2") String time2, @Field("time3") String time3, @Field("content1") String content1, @Field("content2") String content2, @Field("content3") String content3, @Field("userid") String userid);//


    /**
     * 修改用药提醒
     * reminddate:不用取前一天,保持原样就好
     */
    @FormUrlEncoded
    @POST("remind/editRemind")
    Call<String> editRemind(@Field("remindid") String remindid, @Field("reminddate") String reminddate, @Field("enddate") String enddate, @Field("time1") String time1, @Field("time2") String time2, @Field("time3") String time3, @Field("content1") String content1, @Field("content2") String content2, @Field("content3") String content3, @Field("userid") String userid);//

    /**
     * 获取用药提醒详情
     */
    @FormUrlEncoded
    @POST("remind/getRemindById")
    Call<RemidBean> getRemind(@Field("remindid") String remindid);

    /**
     * 删除用药提醒
     */
    @FormUrlEncoded
    @POST("remind/deleteRemind")
    Call<String> deleteRemind(@Field("remindid") String remindid);

    /**
     * 获取我的病历
     */
    @FormUrlEncoded
    @POST("case/getMyCases")
    Call<List<myCasesBean>> getMyCases(@Field("userid") String userid);

    /**
     * 获取病历详情
     */
    @FormUrlEncoded
    @POST("case/getCaseInfo")
    Call<myCasesBean> getCaseInfo(@Field("caseid") String userid);

    /**
     * 获取我的代金券
     */
    @FormUrlEncoded
    @POST("voucher/getMyVoucher")
    Call<List<myCashCouponsBean>> getMyVoucher(@Field("userId") String userid);

    /**
     * 分享获取到的代金券
     * vouType
     * 1:10块,
     * 2:15块,
     * 3:25块
     */
    @FormUrlEncoded
    @POST("voucher/getShareVoucher")
    Call<myCasesBean> getShareVoucher(@Field("userid") String userid, @Field("voutype") String voutype);

    /**
     * 收藏文章
     * 用户ID,文章ID,收藏时间
     */
    @FormUrlEncoded
    @POST("collect/collectCycl")
    Call<String> collectCycl(@Field("userid") String userid, @Field("cyclopediaid") String cyclopediaid, @Field("ctime") String ctime);

    /**
     * 取消文章收藏
     */
    @FormUrlEncoded
    @POST("collect/cancleCollect")
    Call<String> cancleCollect(@Field("userid") String userid, @Field("cyclId") String cyclId);

    /**
     * 获取用户收藏的文章
     */
    @FormUrlEncoded
    @POST("collect/getUserCollect")
    Call<List<UserCollectBean>> getUserCollect(@Field("userId") String userid);

    /**
     * 判读用户是否收藏该文章，true改变红心
     */
    @FormUrlEncoded
    @POST("collect/isCollected")
    Call<String> isCollected(@Field("userId") String userId, @Field("cyclId") String cyclId);

    /**
     * 发布说说
     * econtent:说说内容;userid:用户ID;epubtime:发布时间;images:图片
     * 发布时间:yyyy-MM-dd hh:mm:ss,
     * images:以文件形式发送
     */
    @Multipart
    @POST("essay/publishEssay")
    Call<String> publishEssay(@PartMap Map<String, RequestBody> params);

    /**
     * 获取说说列表
     */
    @GET("essay/getAllEssaiesByPage")
    Call<List<AllLecturesBean>> getAllLectures1(@Query("pageNum") String page, @Query("userid") String userid);

    /**
     * 获取说说列表
     */
    @GET("essay/getAllEssaiesByPage")
    Call<AllLecturesBean> getAllLectures(@Query("pageNum") String page, @Query("userid") String userid);

    /**
     * 说说点赞
     * eid:说说ID;userid:用户ID;agreetime:点赞时间 essay/disAgreeWithEssay
     */
    @FormUrlEncoded
    @POST("essay/agreeWithEssay")
    Call<String> agreeWithEssay(@Field("eid") String eid, @Field("userid") String userid, @Field("agreetime") String agreetime);

    /**
     * 取消说说点赞
     * userid:用户ID;eid:说说ID
     */
    @FormUrlEncoded
    @POST("essay/disAgreeWithEssay")
    Call<String> disAgreeWithEssay(@Field("eid") String eid, @Field("userid") String userid);

    /**
     * 添加关注
     * fuserid:用户ID;username:用户昵称;focusedid:被关注人ID;focusedname:被关注人昵称
     * username和focusedname可选
     */
    @FormUrlEncoded
    @POST("focus/addMyFocus")
    Call<String> addMyFocus(@Field("fuserid") String fuserid, @Field("username") String username, @Field("focusedid") String focusedid, @Field("username") String focusedname);

    /**
     * 取消关注
     * userid:用户ID;focUserId:被关注人ID
     */
    @FormUrlEncoded
    @POST("focus/delMyFocus")
    Call<String> delMyFocus(@Field("userid") String userid, @Field("focuserid") String focUserId);

    /**
     * 说说详情
     * eid:说说ID  1df9a3f7fcb7474fa1826d0d985f3799
     */
    @GET("essay/getEssayInfo")
    Call<EssayInfoBean> getEssayInfo(@Query("eid") String eid);

    /**
     * 说说评论列表
     * eid:说说ID  10e4084158d94170a8e3915427c4914c
     */
    @GET("ecomment/getEComments")
    Call<List<EcommentsBean>> getEComments(@Query("eid") String eid);

    /**
     * 添加说说评论
     * eid:说说ID  10e4084158d94170a8e3915427c4914c
     * eccontent:内容;ecuserid:用户ID;ecusername:用户昵称;ectime:评论时间;ressayid:说说ID =说说 eid
     */
    @GET("ecomment/addEComment")
    Call<String> addEComment(@Query("eccontent") String eccontent, @Query("ecuserid") String ecuserid,
                             @Query("ecusername") String ecusername, @Query("ectime") String ectime, @Query("ressayid") String ressayid);

    /**
     * 回复说说评论
     * replyid:评论ID,回复的哪一条评论;ressayid:说说ID;ruserid:回复者的ID;rusername:回复者的昵称;rcontent:回复内容;rtime:回复时间;ecuserid:评论者的ID;ecusername:评论者的昵称;ectime:与回复时间值相同
     * replyid,ressayid,ruserid,rusername,rcontent,rtime,ecuserid,ecusername,ectime
     */
    @FormUrlEncoded
    @POST("ecomment/addEComment")
    Call<String> addReplyEComment(@Field("replyid") String replyid, @Field("ressayid") String ressayid, @Field("ruserid") String ruserid,
                                  @Field("rusername") String rusername, @Field("rcontent") String rcontent, @Field("rtime") String rtime,
                                  @Field("ecuserid") String ecuserid, @Field("ecusername") String ecusername, @Field("ectime") String ectime);


    /**
     * 我的关注
     */
    @GET("focus/getMyFocus")
    Call<List<MyFocus>> getMyFocus(@Query("userid") String userid);


    /**
     * 自己/某一用户 发布的说说
     * userid,pageNum
     */
    @FormUrlEncoded
    @POST("essay/getUserEssaies")
    Call<List<UserEssaies>> getUserEssaies(@Field("userid") String userid, @Field("pageNum") String pageNum);

}
