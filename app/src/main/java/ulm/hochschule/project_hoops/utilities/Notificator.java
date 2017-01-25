package ulm.hochschule.project_hoops.utilities;

import java.util.Observable;

/**
 * Eine Klasse die dazu dient Klassen die bereits eine Klasse erben trotzdem Observable zu machen
 * Created by Ralph on 18.05.2016.
 */
public class Notificator extends Observable{


    public void notifObs() {
        setChanged();
        notifyObservers();
    }
}
