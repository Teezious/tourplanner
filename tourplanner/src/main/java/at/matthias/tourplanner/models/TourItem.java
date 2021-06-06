package at.matthias.tourplanner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

public class TourItem {
  @Getter @Setter public String name;
  @Getter @Setter public String description;
  @Getter @Setter public String start;
  @Getter @Setter public String end;
  @Getter @Setter public float distance;
  @JsonIgnore @Getter @Setter public int id;
  @JsonIgnore @Getter @Setter public String image;
  public boolean favorite;

  public TourItem() {}

  public TourItem(int id, String name, String start, String end, String description, float distance,
      String image) {
    this.name = name;
    this.description = description;
    this.distance = distance;
    this.start = start;
    this.end = end;
    this.id = id;
    this.image = image;
    this.favorite = false;
  }

  public TourItem(int id, String name, String start, String end, String description, float distance,
      String image, boolean favorite) {
    this.name = name;
    this.description = description;
    this.distance = distance;
    this.start = start;
    this.end = end;
    this.id = id;
    this.image = image;
    this.favorite = favorite;
  }

  public TourItem(String name, String start, String end, String description) {
    this.name = name;
    this.description = description;
    this.start = start;
    this.end = end;
  }

  public TourItem(String name, String start, String end, String description, float distance,
      String image, boolean favorite) {
    this.name = name;
    this.description = description;
    this.distance = distance;
    this.start = start;
    this.end = end;
    this.image = image;
    this.favorite = favorite;
  }

  public TourItem(
      String name, String start, String end, String description, float distance, boolean favorite) {
    this.name = name;
    this.description = description;
    this.distance = distance;
    this.start = start;
    this.end = end;
    this.favorite = favorite;
  }

  public boolean getFavorite() {
    return favorite;
  }

  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }
}
