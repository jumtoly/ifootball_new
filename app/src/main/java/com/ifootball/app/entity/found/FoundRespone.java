package com.ifootball.app.entity.found;

import com.ifootball.app.entity.HasPageInfo;
import com.ifootball.app.entity.PageInfo;
import com.neweggcn.lib.json.annotations.SerializedName;

import java.util.List;

/**
 * Created by liu.yao on 2015/12/16.
 */
public class FoundRespone implements HasPageInfo {
    @SerializedName("NearbyVenue")
    private List<FoundNearbyCourt> mFoundNearbyCourt;

    @SerializedName("NearbyCustomer")
    private List<FoundNearbyFriend> mFoundNearbyFriend;


    @SerializedName("PageInfo")
    private PageInfo mPageInfo;


    public List<FoundNearbyCourt> getmFoundNearbyCourt() {
        return mFoundNearbyCourt;
    }

    public void setmFoundNearbyCourt(List<FoundNearbyCourt> mFoundNearbyCourt) {
        this.mFoundNearbyCourt = mFoundNearbyCourt;
    }

    public List<FoundNearbyFriend> getmFoundNearbyFriend() {
        return mFoundNearbyFriend;
    }

    public void setmFoundNearbyFriend(List<FoundNearbyFriend> mFoundNearbyFriend) {
        this.mFoundNearbyFriend = mFoundNearbyFriend;
    }


    @Override
    public PageInfo getPageInfo() {
        // TODO Auto-generated method stub
        return mPageInfo;
    }


}
