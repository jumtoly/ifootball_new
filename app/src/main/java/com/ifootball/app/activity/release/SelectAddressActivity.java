package com.ifootball.app.activity.release;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.common.Common;
import com.ifootball.app.entity.release.ReleaseSelectAddress;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.framework.widget.TitleBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu.yao on 2016/1/11.
 */
public class SelectAddressActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public static final String ADDRESS = "ADDRESS";
    private ListView mListView;
    private GeoCoder mSearch;
    private List<PoiInfo> mPoiList;
    private ArrayList<ReleaseSelectAddress> mAddressList = new ArrayList<ReleaseSelectAddress>();
    private ProgressBar mPBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_select_address);

        TitleBarView view = (TitleBarView) findViewById(R.id.main_selcet_address_titlebar);
        view.setViewData(getResources().getDrawable(R.mipmap.ico_backspace),
                "选择地点", null);
        view.setActivity(this);

        mListView = (ListView) findViewById(R.id.main_selcet_address_listview);
        mListView.setOnItemClickListener(this);

        mPBar = (ProgressBar) findViewById(R.id.main_selcet_address_progress);
        mAddressList.add(new ReleaseSelectAddress("不选择位置", ""));

        mPBar.setVisibility(View.VISIBLE);
        getRoundInfo();

    }

    private void getRoundInfo() {

        if (MySharedCache.get(Common.CURRENT_LATITUDE.name(), "0").isEmpty()
                && MySharedCache.get(Common.CURRENT_LONGITUDE.name(), "0")
                .isEmpty()) {
            return;
        }
        float latitude = Float.parseFloat(MySharedCache.get(
                Common.CURRENT_LATITUDE.name(), "0"));
        float longitude = Float.parseFloat(MySharedCache.get(
                Common.CURRENT_LONGITUDE.name(), "0"));
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(listener);
        LatLng latLng = new LatLng(latitude, longitude);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

    }

    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        public void onGetGeoCodeResult(GeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            }
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            } else {

                mPoiList = result.getPoiList();
                for (PoiInfo poiInfo : mPoiList) {
                    ReleaseSelectAddress address = new ReleaseSelectAddress(
                            poiInfo.name, poiInfo.address);
                    mAddressList.add(address);
                }
                mListView.setAdapter(new SelectAddress2DAdapter(
                        SelectAddressActivity.this, mAddressList));
                mPBar.setVisibility(View.GONE);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent();
        intent.putExtra(ADDRESS, mAddressList.get(position));
        setResult(ReleaseTrendsActivity.ADDRESS, intent);
        this.finish();
    }


}

class SelectAddress2DAdapter extends BaseAdapter {

    private List<ReleaseSelectAddress> mAddress;
    private Context mContext;

    public SelectAddress2DAdapter(Context context,
                                  List<ReleaseSelectAddress> address) {
        this.mContext = context;
        this.mAddress = address;
    }

    @Override
    public int getCount() {
        return mAddress.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_select_address, null);
            holder = new MyViewHolder();
            holder.name = (TextView) convertView
                    .findViewById(R.id.item_select_address_name);
            holder.address = (TextView) convertView
                    .findViewById(R.id.item_select_address_address);
            convertView.setTag(holder);

        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.name.setText(mAddress.get(position).getLocationName());
        holder.address.setText(mAddress.get(position).getLocationAddress());
        return convertView;
    }

    class MyViewHolder {
        TextView name;
        TextView address;

    }

}
