package com.luojilab.component.basiclib.api;


import com.luojilab.component.basiclib.bean.BaseEntity;
import com.luojilab.component.basiclib.bean.QrcodeInfoBean;
import com.luojilab.component.basiclib.bean.SamplingDetailBean;
import com.luojilab.component.basiclib.bean.SamplingResBean;
import com.luojilab.component.basiclib.bean.login.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhengchengrong on 2017/9/1.
 */

public interface BjajService {

    @FormUrlEncoded
    @POST("api/jzqy/postLogin")
    Observable<BaseEntity<UserBean>> toLogin(@Field("username") String username,@Field("password")String password);

    @GET("api/jzqy/getProject")
    Observable<BaseEntity<SamplingResBean>> getProject(@Query("username") String username, @Query("password") String password);
    @GET("api/jzqy/getSampleInfo")
    Observable<BaseEntity<SamplingDetailBean>> getSampleInfo(@Query("username") String username, @Query("password") String password, @Query("zljdbm") String zljdbm);
    @GET("api/jzqy/getQrcodeInfo")
    Observable<BaseEntity<QrcodeInfoBean>> getQrcodeInfo(@Query("qrcode") String qrcode, @Query("id") String id);

    @FormUrlEncoded
    @POST("api/jzqy/postQrcodeMap")
    Observable<BaseEntity<UserBean>> postQrcodeMap(@Field("id") String id,@Field("qrcode")String qrcode,@Field("maplocation")String maplocation,@Field("wtmanlongitude")String wtmanlongitude,@Field("wtmanlatitude")String wtmanlatitude,@Field("jzmanlongitude")String jzmanlongitude,@Field("jzmanlatitude")String jzmanlatitude,@Field("flag")String flag,@Field("jzuserid")String jzuserid);


    @FormUrlEncoded
    @POST("api/jzqy/postImg")
    Observable<BaseEntity<UserBean>> postImg(@Field("id") String id,@Field("picname")String picname,@Field("filed")String filed,@Field("district")String district);

    //获取变更记录
/*    @POST("monitorInfo/getMonitorInfo")
    Observable<BaseBeanRsp<SupervisionPlanFirstRsp>> getMonitorInfo2(@Body SupervisionPlanFirstReq req);*/
/*    @POST("monitorunit/getMonitorunit")
    Observable<BaseBeanRsp<ChangeAddressResponBean>> getMonitorunitStr(@Body ChangeAddressRequestBean params);*/

}
