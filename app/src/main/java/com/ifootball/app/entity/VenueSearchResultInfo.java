package com.ifootball.app.entity;

import com.neweggcn.lib.json.annotations.SerializedName;

import java.util.Collection;
import java.util.List;


public class VenueSearchResultInfo implements HasPageInfo, HasCollection<VenueSearchResultItem> {
	@SerializedName("ResultList")
	private List<VenueSearchResultItem> mVenueListItems;
	
 
	@SerializedName("PageInfo")
	private PageInfo mPageInfo;


	@Override
	public Collection<VenueSearchResultItem> getList() {
		// TODO Auto-generated method stub
		return mVenueListItems;
	}


	@Override
	public PageInfo getPageInfo() {
		// TODO Auto-generated method stub
		return mPageInfo;
	}
	
}