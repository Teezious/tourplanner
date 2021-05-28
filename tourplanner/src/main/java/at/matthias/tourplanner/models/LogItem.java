package at.matthias.tourplanner.models;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class LogItem {
    @Getter @Setter private int id;
    @Getter @Setter private LocalDate date;
    @Getter @Setter private int time;
    @Getter @Setter private int distance;
    @Getter @Setter private int rating;
    @Getter @Setter private float avgSpd;
    @Getter @Setter private int breaks;
    @Getter @Setter private int degrees;
    @Getter @Setter private String weather;
    @Getter @Setter private String activity;

    public LogItem(LocalDate date, int time, int distance, int rating, int breaks, int degrees, String weather, String activity) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.rating = rating; // TODO maybe calculate instead of user input
        this.breaks = breaks;
        this.degrees = degrees;
        this.weather = weather;
        this.activity = activity;
        calculateAvgSpeed();
    }
    public LogItem(LocalDate date, int time, int distance, int rating, float avgSpeed, int breaks, int degrees, String weather,
                   String activity, int id) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.rating = rating; // TODO maybe calculate instead of user input
        this.breaks = breaks;
        this.degrees = degrees;
        this.weather = weather;
        this.activity = activity;
        this.avgSpd = avgSpeed;
        this.id = id;
    }

    public void calculateAvgSpeed() {
        this.avgSpd = (float)distance / (float)time;
    }
}
