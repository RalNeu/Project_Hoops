package ulm.hochschule.project_hoops.objects;

import java.util.Date;

/**
 * Created by acer on 13.06.2016.
 */
public class BlockingBettingGame {

    /*
    Wenn in der XML den start für das Spiel angezeigt wird muss man die Uhr zeit des severs abfragen und denn mit
     dem Spielwert. Eine methode dann schreiben ob das spiel schon gestartet ist oder nicht true für start geht nicht
     mehr in die methoden rein zeigt an ist nicht mehr möglich zu wetten. False es geht noch.
     */
    private int hour;
    private int minute;
    private boolean startedGamebool = false ;


    public void checkIfGameStarted(){
        Date date  = new Date();
        this.hour = date.getHours();
        this.minute = date.getMinutes();
        try {
            while(startedGamebool==false) {
                wait(30000);
                if (this.hour == 0/*xmlcode*/ && this.minute ==0 /*xmlcodeminutes*/) {
                    this.startedGamebool = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    // diese methode sollte die ganze zeit laufen
    // wenn der button gedrückt wird wird abgefragt.

    public boolean getstartedGamebool()
    {
        return startedGamebool;
    }
}
