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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifootball.app.R;
import com.ifootball.app.activity.green.VenueDetailActivity;
import com.ifootball.app.baseapp.BaseFragment;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.found.FoundNearbyCourt;
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
import java.util.List;
import java.util.Random;

public class NearbyCourtFragment extends BaseFragment {
    private static final int pageSize = 10;
    private int pageIndex = 0;
    private View view;
    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    private FrameLayout[] frameLayouts = new FrameLayout[11];
    private RelativeLayout parentLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {

            view = inflater.inflate(R.layout.activity_found_fragment_nearbycourt, null);
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
        frameLayouts[0] = (FrameLayout) view.findViewById(R.id.ball_one_layout);
        frameLayouts[1] = (FrameLayout) view.findViewById(R.id.ball_two_layout);
        frameLayouts[2] = (FrameLayout) view.findViewById(R.id.ball_three_layout);
        frameLayouts[3] = (FrameLayout) view.findViewById(R.id.ball_four_layout);
        frameLayouts[4] = (FrameLayout) view.findViewById(R.id.ball_five_layout);
        frameLayouts[5] = (FrameLayout) view.findViewById(R.id.ball_six_layout);
        frameLayouts[6] = (FrameLayout) view.findViewById(R.id.ball_seven_layout);
        frameLayouts[7] = (FrameLayout) view.findViewById(R.id.ball_eight_layout);
        frameLayouts[8] = (FrameLayout) view.findViewById(R.id.ball_nine_layout);
        frameLayouts[9] = (FrameLayout) view.findViewById(R.id.ball_ten_layout);
        frameLayouts[10] = (FrameLayout) view.findViewById(R.id.ball_eleven_layout);
        parentLayout = (RelativeLayout) view.findViewById(R.id.found_nearbycourt_flayout);
        recoverView();
    }

    private void setAnim() {
        Animation animation= AnimationUtils.loadAnimation(getActivity(), R.anim.found_img_anim);

        //得到一个LayoutAnimationController对象；

        LayoutAnimationController lac=new LayoutAnimationController(animation);

        //设置控件显示的顺序；

        lac.setOrder(LayoutAnimationController.ORDER_RANDOM);

        //设置控件显示间隔时间；

        lac.setDelay(1);

        //为ListView设置LayoutAnimationController属性；

        parentLayout.setLayoutAnimation(lac);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        new MyAsyncTask<FoundRespone>(getActivity()) {

            @Override
            public FoundRespone callService() throws IOException,
                    JsonParseException, BizException, ServiceException {
                FoundRespone nearByCourtData = new FoundService().getNearByCourtData(pageIndex, pageSize, 1);
                if (nearByCourtData != null) {
                    mHasLoadedOnce = true;
                    pageIndex = pageIndex + 1;
                }
                return nearByCourtData;
            }

            @Override
            public void onLoaded(FoundRespone result) throws Exception {
                if (result != null) {
                    initView(result.getmFoundNearbyCourt());

                }
            }


        }.execute();
    }

    private void initView(final List<FoundNearbyCourt> foundNearbyCourts) {
        recoverView();
        List random = random();
        for (int i = 0; i < foundNearbyCourts.size(); i++) {
            Log.d("TEST", random.get(i) + "");
            FrameLayout layout = frameLayouts[((int) random.get(i))];
            final FoundNearbyCourt foundNearbyCourt = foundNearbyCourts.get(i);
            ImageLoaderUtil.displayImage(foundNearbyCourt.getDefaultPicUrl(), (CircleImageView) layout.getChildAt(0), R.mipmap.app_icon);
            ((TextView) layout.getChildAt(2)).setText(foundNearbyCourt.getName());
            layout.setClickable(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                layout.setAlpha(1);
            } else {
                layout.getBackground().setAlpha(255);
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(VenueDetailActivity.SYSNO, foundNearbyCourt.getSysNo());
                    IntentUtil.redirectToNextActivity(getActivity(), VenueDetailActivity.class, bundle);
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
}

