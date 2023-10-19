package com.example.gamifiedsurvey;

public class LeaderBoard {

       private String Name;
       private long Scores;
        LeaderBoard()
        {

        }
    public LeaderBoard(String name, int scores) {
        this.Name = name;
        this.Scores = scores;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getScores() {
        return Scores;
    }

    public void setScores(int scores) {
        Scores = scores;
    }
}
