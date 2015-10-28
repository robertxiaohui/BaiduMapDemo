package com.jerry.bdmapdemo;

/**
 * Created by Administrator on 2015/10/26.
 */
public class CoordBean {
    public float showMinLevel;//显示最低等级
    public float showMaxLevel;
    public double X;
    public double Y;
    public int number;
    public String address;


    public CoordBean(float showMinLevel, float showMaxLevel, double x, double y, int number, String address) {
        this.showMinLevel = showMinLevel;
        this.showMaxLevel = showMaxLevel;
        X = x;
        Y = y;
        this.number = number;
        this.address = address;
    }

    public float getShowMaxLevel() {
        return showMaxLevel;
    }

    public void setShowMaxLevel(int showMaxLevel) {
        this.showMaxLevel = showMaxLevel;
    }

    public float getShowMinLevel() {
        return showMinLevel;
    }

    public void setShowMinLevel(int showMinLevel) {
        this.showMinLevel = showMinLevel;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CoordBean{" +
                "showMinLevel=" + showMinLevel +
                ", showMaxLevel=" + showMaxLevel +
                ", X=" + X +
                ", Y=" + Y +
                ", number=" + number +
                ", address='" + address + '\'' +
                '}';
    }

}
