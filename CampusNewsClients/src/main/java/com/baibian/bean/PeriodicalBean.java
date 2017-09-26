package com.baibian.bean;

/**
 * Created by Liao
 *期刊数据结构
 */

public class PeriodicalBean {

    private int cover;//期刊图片的地址
    private String title;//期刊题目
    private String brief ;//期刊简介
    private String date;//日期
    private long readNumber;//阅读数
    private long fansNumber;//粉丝数
    private long recommendNumber;//推荐数
    private int freeChapters=1;//免费章节数
    private int totalChapters;//总章节数
    private int purchaseChapters;//购买章节数
    private double price=5;//购买章节的费用
    private double discount;//章节打折情况
    private boolean isRecommend;// 推荐
    private  boolean isFocus;//收藏




   public PeriodicalBean(){

    }

    public PeriodicalBean(int cover, String title, String brief, long readNumber){
        this.cover=cover;
        this.title=title;
        this.brief=brief;
        this.readNumber=readNumber;
    }

    public int getCover() {
        return cover;
    }
    public void setCover(int cover) {
        this.cover = cover;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public String getBrief() {
        return brief;
    }
    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setReadNumber(long readNumber) {
        this.readNumber = readNumber;
    }
    public long getReadNumber() {
        return readNumber;
    }

    public long getRecommendNumber() {
        return recommendNumber;
    }
    public void setRecommendNumber(long recommendNumber) {
        this.recommendNumber = recommendNumber;
    }

    public void setFansNumber(long fansNumber) {
        this.fansNumber = fansNumber;
    }
    public long getFansNumber() {
        return fansNumber;
    }

    public boolean isFocus() {
        return isFocus;
    }
    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public boolean isRecommend() {
        return isRecommend;
    }
    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public int getFreeChapters() {
        return freeChapters;
    }
    public int getPurchaseChapters() {
        return purchaseChapters;
    }
    public int getTotalChapters() {
        return totalChapters;
    }
    public void setFreeChapters(int freeChapters) {
        this.freeChapters = freeChapters;
    }
    public void setPurchaseChapters(int purchaseChapters) {
        this.purchaseChapters = purchaseChapters;
    }
    public void setTotalChapters(int totalChapters) {
        this.totalChapters = totalChapters;
    }


    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}