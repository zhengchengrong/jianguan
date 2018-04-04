package com.jg.chipcomponent;



import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.jg.chipcomponent.utils.ImageUtils;
import com.luojilab.component.basiclib.Const;
import com.luojilab.component.basiclib.api.BaseObserver;
import com.luojilab.component.basiclib.api.RetrofitFactory;
import com.luojilab.component.basiclib.api.RxSchedulers;
import com.luojilab.component.basiclib.base.BaseActivity;
import com.luojilab.component.basiclib.bean.BaseEntity;
import com.luojilab.component.basiclib.bean.QrcodeInfoBean;
import com.luojilab.component.basiclib.bean.SamplingResBean;
import com.luojilab.component.basiclib.bean.login.UserBean;
import com.luojilab.component.basiclib.utils.RxLogTool;
import com.luojilab.component.basiclib.utils.RxSPTool;
import com.luojilab.component.basiclib.utils.RxToast;
import com.luojilab.component.basiclib.utils.RxTool;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import io.reactivex.Observable;


public class ChipAddActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate {

    private String code;
    private String id;
    private static final int PRC_PHOTO_PICKER = 1;

    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;

    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";

    private SuperTextView chip_supertv_num;
    private SuperTextView chip_supertv_dingwei;


    /**
     * 拖拽排序九宫格控件
     */
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private TextView tv_commit;
    private QrcodeInfoBean mQrcodeInfoBean;
    private TextureMapView mTextureMapView;

    // 定位相关
    LocationClient mLocClient;
    BaiduMap mBaiduMap;
    public MyLocationListenner myListener = new MyLocationListenner();
    boolean isFirstLoc = true; // 是否首次定位
    private String latitude = "39.90";
    private String longitude = "116.40";
    private String address="";

    @Override
    protected int attachLayoutRes() {
        return R.layout.chip_add_activity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews() {
        code = getIntent().getStringExtra(Const.CODE);
        id = getIntent().getStringExtra(Const.ID);
        if(id==null){
            initTitle(true,"见证信息");
        }else{
            initTitle(true,"取样信息");
        }
        chip_supertv_num = this.findViewById(R.id.chip_supertv_num);
        chip_supertv_dingwei = this.findViewById(R.id.chip_supertv_dingwei);
        chip_supertv_num.setLeftString("芯片编号:"+code);
        tv_commit = this.findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> picData =  mPhotosSnpl.getData();
                for(int i=0;i<picData.size();i++){
                    String path = picData.get(i);
                    String s =   ImageUtils.bitmapToString(path);
                    String picname = id +"wt" + (i+1);
                    if(id==null) { // 如果id为空就是见证端的
                        Observable<BaseEntity<UserBean>> observable = RetrofitFactory.getInstance().postImg(mQrcodeInfoBean.getId(), picname, s, "jz");
                        observable.compose(RxSchedulers.<BaseEntity<UserBean>>compose(
                        )).subscribe(new BaseObserver<UserBean>() {
                            @Override
                            protected void onHandleSuccess(BaseEntity<UserBean> bean) {
                                //RxToast.showToast(bean.getDescription());
                            }
                            @Override
                            protected void onHandleEmpty(BaseEntity<UserBean> bean) {
                                //  RxToast.showToast(bean.getDescription());
                            }
                        });
                    }else{
                        Observable<BaseEntity<UserBean>> observable = RetrofitFactory.getInstance().postImg(id, picname, s, "wt");
                        observable.compose(RxSchedulers.<BaseEntity<UserBean>>compose(
                        )).subscribe(new BaseObserver<UserBean>() {
                            @Override
                            protected void onHandleSuccess(BaseEntity<UserBean> bean) {
                                //RxToast.showToast(bean.getDescription());
                            }
                            @Override
                            protected void onHandleEmpty(BaseEntity<UserBean> bean) {
                                //  RxToast.showToast(bean.getDescription());
                            }
                        });
                    }

                }
                String uesrid="";
                if(mQrcodeInfoBean!=null) {
                     uesrid = TextUtils.isEmpty(mQrcodeInfoBean.getJzuserid()) ? "" : mQrcodeInfoBean.getJzuserid();
                }
                if(id==null) { // 如果id为空就是见证端的
                    Observable<BaseEntity<UserBean>> observable = RetrofitFactory.getInstance().postQrcodeMap(mQrcodeInfoBean.getId(), code, address, "", "", longitude, latitude, "jz", uesrid);
                    observable.compose(RxSchedulers.<BaseEntity<UserBean>>compose(
                    )).subscribe(new BaseObserver<UserBean>() {
                        @Override
                        protected void onHandleSuccess(BaseEntity<UserBean> bean) {
                            RxToast.showToast(bean.getDescription());

                        }
                        @Override
                        protected void onHandleEmpty(BaseEntity<UserBean> bean) {
                            RxToast.showToast(bean.getDescription());
                        }
                    });
                }else{
                    Observable<BaseEntity<UserBean>> observable = RetrofitFactory.getInstance().postQrcodeMap(id, code, address, longitude, latitude, "", "", "wt", uesrid);
                    observable.compose(RxSchedulers.<BaseEntity<UserBean>>compose(
                    )).subscribe(new BaseObserver<UserBean>() {
                        @Override
                        protected void onHandleSuccess(BaseEntity<UserBean> bean) {
                            RxToast.showToast(bean.getDescription());
                        }
                        @Override
                        protected void onHandleEmpty(BaseEntity<UserBean> bean) {
                            RxToast.showToast(bean.getDescription());
                        }
                    });
                }
            }
        });
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        mTextureMapView = findViewById(R.id.texture_map);
        mPhotosSnpl.setEditable(true);
        mPhotosSnpl.setDelegate(this);

        ininMap();
    }

    private void ininMap() {
        mBaiduMap = mTextureMapView.getMap();
        //设置地图缩放级别
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        //就是这个方法设置为true，才能获取当前的位置信息
        option.setIsNeedAddress(true);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类
        option.setScanSpan(15000); //15秒
        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
        option.disableCache(false);// 禁止启用缓存定位
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    protected void initData() {
        getData();
    }

    public void getData(){
        Observable<BaseEntity<QrcodeInfoBean>> observable = RetrofitFactory.getInstance().getQrcodeInfo(code,null);
        observable.compose(RxSchedulers.<BaseEntity<QrcodeInfoBean>>compose(
        )).subscribe(new BaseObserver<QrcodeInfoBean>() {
            @Override
            protected void onHandleSuccess(BaseEntity<QrcodeInfoBean> bean) {
                mQrcodeInfoBean =bean.getList().get(0);

            }
            @Override
            protected void onHandleEmpty(BaseEntity<QrcodeInfoBean> bean) {
            }
        });
    }
    @Override
    protected void updateViews(boolean isRefresh) {

    }


    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);

    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
                mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mTextureMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            latitude = location.getLatitude() + "";
            longitude = location.getLongitude() + "";
            address = location.getAddress().city;
            location.getAddress();
            chip_supertv_dingwei.setLeftString(latitude+"   "+longitude);
            // 這裡不斷監聽，獲取當前的位置
            RxLogTool.d(locData);
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            mBaiduMap
                    .setMyLocationConfigeration(new MyLocationConfiguration(
                            MyLocationConfiguration.LocationMode.NORMAL, true, null));
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
