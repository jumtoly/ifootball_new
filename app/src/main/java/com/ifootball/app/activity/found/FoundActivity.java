package com.ifootball.app.activity.found;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.activity.stand.StandBestHeatFragment;
import com.ifootball.app.activity.stand.StandNearByFragment;
import com.ifootball.app.activity.stand.StandRostrumFragment;
import com.ifootball.app.framework.widget.CircleImageView;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.ifootball.app.util.ExitAppUtil;

import java.util.ArrayList;
import java.util.List;

public class FoundActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final int BALL = 0;
    private static final int FRIEND = 1;

    private ViewPager viewPager;
    private List<Fragment> listViews;
    private MyFragmentPagerAdapter mAdapter;
    private Button ballBtn;
    private Button friendBtn;
    private ImageView ballImg;
    private ImageView friendImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_found, "", NavigationHelper.FOUND, true, true);
        findview();
        setCircleImg();
        initViewPager();
    }

    private void setCircleImg() {
        CircleImageView mImageView = (CircleImageView) findViewById(R.id.frg_found_center);
        mImageView.setImageResource(R.mipmap.app_icon);
        mImageView.bringToFront();
    }

    private void findview() {
        ballBtn = (Button) findViewById(R.id.found_bar_ball);
        friendBtn = (Button) findViewById(R.id.found_bar_friend);
        ballImg = (ImageView) findViewById(R.id.found_bar_ball_line);
        friendImg = (ImageView) findViewById(R.id.found_bar_friend_line);

        viewPager = (ViewPager) findViewById(R.id.frg_found_vPager);

        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(this);
    }

    private void initViewPager() {
        listViews = new ArrayList<>();

        listViews.add(new NearbyCourtFragment());
        listViews.add(new NearbyFriendsFragment());

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listViews);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(BALL);
        setSelectBar(BALL);
    }

    private void setSelectBar(int type) {
        restoreViewState();
        switch (type) {
            case BALL:
                ballBtn.getPaint().setFakeBoldText(true);
                ballBtn.postInvalidate();
                ballImg.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(BALL);
                break;
            case FRIEND:
                friendBtn.getPaint().setFakeBoldText(true);
                friendBtn.postInvalidate();
                friendImg.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(FRIEND);
                break;
        }
    }

    private void restoreViewState() {
        ballBtn.getPaint().setFakeBoldText(false);
        ballBtn.postInvalidate();
        ballImg.setVisibility(View.GONE);

        friendBtn.getPaint().setFakeBoldText(false);
        friendBtn.postInvalidate();
        friendImg.setVisibility(View.GONE);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ExitAppUtil.exit(this);

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        setSelectBar(i);

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> listViews = new ArrayList<Fragment>();

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> listViews) {
            super(fm);
            this.listViews = listViews;
        }


        @Override
        public int getCount() {
            return listViews.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return listViews.get(arg0);
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//		contentViewPager.clearOnPageChangeListeners();
    }
}
