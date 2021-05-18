package at.matthias.tourplanner.models;

import lombok.Getter;
import lombok.Setter;

public class TourItem{
    @Getter @Setter  public String name;
    @Getter @Setter public String url;

    public TourItem(String name){
       this.name = name; 
    }


}
