package com.ifootball.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifootball.app.R;
import com.ifootball.app.activity.stand.DetailsActivity;
import com.ifootball.app.adapter.stand.StandPage2DAdapter;
import com.ifootball.app.common.StandPageTypeEnum;
import com.ifootball.app.entity.HasCollection;
import com.ifootball.app.entity.stand.DynamicInfo;
import com.ifootball.app.entity.stand.StandInfo;
import com.ifootball.app.framework.adapter.MyDecoratedAdapter;
import com.ifootball.app.framework.content.CBCollectionResolver;
import com.ifootball.app.framework.content.CollectionStateObserver;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.stand.StandService;

import java.io.IOException;

public class MainActivity extends Activity {


    private static final int pageSize = 10;

    private int pageIndex = 0;


    private PullToRefreshListView mListView;
    //    private CBCollectionResolver<StandInfo> mResolver;
    private View view;
    private DynamicInfo mDynamicInfo;
    private StandPage2DAdapter mAdapter;

    private int mCurIndex = -1;
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;
    private boolean mIsFristLoading = true;
    private int mCurrentLastPageIndex;
    private CBCollectionResolver<StandInfo> mResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        getData2();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt(DetailsActivity.SYSNO, ((StandInfo) mListView.getAdapter().getItem(position)).getSysNo());
                IntentUtil.redirectToNextActivity(MainActivity.this, DetailsActivity.class, bundle);


            }
        });
        /*mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Toast.makeText(MainActivity.this, "onPullDownToRefresh", Toast.LENGTH_SHORT).show();
                mCurrentLastPageIndex = pageIndex;
                pageIndex = 0;
                getData();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {


                Toast.makeText(MainActivity.this, "onPullUpToRefresh", Toast.LENGTH_SHORT).show();
                pageIndex = mCurrentLastPageIndex;
                if (mDynamicInfo != null
                        && mDynamicInfo.getList() != null
                        && mDynamicInfo.getList().size() > 0) {
                    mHasLoadedOnce = true;
                    pageIndex = pageIndex + 1;
                } else {
                    MyToast.show(MainActivity.this, "已经到底了……", Toast.LENGTH_SHORT);
                }
                getData();
            }
        });*/
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
               /* Toast.makeText(MainActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
                mCurrentLastPageIndex = pageIndex;
                pageIndex = 0;
                getData2();*/
            }
        });
    }

    private void getData2() {
        mResolver = new CBCollectionResolver<StandInfo>() {
            @Override
            public HasCollection<StandInfo> query()
                    throws IOException, ServiceException {

                mDynamicInfo = new StandService().getDynamicInfo(pageIndex, pageSize, StandPageTypeEnum.BESTHEAT.getPageType());

                if (mDynamicInfo != null
                        && mDynamicInfo.getList() != null
                        && mDynamicInfo.getList().size() > 0) {
                    mHasLoadedOnce = true;
                    pageIndex = pageIndex + 1;
                }

                return mDynamicInfo;
            }
        };

        mAdapter = new StandPage2DAdapter(this);
        mListView.setAdapter(mAdapter);
        CollectionStateObserver observer = new CollectionStateObserver();
        mAdapter.setVisible(true);
        observer.setActivity(MainActivity.this);
        observer.setAdapters(mAdapter);
        mAdapter.startQuery(mResolver);
        mListView.setOnScrollListener(new MyDecoratedAdapter.ListScrollListener(mAdapter, mResolver));
    }

  /*  private void getData() {
        new MyAsyncTask<DynamicInfo>(MainActivity.this) {

            @Override
            public DynamicInfo callService() throws IOException,
                    JsonParseException, BizException, ServiceException {
                return new StandService().getDynamicInfo(pageIndex, pageSize, StandPageTypeEnum.BESTHEAT.getPageType());
            }

            @Override
            public void onLoaded(DynamicInfo result) throws Exception {
                if (result != null) {
                    mDynamicInfo = result;
                    mAdapter = new StandPage2DAdapter2(MainActivity.this, mDynamicInfo.getStandInfos());
                    mListView.setAdapter(mAdapter);

                }
//                if (!mIsFristLoading) {
                mListView.onRefreshComplete();
               *//* }
                mIsFristLoading = false;*//*
            }

        }.executeTask();


    }*/


    private void findView() {

        mListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);


    }

}
