package com.ifootball.app.entity.found;

import com.neweggcn.lib.json.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by liu.yao on 2015/12/16.
 */
public class FoundNearbyCourt implements Serializable {
    @SerializedName("SysNo")
    private int SysNo;

    @SerializedName("Name")
    private String Name;

    @SerializedName("ShowDefaultPicUrl")
    private String DefaultPicUrl;

    @SerializedName("Latitude")
    private float Latitude;

    @SerializedName("Longitude")
    private float Longitude;

    public int getSysNo() {
        return SysNo;
    }

    public void setSysNo(int sysNo) {
        SysNo = sysNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDefaultPicUrl() {
        return DefaultPicUrl;
    }

    public void setDefaultPicUrl(String defaultPicUrl) {
        DefaultPicUrl = defaultPicUrl;
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
