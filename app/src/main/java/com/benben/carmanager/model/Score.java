package com.benben.carmanager.model;
//设置信息类
public class Score {
    private int _id;        //Id
    private int maxScore;       //
    private int preScore;
    private int userId;


    public Score(int maxScore, int preScore, int userId) {
        this.maxScore = maxScore;
        this.preScore = preScore;
        this.userId = userId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getPreScore() {
        return preScore;
    }

    public void setPreScore(int preScore) {
        this.preScore = preScore;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
