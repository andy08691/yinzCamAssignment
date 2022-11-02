package com.example.yinzcamassignment.data;

import java.io.Serializable;

public class ScheduleBean implements Serializable {
    private String Week, GameState, AwayScore, HomeScore, Type;
    private Long Timestamp;
    private TeamBean Opponent;
    public ScheduleBean() {
    }

    public String getWeek() {
        return Week;
    }

    public void setWeek(String week) {
        Week = week;
    }

    public String getGameState() {
        return GameState;
    }

    public void setGameState(String gameState) {
        GameState = gameState;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }

    public String getAwayScore() {
        return AwayScore;
    }

    public void setAwayScore(String awayScore) {
        AwayScore = awayScore;
    }

    public String getHomeScore() {
        return HomeScore;
    }

    public void setHomeScore(String homeScore) {
        HomeScore = homeScore;
    }

    public TeamBean getOpponent() {
        return Opponent;
    }

    public void setOpponent(TeamBean opponent) {
        Opponent = opponent;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "ScheduleBean{" +
                "Week='" + Week + '\'' +
                ", GameState='" + GameState + '\'' +
                ", AwayScore='" + AwayScore + '\'' +
                ", HomeScore='" + HomeScore + '\'' +
                ", Type='" + Type + '\'' +
                ", Timestamp=" + Timestamp +
                ", Opponent=" + Opponent +
                '}';
    }
}
