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
  @Getter @Setter private float speed;
  @Getter @Setter private int breaks;
  @Getter @Setter private int degrees;
  @Getter @Setter private String weather;
  @Getter @Setter private String activity;

  public LogItem(LocalDate date, int time, int distance, int rating, int breaks, int degrees,
      String weather, String activity) {
    this.date = date;
    this.time = time;
    this.distance = distance;
    this.rating = rating;
    this.breaks = breaks;
    this.degrees = degrees;
    this.weather = weather;
    this.activity = activity;
    calculateSpeed();
  }
  public LogItem(LocalDate date, int time, int distance, int rating, float speed, int breaks,
      int degrees, String weather, String activity, int id) {
    this.date = date;
    this.time = time;
    this.distance = distance;
    this.rating = rating;
    this.breaks = breaks;
    this.degrees = degrees;
    this.weather = weather;
    this.activity = activity;
    this.speed = speed;
    this.id = id;
  }

  public void calculateSpeed() {
    this.speed = (float) distance / (float) time;
  }
}
