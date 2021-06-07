package at.matthias.tourplanner.models;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteItem {
  @JsonProperty private String sessionId;
  @JsonProperty private float distance;

  public RouteItem() {}

  public RouteItem(String sessionId, float distance) {
    this.sessionId = sessionId;
    this.distance = distance;
  }
  // TODO
  // @Override
  // public String toString() {
  //     return "Route: {\n"
  //         + "\tsessionId: " + sessionId + ",\n"
  //         + "\tdistance: " + distance + "\n"
  //         + "}";
  // }
}
