package com.ifootball.app.activity.career;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifootball.app.R;
import com.ifootball.app.activity.base.BaseActivity;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.career.CareerBaseInfo;
import com.ifootball.app.framework.widget.CircleImageView;
import com.ifootball.app.framework.widget.NavigationHelper;
import com.ifootball.app.util.ExitAppUtil;
import com.ifootball.app.util.ImageLoaderUtil;
import com.ifootball.app.util.MyAsyncTask;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.career.CareerService;
import com.neweggcn.lib.json.JsonParseException;

import java.io.IOException;

public class CareerActivity extends BaseActivity {
    private CircleImageView headerImageImg;
    private ImageView friendTrendRedPointImg;
    private TextView nameTv;
    private TextView positionTv;
    private TextView callTv;
    private TextView topicTv;
    private TextView friendsTv;
    private TextView describeTv;
    private TextView friendTrendRemindTv;
    private TextView placeTv;
    private RelativeLayout recordReLayout;
    private RelativeLayout oneselfTrendReLayout;
    private RelativeLayout friendTrendReLayout;
    private RelativeLayout changePasswordReLayout;
    private RelativeLayout aboutReLayout;
    private RelativeLayout settingReLayout;
    private RelativeLayout quitReLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        putContentView(R.layout.activity_career, "", NavigationHelper.CAREER, true, true);
        findView();
        getData();

    }

    private void findView() {
        headerImageImg = (CircleImageView) findViewById(R.id.activity_career_head_circle);
        friendTrendRedPointImg = (ImageView) findViewById(R.id.activity_career_friend_trend_red_point);
        nameTv = (TextView) findViewById(R.id.activity_career_head_name);
        positionTv = (TextView) findViewById(R.id.activity_career_head_position);
        callTv = (TextView) findViewById(R.id.activity_career_head_call);
        topicTv = (TextView) findViewById(R.id.activity_career_head_bottom_topic);
        friendsTv = (TextView) findViewById(R.id.activity_career_head_bottom_friend);
        describeTv = (TextView) findViewById(R.id.activity_career_describe_tv);
        placeTv = (TextView) findViewById(R.id.activity_career_describe_place);
        friendTrendRemindTv = (TextView) findViewById(R.id.activity_career_friend_trend_remind);
        recordReLayout = (RelativeLayout) findViewById(R.id.activity_career_record_layout);
        oneselfTrendReLayout = (RelativeLayout) findViewById(R.id.activity_career_friend_trend_layout);
        friendTrendReLayout = (RelativeLayout) findViewById(R.id.activity_career_friend_trend_layout);
        changePasswordReLayout = (RelativeLayout) findViewById(R.id.activity_career_change_password_layout);
        aboutReLayout = (RelativeLayout) findViewById(R.id.activity_career_about_layout);
        settingReLayout = (RelativeLayout) findViewById(R.id.activity_career_setting_layout);
        quitReLayout = (RelativeLayout) findViewById(R.id.activity_career_quit_layout);
    }

    public void getData() {

        new MyAsyncTask<CareerBaseInfo>(CareerActivity.this) {

            @Override
            public CareerBaseInfo callService() throws IOException,
                    JsonParseException, BizException, ServiceException {
                return new CareerService().getCareerHomeVM();
            }

            @Override
            public void onLoaded(CareerBaseInfo result) throws Exception {
                if (result != null) {
                    initView(result);
                }
            }
        }.execute();
    }


    private void initView(CareerBaseInfo result) {
        if (result.getAvatarPath() != null) {
            ImageLoaderUtil.displayImage(result.getAvatarPath(), headerImageImg, R.mipmap.app_icon);
        }
        nameTv.setText(result.getNickName());
        positionTv.setText(result.getPosition());
        topicTv.setText(result.getTopicCount());
        friendsTv.setText(result.getFriendCount());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ExitAppUtil.exit(this);

        }
        return super.onKeyDown(keyCode, event);
    }


}
