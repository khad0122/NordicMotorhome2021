package com.example.nordicmotorhome;

public class Season {
    private int price_percent;
    private String season_name;
    private String season_from;
    private String season_to;
    private int season_ID;

    public int getPrice_percent() { return price_percent; }
    public void setPrice_percent(int price_percent) { this.price_percent = price_percent; }

    public String getSeason_name() { return season_name; }
    public void setSeason_name(String season_name) { this.season_name = season_name; }

    public String getSeason_from() { return season_from; }
    public void setSeason_from(String season_from) { this.season_from = season_from; }

    public String getSeason_to() { return season_to; }
    public void setSeason_to(String season_to) { this.season_to = season_to; }

    public int getSeason_ID(){return season_ID;}
    public void setSeason_ID(int id){season_ID = id;}
}
