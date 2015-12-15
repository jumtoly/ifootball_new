package com.ifootball.app.activity.found;

import com.ifootball.app.baseapp.BaseFragment;

public class NearbyFriendsFragment extends BaseFragment {
    @Override
    protected void lazyLoad() {

    }

	/*private RelativeLayout mRelativeLayout;
    //	private List<String> mNearbyCourts = new ArrayList<String>();
	private int screenWidth;
	private int screenHeitht;
	private boolean isShow;
	private List<DiscoveryInfo.NearbyCustomer> nearbyCustomers = new ArrayList<DiscoveryInfo.NearbyCustomer>();

	public NearbyFriendsFragment(
			List<DiscoveryInfo.NearbyCustomer> nearbyCustomers)
	{
		super();
		this.nearbyCustomers = nearbyCustomers;
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

		View view = inflater.inflate(R.layout.activity_found_fragment_nearbyfriends, null);
		mRelativeLayout = (RelativeLayout) view
				.findViewById(R.id.frg_nearbyfriend_flayout);
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
									FriendDetailActivity.class);
							intent.putExtra("NEARBY_CUSTOMER_SYSNO",
									nearbyCustomers.get(index)
											.getCustomerSysNo());
							getActivity().startActivity(intent);
						}
					});
		}
		isPrepared = true;
		return mRelativeLayout;
	}

	private void initView(RelativeLayout layout)
	{
		for (int i = 0; i < nearbyCustomers.size(); i++)
		{
			addView(layout, 75, nearbyCustomers.get(i));
		}
		if (!isCurrentVisible || !isPrepared)
		{
			return;
		}
		AnimationUtils.startAnimationsOut(screenWidth, screenHeitht,
				mRelativeLayout, 300);
	}

	private void addView(RelativeLayout layout, int size,
			NearbyCustomer nearbyCustomers)
	{
		RelativeLayout fLayout = new RelativeLayout(getActivity());
		LayoutParams fParams = new LayoutParams(UnitConverter.dip2px(
				getActivity(), size), UnitConverter.dip2px(getActivity(), size
				+ size / 5 + size / 2));
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
		ImageLoaderUtil.displayImage(nearbyCustomers.getSvatarUrl(),
				vImageView, R.drawable.app_icon);
		fLayout.addView(vImageView);
		//		layout.addView(vImageView);

		TextView textView = new TextView(getActivity());
		LayoutParams paramsTV = new LayoutParams(UnitConverter.dip2px(
				getActivity(), size), UnitConverter.dip2px(getActivity(),
				size / 3));
		paramsTV.alignWithParent = true;
		paramsTV.addRule(RelativeLayout.ALIGN_BOTTOM);
		paramsTV.addRule(RelativeLayout.CENTER_HORIZONTAL);
		textView.setLayoutParams(paramsTV);
		textView.setFocusable(false);
		textView.setText(nearbyCustomers.getNickName());
		textView.setTextSize(12);
		fLayout.addView(textView);
		fLayout.setVisibility(View.GONE);
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

		//		AnimationUtils.startAnimations(mRelativeLayout);

	}

	@Override
	protected void onInVisible()
	{
	}

	@Override
	protected void getData()
	{
		if (!isCurrentVisible || !isPrepared)
		{
			return;
		}
		if (!isShow)
		{
			AnimationUtils.startAnimationsOut(screenWidth, screenHeitht,
					mRelativeLayout, 300);
			isShow = true;
		}
	}

	@Override
	protected void installView()
	{

	}*/

}
