package com.avision_amc.africavisionapp;

public class Contestant {
    private String countryName;
    private int flagResId;
    private String songName;
    private String callID;



    Contestant(String countryName, int flagResId, String songName, String callID)
    {
        this.countryName=countryName;
        this.flagResId=flagResId;
        this.songName=songName;
        this.callID=callID;

    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getFlagResId() {
        return flagResId;
    }

    public void setFlagResId(int flagResId) {
        this.flagResId = flagResId;
    }

    public String getCallID() {
        return callID;
    }

    public void setCallID(String callID) {
        this.callID = callID;
    }
}
