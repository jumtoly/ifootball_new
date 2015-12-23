package com.ifootball.app.entity.career;

import com.neweggcn.lib.json.annotations.SerializedName;

/**
 * Created by liu.yao on 2015/12/23.
 */
public class CareerBaseInfo {
    @SerializedName("AvatarPath")
    private String AvatarPath;

    @SerializedName("NickName")
    private String NickName;

    @SerializedName("Position")
    private String Position;

    @SerializedName("TopicCount")
    private int TopicCount;

    @SerializedName("FriendCount")
    private int FriendCount;

    public String getAvatarPath() {
        return AvatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        AvatarPath = avatarPath;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public int getTopicCount() {
        return TopicCount;
    }

    public void setTopicCount(int topicCount) {
        TopicCount = topicCount;
    }

    public int getFriendCount() {
        return FriendCount;
    }

    public void setFriendCount(int friendCount) {
        FriendCount = friendCount;
    }
}
