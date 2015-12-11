package com.ifootball.app.activity.green;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.framework.widget.TitleBarView;

import java.util.HashMap;

public class MapActivity extends BaseActivity {

    public static final String COURT_LOCATION = "COURT_LOCATION";
    private HashMap<String, String> courtLocationMap;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationClientOption.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private boolean isFirstLoc = true;// 是否首次定位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        putContentView(R.layout.activity_map, "", true, true);
        TitleBarView titelView = (TitleBarView) findViewById(R.id.frg_map_titlebar);
        titelView.setViewData(getResources().getDrawable(R.mipmap.ico_backspace), "地图页面", null);
        titelView.setActivity(this);

        courtLocationMap = (HashMap<String, String>) getIntent().getSerializableExtra(COURT_LOCATION);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        setBaiduMapLocation();

    }

    private void setBaiduMapLocation() {


        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

                float f = mBaiduMap.getMaxZoomLevel();// 19.0
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, f - 5);
                mBaiduMap.animateMapStatus(u);
            }
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
//        mark.recycle();
        super.onDestroy();
    }

}
