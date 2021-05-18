package at.matthias.tourplanner.BL;

public final class TourFactory {
    private static Manager manager;

    public static final Manager GetManager() {
        if (manager == null) {
            manager = new ManagerImpl();
        }
        return manager;
    }
}