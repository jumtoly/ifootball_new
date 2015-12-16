package com.ifootball.app.entity.found;

import com.neweggcn.lib.json.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by liu.yao on 2015/12/16.
 */
public class FoundRequest implements Serializable {
    @SerializedName("CurLatitude")
    private float CurLatitude;// 纬度
    @SerializedName("CurLongitude")
    private float CurLongitude;// 经度
    @SerializedName("listtype")
    private int Type;
    @SerializedName("pageindex")
    private int pageindex;
    @SerializedName("pagesize")
    private int pagesize;

    public FoundRequest(float curLatitude, float curLongitude, int type, int pageindex, int pagesize) {
        CurLatitude = curLatitude;
        CurLongitude = curLongitude;
        Type = type;
        this.pageindex = pageindex;
        this.pagesize = pagesize;
    }

    public float getCurLatitude() {
        return CurLatitude;
    }

    public void setCurLatitude(float curLatitude) {
        CurLatitude = curLatitude;
    }

    public float getCurLongitude() {
        return CurLongitude;
    }

    public void setCurLongitude(float curLongitude) {
        CurLongitude = curLongitude;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getPageindex() {
        return pageindex;
    }

    public void setPageindex(int pageindex) {
        this.pageindex = pageindex;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
