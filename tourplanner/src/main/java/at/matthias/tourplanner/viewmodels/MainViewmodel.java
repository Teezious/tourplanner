package at.matthias.tourplanner.viewmodels;


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
