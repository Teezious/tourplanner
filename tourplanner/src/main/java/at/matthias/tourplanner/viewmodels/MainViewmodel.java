package at.matthias.tourplanner.viewmodels;
import org.apache.log4j.Logger;

public class MainViewmodel {
    private TourOverviewViewmodel tovm;
    private TourInfosViewmodel tivm;
    public MainViewmodel(TourOverviewViewmodel tovm, TourInfosViewmodel tivm) {
        this.tovm = tovm;
        this.tivm = tivm;
        this.tovm.addObserver(tivm);
        this.tivm.addSearchObserver(tovm);
    }
}
