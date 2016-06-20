package ulm.hochschule.project_hoops.objects;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by acer on 13.06.2016.
 */
public class BlockingBettingGame{

    /*
    Wenn in der XML den start für das Spiel angezeigt wird muss man die Uhr zeit des severs abfragen und denn mit
     dem Spielwert. Eine methode dann schreiben ob das spiel schon gestartet ist oder nicht true für start geht nicht
     mehr in die methoden rein zeigt an ist nicht mehr möglich zu wetten. False es geht noch.
     */
    private int hour;
    private int minute;
    private boolean startedGamebool = false ;
    private XMLReader xmlReader;


    public void blockingBettingGame(){
        while(true) {
            //Calendar
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
            String currentTime = (hour + ":" + minute + ":" + 00);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String currentDay = (year + "-" + month + 1 + "-" + day);


            //XML nur noch zum test kann gelöscht werden
            xmlReader.ReadXMLGameTime();
            String xmlTime = xmlReader.getXmlTimeString();
            String xmlDay = xmlReader.getXmlDayString();
            //Test outprint Test muss dann gelöscht werden
            System.out.print("currentTime: "+ currentTime);
            System.out.print("currentDay: " + currentDay);
            System.out.print("xmlTime: " + xmlTime);
            System.out.print("xmlDay: "+xmlDay);

            try {
                while (startedGamebool == false) {
                    xmlReader.ReadXMLGameTime();
                    wait(30000);
                    //Test
                    System.out.print("test 30 sec:"+"Day:"+xmlReader.getXmlDayString()+"Stunde:"+xmlReader.getXmlTimeString());
                    //
                    if (currentTime.equals( xmlReader.getXmlTimeString()) && currentDay.equals(xmlReader.getXmlDayString())) {
                        this.startedGamebool = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                wait(10000000);
                this.startedGamebool = false;

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }





    public boolean getstartedGamebool()
    {
        return startedGamebool;
    }


}
