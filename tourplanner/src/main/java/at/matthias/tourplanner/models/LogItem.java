package at.matthias.tourplanner.models;

import at.matthias.tourplanner.models.Weather;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

public class LogItem {
    @Getter @Setter private int logId;
    @Getter @Setter private LocalDate date;
    @Getter @Setter private LocalTime time;
    @Getter @Setter private float distance;
    @Getter @Setter private int rating;
    @Getter @Setter private float avgSpd;
    @Getter @Setter private int breaks;
    @Getter @Setter private int degrees;
    @Getter @Setter private Weather weather;
    @Getter @Setter private Activity activity;

    // TODO Constructor
}
