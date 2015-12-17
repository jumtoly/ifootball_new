package com.ifootball.app.activity.found;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifootball.app.R;
import com.ifootball.app.activity.MapActivity;
import com.ifootball.app.baseapp.BaseFragment;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.found.FoundNearbyCourt;
import com.ifootball.app.entity.found.FoundNearbyFriend;
import com.ifootball.app.entity.found.FoundRespone;
import com.ifootball.app.framework.widget.CircleImageView;
import com.ifootball.app.util.ImageLoaderUtil;
import com.ifootball.app.util.IntentUtil;
import com.ifootball.app.util.MyAsyncTask;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.found.FoundService;
import com.neweggcn.lib.json.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NearbyFriendsFragment extends BaseFragment implements FoundActivity.OnButtonOnclickListener {
    private static final int pageSize = 10;
    private int pageIndex = 0;
    private View view;
    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    private LinearLayout[] frameLayouts = new LinearLayout[11];
    private RelativeLayout parentLayout;
    private List<FoundNearbyFriend> foundNearbyFriends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {

            view = inflater.inflate(R.layout.activity_found_fragment_nearbyfriends, null);
            findView(view);
            isPrepared = true;
            lazyLoad();
        }


        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }


    private void findView(View view) {
        frameLayouts[0] = (LinearLayout) view.findViewById(R.id.friend_one_layout);
        frameLayouts[1] = (LinearLayout) view.findViewById(R.id.friend_two_layout);
        frameLayouts[2] = (LinearLayout) view.findViewById(R.id.friend_three_layout);
        frameLayouts[3] = (LinearLayout) view.findViewById(R.id.friend_four_layout);
        frameLayouts[4] = (LinearLayout) view.findViewById(R.id.friend_five_layout);
        frameLayouts[5] = (LinearLayout) view.findViewById(R.id.friend_six_layout);
        frameLayouts[6] = (LinearLayout) view.findViewById(R.id.friend_seven_layout);
        frameLayouts[7] = (LinearLayout) view.findViewById(R.id.friend_eight_layout);
        frameLayouts[8] = (LinearLayout) view.findViewById(R.id.friend_nine_layout);
        frameLayouts[9] = (LinearLayout) view.findViewById(R.id.friend_ten_layout);
        frameLayouts[10] = (LinearLayout) view.findViewById(R.id.friend_eleven_layout);
        parentLayout = (RelativeLayout) view.findViewById(R.id.found_nearbyfriend_flayout);
        recoverView();
    }

    private void setAnim() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.found_img_anim);
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);
        lac.setDelay((float) 0.3);
        parentLayout.setLayoutAnimation(lac);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        getData();
    }

    private void getData() {
        new MyAsyncTask<FoundRespone>(getActivity()) {

            @Override
            public FoundRespone callService() throws IOException,
                    JsonParseException, BizException, ServiceException {
                FoundRespone nearByCourtData = new FoundService().getNearByCourtData(pageIndex, pageSize, 1);
                if (nearByCourtData != null) {
                    mHasLoadedOnce = true;
//                    pageIndex = pageIndex + 1;
                }
                return nearByCourtData;
            }

            @Override
            public void onLoaded(FoundRespone result) throws Exception {
                if (result != null) {
                    foundNearbyFriends = result.getmFoundNearbyFriend();
                    initView();

                }
            }


        }.execute();
    }

    private void initView() {
        recoverView();
        List random = random();
        for (int i = 0; i < foundNearbyFriends.size(); i++) {
            Log.d("TEST", random.get(i) + "");
            LinearLayout layout = frameLayouts[((int) random.get(i))];
            final FoundNearbyFriend foundNearbyCourt = foundNearbyFriends.get(i);
            ImageLoaderUtil.displayImage(foundNearbyCourt.getAvatarUrl(), (CircleImageView) layout.getChildAt(0), R.mipmap.app_icon);
            if (layout.getChildCount() == 2) {
                ((TextView) layout.getChildAt(1)).setText(foundNearbyCourt.getNickName());
            }
            layout.setClickable(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                layout.setAlpha(1);
            } else {
                layout.getBackground().setAlpha(255);
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Bundle bundle = new Bundle();
                    bundle.putInt(VenueDetailActivity.SYSNO, foundNearbyCourt.getSysNo());
                    IntentUtil.redirectToNextActivity(getActivity(), VenueDetailActivity.class, bundle);*/
                }
            });

        }
        setAnim();
        parentLayout.startLayoutAnimation();

    }

    private void recoverView() {
        for (int i = 0; i < frameLayouts.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                frameLayouts[i].setAlpha(0F);
            } else {
                frameLayouts[i].getBackground().setAlpha(0);
            }
            frameLayouts[i].setClickable(false);
        }
    }


    private List random() {
        List randomList = new ArrayList();  //生成数据集，用来保存随即生成数，并用于判断
        Random rd = new Random();
        while (randomList.size() < 11) {
            int num = rd.nextInt(11);
            if (!randomList.contains(num)) {
                randomList.add(num);  //往集合里面添加数据。
            }
        }
        return randomList;
    }

    @Override
    public void onMapButtonClick() {
        HashMap<String, String> map = new HashMap<>();
        for (FoundNearbyFriend courtInfo : foundNearbyFriends) {
            map.put(String.valueOf(courtInfo.getLatitude()), String.valueOf(courtInfo.getLongitude()));
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(MapActivity.COURT_LOCATION, map);
        IntentUtil.redirectToNextActivity(getActivity(), MapActivity.class, bundle);
    }

    @Override
    public void onRefreshButtonClick() {
        getData();
    }
}
