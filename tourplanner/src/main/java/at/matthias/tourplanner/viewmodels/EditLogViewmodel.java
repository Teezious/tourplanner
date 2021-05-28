package at.matthias.tourplanner.viewmodels;

import at.matthias.tourplanner.BL.Loghandler;
import at.matthias.tourplanner.models.LogItem;
import lombok.Getter;
import lombok.Setter;

public class EditLogViewmodel {
   @Getter @Setter private LogItem toEdit; 

   public void saveEdit(LogItem edited){
       Loghandler lh = new Loghandler();
        lh.edit(edited);
   }
}
