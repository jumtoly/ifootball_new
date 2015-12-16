package com.ifootball.app.entity.found;

import com.neweggcn.lib.json.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by liu.yao on 2015/12/16.
 */
public class FoundNearbyFriend implements Serializable {

    @SerializedName("CustomerSysNo")
    private int CustomerSysNo;

    @SerializedName("NickName")
    private String NickName;

    @SerializedName("AvatarUrl")
    private String AvatarUrl;

    @SerializedName("Latitude")
    private float Latitude;

    @SerializedName("Longitude")
    private float Longitude;

    public int getCustomerSysNo() {
        return CustomerSysNo;
    }

    public void setCustomerSysNo(int customerSysNo) {
        CustomerSysNo = customerSysNo;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }
}
