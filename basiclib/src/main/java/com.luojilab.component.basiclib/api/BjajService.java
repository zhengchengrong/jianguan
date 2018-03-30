package com.luojilab.component.basiclib.api;


import com.luojilab.component.basiclib.bean.BaseEntity;
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
    //获取变更记录
/*    @POST("monitorInfo/getMonitorInfo")
    Observable<BaseBeanRsp<SupervisionPlanFirstRsp>> getMonitorInfo2(@Body SupervisionPlanFirstReq req);*/
/*    @POST("monitorunit/getMonitorunit")
    Observable<BaseBeanRsp<ChangeAddressResponBean>> getMonitorunitStr(@Body ChangeAddressRequestBean params);*/

}
