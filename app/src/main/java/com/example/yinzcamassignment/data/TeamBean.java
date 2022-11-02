package com.example.yinzcamassignment.data;

import java.io.Serializable;

public class TeamBean implements Serializable {
    private String TriCode, FullName, Name, City, Record, Wins, Losses, WinPercentage;

    public TeamBean() {
    }

    public String getTriCode() {
        return TriCode;
    }

    public void setTriCode(String triCode) {
        TriCode = triCode;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getRecord() {
        return Record;
    }

    public void setRecord(String record) {
        Record = record;
    }

    public String getWins() {
        return Wins;
    }

    public void setWins(String wins) {
        Wins = wins;
    }

    public String getLosses() {
        return Losses;
    }

    public void setLosses(String losses) {
        Losses = losses;
    }

    public String getWinPercentage() {
        return WinPercentage;
    }

    public void setWinPercentage(String winPercentage) {
        WinPercentage = winPercentage;
    }

    @Override
    public String toString() {
        return "TeamBean{" +
                "TriCode='" + TriCode + '\'' +
                ", FullName='" + FullName + '\'' +
                ", Name='" + Name + '\'' +
                ", City='" + City + '\'' +
                ", Record='" + Record + '\'' +
                ", Wins='" + Wins + '\'' +
                ", Losses='" + Losses + '\'' +
                ", WinPercentage='" + WinPercentage + '\'' +
                '}';
    }
}
