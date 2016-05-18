package ulm.hochschule.project_hoops;

import java.util.Observable;

/**
 * Created by Ralph on 18.05.2016.
 */
public class Notificator extends Observable{


    public void notifObs() {
        setChanged();
        notifyObservers();
    }
}
