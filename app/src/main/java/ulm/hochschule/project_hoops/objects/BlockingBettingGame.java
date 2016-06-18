package ulm.hochschule.project_hoops.objects;

import java.util.Calendar;
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
    private XMLReader xmlReader;


    public void checkIfGameStarted(){
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        String currentTime = (hour+":"+minute+":"+00);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String currentDay = (year+"-"+month+1+"-"+day);
        xmlReader.ReadXML();
        String xmlTime = xmlReader.getXmlTimeString();
        String xmlDay = xmlReader.getXmlDayString();

        try {
            while(startedGamebool==false) {
                wait(30000);
                /*if (currentTime.equals(xmlTime)&& currentDay.equals()) {
                    this.startedGamebool = true;
                    xmlTime = null;
                }*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean getstartedGamebool()
    {
        return startedGamebool;
    }

    //Gedanken: 1. logic Tages abfrage benötigt schon fast fertig?
    //          2. Button bei den Coins if abfage den getstartedGamebool() ture = text"spiel hat begonnen es ist nicht möglich zu tippen
                   //false man kann einfach wetten
    //xml Day muss getMthode  nochmachen
    //Classen in den Server schreiben
}
