package com.example.mid_term_assignment_air_quality.entities;

public class AirQualityData {

    private double aqi;
    private double o3;
    private double co;
    private double so2;
    private double no2;
    private double pm25;
    private double pm10;
    private int pollen_level_tree ;
    private int pollen_level_grass ;
    private int pollen_level_weed;
    private int mold_level;

    public AirQualityData(double aqi, double o3, double co, double so2, double no2, double pm25, double pm10, int pollen_level_tree, int pollen_level_grass, int pollen_level_weed, int mold_level) {
        this.aqi = aqi;
        this.o3 = o3;
        this.co = co;
        this.so2 = so2;
        this.no2 = no2;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.pollen_level_tree = pollen_level_tree;
        this.pollen_level_grass = pollen_level_grass;
        this.pollen_level_weed = pollen_level_weed;
        this.mold_level = mold_level;
    }

    public AirQualityData() {
    }

    public double getAqi() {
        return aqi;
    }

    public void setAqi(double aqi) {
        this.aqi = aqi;
    }

    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getSo2() {
        return so2;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public double getNo2() {
        return no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getPm25() {
        return pm25;
    }

    public void setPm25(double pm25) {
        this.pm25 = pm25;
    }
    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public int getPollen_level_tree() {
        return pollen_level_tree;
    }

    public void setPollen_level_tree(int pollen_level_tree) {
        this.pollen_level_tree = pollen_level_tree;
    }

    public int getPollen_level_grass() {
        return pollen_level_grass;
    }

    public void setPollen_level_grass(int pollen_level_grass) {
        this.pollen_level_grass = pollen_level_grass;
    }

    public int getPollen_level_weed() {
        return pollen_level_weed;
    }

    public void setPollen_level_weed(int pollen_level_weed) {
        this.pollen_level_weed = pollen_level_weed;
    }

    public int getMold_level() {
        return mold_level;
    }

    public void setMold_level(int mold_level) {
        this.mold_level = mold_level;
    }

    @Override
    public String toString() {
        return "AirQualityData{" +
                "aqi=" + aqi +
                ", o3=" + o3 +
                ", co=" + co +
                ", so2=" + so2 +
                ", no2=" + no2 +
                ", pm25=" + pm25 +
                ", pm10=" + pm10 +
                ", pollen_level_tree=" + pollen_level_tree +
                ", pollen_level_grass=" + pollen_level_grass +
                ", pollen_level_weed=" + pollen_level_weed +
                ", mold_level" + mold_level +
                '}';
    }
}
