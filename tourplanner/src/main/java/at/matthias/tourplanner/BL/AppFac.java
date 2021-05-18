package at.matthias.tourplanner.BL;

public final class AppFac{
    private static AppManager manager;

    public static final AppManager GetManager(){
        if(manager == null){
            manager = new ManagerImpl();
        }
        return manager;
    }



}