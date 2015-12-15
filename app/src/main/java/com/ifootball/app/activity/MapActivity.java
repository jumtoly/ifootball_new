package com.ifootball.app.activity;

import android.os.Bundle;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.framework.widget.TitleBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MapActivity extends BaseActivity {

    public static final String COURT_LOCATION = "COURT_LOCATION";
    private HashMap<String, String> courtLocationMap;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    // 定位相关
    private LocationClient mLocClient;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationClientOption.LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private boolean isFirstLoc = true;// 是否首次定位


    //添加位置图标相关
    private InfoWindow mInfoWindow;
    private List<LatLng> latLngList = new ArrayList<>();
    private List<MarkerOptions> markerOptionsList = new ArrayList<>();
    private BitmapDescriptor mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        putContentView(R.layout.activity_map, "", true, true);
        TitleBarView titelView = (TitleBarView) findViewById(R.id.frg_map_titlebar);
        titelView.setViewData(getResources().getDrawable(R.mipmap.ico_backspace), "地图页面", null);
        titelView.setActivity(this);

        mark = BitmapDescriptorFactory.fromResource(R.mipmap.ico_marka);
        courtLocationMap = (HashMap<String, String>) getIntent().getSerializableExtra(COURT_LOCATION);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        setBaiduMapLocation();
        initOverlay();

    }

    private void initOverlay() {
        Float f = mBaiduMap.getMaxZoomLevel();
        Set<String> keySet = courtLocationMap.keySet();
        if (courtLocationMap.size() == 1) {

            for (String lat : keySet) {
                LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(courtLocationMap.get(lat)));
                MapStatus mMapStatus = new MapStatus.Builder().target(latLng).zoom(18).build();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                MarkerOptions options = new MarkerOptions().position(latLng).icon(mark).zIndex(0);
                Marker marker = (Marker) mBaiduMap.addOverlay(options);
                marker.setTitle("更改位置");
            }

        } else {

            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(f - 5);
            mBaiduMap.setMapStatus(msu);
            for (String lat : keySet) {
                LatLng latLng = new LatLng(Double.valueOf(lat), Double.valueOf(courtLocationMap.get(lat)));
                MarkerOptions options = new MarkerOptions().position(latLng).icon(mark).zIndex(0);
                Marker marker = (Marker) mBaiduMap.addOverlay(options);
                marker.setTitle("更改位置");
            }
        }


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                                               @Override
                                               public boolean onMarkerClick(final Marker marker) {
                                                   Button button = new Button(getApplicationContext());
                                                   button.setBackgroundResource(R.mipmap.popup);
                                                   InfoWindow.OnInfoWindowClickListener listener = null;

                                                   button.setText(marker.getTitle());
                                                   listener = new InfoWindow.OnInfoWindowClickListener() {
                                                       public void onInfoWindowClick() {
                                                           //TODO 点击图标跳转位置
                                                           /*LatLng ll = marker.getPosition();
                                                           LatLng llNew = new LatLng(ll.latitude + 0.005,
                                                                   ll.longitude + 0.005);
                                                           marker.setPosition(llNew);
                                                           mBaiduMap.hideInfoWindow();*/
                                                       }
                                                   };
                                                   LatLng ll = marker.getPosition();
                                                   mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                                                   mBaiduMap.showInfoWindow(mInfoWindow);
                                                   return false;
                                               }
                                           }

        );

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
        mark.recycle();
        this.finish();
        super.onDestroy();
    }

}
