package com.dudu.prudential.models;

public class UserInfo {
    private String mName;
    private int mScore;
    private String mHeadIconUrl;

    public UserInfo(String mName, int mScore, String headIconUrl) {
        this.mName = mName;
        this.mScore = mScore;
        this.mHeadIconUrl = headIconUrl;
    }

    public UserInfo(String mName) {
        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        this.mScore = score;
    }

    public String getHeadIconUrl() {
        return mHeadIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.mHeadIconUrl = headIconUrl;
    }
}
