package at.matthias.tourplanner.models;

import lombok.Getter;
import lombok.Setter;

public class TourItem {
    @Getter @Setter public String name;
    @Getter @Setter public String description;
    @Getter @Setter public String start;
    @Getter @Setter public String end;
    @Getter @Setter public float distance;
    @Getter @Setter public int id;
    @Getter @Setter public String image;
    public boolean favorite;

    public TourItem(int id, String name, String start, String end, String description, float distance, String image) {
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.start = start;
        this.end = end;
        this.id = id;
        this.image = image;
        this.favorite = false;
    }

    public TourItem(int id, String name, String start, String end, String description, float distance, String image,
                    boolean favorite) {
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.start = start;
        this.end = end;
        this.id = id;
        this.image = image;
        this.favorite = favorite;
    }

    public boolean getFavorite() {
        return favorite;
    }
}
