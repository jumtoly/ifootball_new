package com.ifootball.app.activity.found;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifootball.app.R;
import com.ifootball.app.baseapp.BaseFragment;

public class NearbyCourtFragment extends BaseFragment {
    private View view;
    private boolean isPrepared;

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
    }

    @Override
    protected void lazyLoad() {

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

