package com.ifootball.app.activity.found;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifootball.app.R;
import com.ifootball.app.baseapp.BaseFragment;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.found.FoundNearbyCourt;
import com.ifootball.app.entity.found.FoundRespone;
import com.ifootball.app.entity.stand.StandInfo;
import com.ifootball.app.framework.content.CBCollectionResolver;
import com.ifootball.app.framework.widget.CircleImageView;
import com.ifootball.app.util.ImageLoaderUtil;
import com.ifootball.app.util.MyAsyncTask;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.found.FoundService;
import com.neweggcn.lib.json.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NearbyCourtFragment extends BaseFragment {
    private static final int pageSize = 10;
    private int pageIndex = 0;
    private View view;
    private boolean isPrepared;
    private boolean mHasLoadedOnce;
    private CBCollectionResolver<StandInfo> mResolver;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
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

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        new MyAsyncTask<FoundRespone>(getActivity()) {

            @Override
            public FoundRespone callService() throws IOException,
                    JsonParseException, BizException, ServiceException {
                return new FoundService().getNearByCourtData(pageIndex, pageSize, 1);
            }

            @Override
            public void onLoaded(FoundRespone result) throws Exception {
                if (result != null) {
                    initView(result.getmFoundNearbyCourt());

                }
            }


        }.execute();
    }

    private void initView(List<FoundNearbyCourt> foundNearbyCourts) {

        List random = random();
        for (int i = 0; i < foundNearbyCourts.size(); i++) {
            FrameLayout layout = frameLayouts[((int) random.get(i))];
            layout.setClickable(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                frameLayouts[i].setAlpha(1);
            } else {
                frameLayouts[i].getBackground().setAlpha(255);
            }
            ImageLoaderUtil.displayImage(foundNearbyCourts.get(i).getDefaultPicUrl(), (CircleImageView) layout.getChildAt(0), R.mipmap.app_icon);
            ((TextView) layout.getChildAt(2)).setText(foundNearbyCourts.get(i).getName());

//            parentLayout.startLayoutAnimation();

        }

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
            int num = rd.nextInt(12);
            if (!randomList.contains(num)) {
                randomList.add(num);  //往集合里面添加数据。
            }
        }
        return randomList;
    }
}
    /*private RelativeLayout mRelativeLayout;
    private static boolean isFirstStart = true;
	//	private List<String> mNearbyCourts = new ArrayList<String>();
	private int screenWidth;
	private int screenHeitht;
	private boolean isShow;
	private ArrayList<DiscoveryInfo.NearbyVenue> nearbyVenues = new ArrayList<DiscoveryInfo.NearbyVenue>();
	private ArrayList<DiscoveryInfo.NearbyVenue> showNearbyVenues = new ArrayList<DiscoveryInfo.NearbyVenue>();

	public NearbyCourtFragment(ArrayList<NearbyVenue> nearbyVenues)
	{
		super();
		this.nearbyVenues = nearbyVenues;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		*//*Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
		display.getSize(point);
		screenWidth = point.x;
		screenHeitht = point.y;*//*
        //======测试数据start=====
		*//*mNearbyCourts.add("世豪广场世豪世豪广场世豪广场豪广场世豪0");
        mNearbyCourts.add("世豪广场世豪广场世豪广场世豪广场世豪广1");
		mNearbyCourts.add("世豪广场世豪广场世豪广场世豪广场世豪广2");
		mNearbyCourts.add("世豪广场世豪广场世豪广场世豪广场世豪广3");
		mNearbyCourts.add("世豪广场世豪广场世豪广场世豪广场世豪广4");
		mNearbyCourts.add("世豪广场世豪广场世豪广场世豪广场世豪广5");
		mNearbyCourts.add("世豪广场世豪广场世豪广场世豪广场世豪广6");
		mNearbyCourts.add("世豪广场世豪广场世豪广场世豪广场世豪广7");
		mNearbyCourts.add("世豪广场世豪广场世豪广场世豪广场世豪广8");
		mNearbyCourts.add("世豪广场世豪世豪广场世豪广世豪广场世豪9");
		mNearbyCourts.add("世豪广场世豪世豪10");*//*
        //======测试数据end=====

		View view = inflater.inflate(R.layout.activity_found_fragment_nearbycourt, null);
		mRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.frg_nearbycourt_flayout);
		initView(mRelativeLayout);

		//		finview(view);
		//		mThread = new Thread(this);
		//		initView();
		//		startAmin();
		for (int i = 0; i < mRelativeLayout.getChildCount(); i++)
		{
			final int index = i;
			mRelativeLayout.getChildAt(i).setOnClickListener(
					new OnClickListener()
					{

						@Override
						public void onClick(View v)
						{
							Intent intent = new Intent(getActivity(),
									VenueDetailActivity.class);
							intent.putExtra("SysNo", nearbyVenues.get(index)
									.getSysNo());
							getActivity().startActivity(intent);

						}
					});
		}
		isPrepared = true;
		return mRelativeLayout;
	}

	private void initView(RelativeLayout layout)
	{
		if (!isFirstStart)
		{
			getData();

		} else
		{

			for (int i = 0; i < nearbyVenues.size(); i++)
			{
				if (i == 10)
				{
					break;
				}
				showNearbyVenues.add(nearbyVenues.get(i));

			}
		}
		for (int i = 0; i < showNearbyVenues.size(); i++)
		{
			if (showNearbyVenues.get(i).getName().length() <= 8
					&& showNearbyVenues.get(i).getName().length() > 1)
			{
				addView(layout, 65, showNearbyVenues.get(i));
			}
			if (showNearbyVenues.get(i).getName().length() > 8
					&& showNearbyVenues.get(i).getName().length() <= 18)
			{
				addView(layout, 75, showNearbyVenues.get(i));
			}
			if (showNearbyVenues.get(i).getName().length() > 18
					&& showNearbyVenues.get(i).getName().length() <= 20)
			{
				addView(layout, 85, showNearbyVenues.get(i));
			}
		}
		AnimationUtils.startAnimationsOut(screenWidth, screenHeitht,
				mRelativeLayout, 300);
		isFirstStart = false;
	}

	private void addView(RelativeLayout layout, int size,
			NearbyVenue nearbyVenue)
	{
		RelativeLayout fLayout = new RelativeLayout(getActivity());
		LayoutParams fParams = new LayoutParams(UnitConverter.dip2px(
				getActivity(), size), UnitConverter.dip2px(getActivity(), size));
		fParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		fLayout.setLayoutParams(fParams);
		CircleImageView vImageView = new CircleImageView(getActivity());
		vImageView.setImageResource(R.drawable.app_icon);
		//		ImageLoaderUtil.displayImage(nearbyVenue.getShowDefaultPicUrl(),
		//				vImageView, R.drawable.app_icon);

		vImageView.setAlpha(0.7F);
		LayoutParams params = new LayoutParams(UnitConverter.dip2px(
				getActivity(), size), UnitConverter.dip2px(getActivity(), size));
		params.alignWithParent = true;
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		vImageView.setLayoutParams(params);
		ImageLoaderUtil.displayImage(nearbyVenue.getShowDefaultPicUrl(),
				vImageView, R.drawable.app_icon);
		fLayout.addView(vImageView);
		//		layout.addView(vImageView);

		TextView textView = new TextView(getActivity());
		LayoutParams paramsTV = new LayoutParams(UnitConverter.dip2px(
				getActivity(), size - 20), UnitConverter.dip2px(getActivity(),
				size - 25));
		paramsTV.alignWithParent = true;
		paramsTV.addRule(RelativeLayout.CENTER_IN_PARENT);
		textView.setLayoutParams(paramsTV);
		textView.setFocusable(false);
		textView.setText(nearbyVenue.getName());
		textView.setTextSize(12);
		fLayout.addView(textView);
		textView.setTextColor(Color.WHITE);
		//		layout.addView(textView);
		layout.addView(fLayout);

	}

	@Override
	public void onResume()
	{
		super.onResume();
	}

	private void startAmin()
	{

	}

	private void finview(View view)
	{

		*//*mReflash = (View) getActivity().findViewById(R.id.frg_found_reflash);*//*
        mRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.frg_nearbycourt_flayout);

	}

	@Override
	public void onStart()
	{
		super.onStart();

	}

	@Override
	protected void onInVisible()
	{

	}

	@Override
	protected void getData()
	{
		if (isFirstStart)
		{
			return;
		}
		if (nearbyVenues.size() < 11)
		{
			return;
		}
		*//*for (int i = 9; i < nearbyVenues.size(); i += 9)
        {
			if (showNearbyVenues.get(showNearbyVenues.size() - 1).getSysNo() == nearbyVenues
					.get(i).getSysNo())
			{
				showNearbyVenues.clear();
				for (int j = i; j < (i == nearbyVenues.size() - 1 ? i : i + 10); j++)
				{
					showNearbyVenues.add(nearbyVenues.get(j));
				}
			}
		}*//*
    }

	@Override
	protected void installView()
	{
		isFirstStart = true;
	}*/

