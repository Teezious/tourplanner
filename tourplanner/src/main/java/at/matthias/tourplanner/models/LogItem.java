package at.matthias.tourplanner.models;

import at.matthias.tourplanner.models.Weather;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

public class LogItem {
    @Getter @Setter private int logId;
    @Getter @Setter private LocalDate date;
    @Getter @Setter private int time;
    @Getter @Setter private int distance;
    @Getter @Setter private int rating;
    @Getter @Setter private float avgSpd;
    @Getter @Setter private int breaks;
    @Getter @Setter private int degrees;
    @Getter @Setter private Weather weather;
    @Getter @Setter private Activity activity;

    public LogItem(LocalDate date, int time, int distance, int rating, int breaks, int degrees, String weather, String activity) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.rating = rating; // TODO maybe calculate instead of user input
        this.breaks = breaks;
        this.degrees = degrees;
        this.weather = Weather.valueOf(weather.toUpperCase());
        this.activity = Activity.valueOf(activity.toUpperCase());
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
        this.weather = Weather.valueOf(weather.toUpperCase());
        this.activity = Activity.valueOf(activity.toUpperCase());
        this.avgSpd = avgSpeed;
        this.logId = id;
    }

    public void calculateAvgSpeed() {
        this.avgSpd = (float)distance / (float)time;
    }
}
