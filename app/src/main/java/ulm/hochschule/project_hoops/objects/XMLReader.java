package ulm.hochschule.project_hoops.objects;

/**xm
 * Created by acer on 13.06.2016.
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class XMLReader {





        public static void ReadXML() {

            try {

                ;

                URL url = new URL("http://lmx.ratiopharmulm.com/stats/stats.php?data=teamschedule&teamid=418&saison=2015&fixedgamesonly=0");
                URLConnection conn = url.openConnection();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(conn.getInputStream());

                doc.getDocumentElement().normalize();

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("spiel");

                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    System.out.println("\nCurrent Element :" + nNode.getNodeName());

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        //datum abfrage
                        Date datumAbfrage = new Date();

                        //String currentTimeDateString = (datumAbfrage.getYear()+"-"+datumAbfrage.getMonth()+"-"+datumAbfrage.getDate());
                        String currentTimeDateString = (2016+"-"+06+"-"+17);
                        String xmlDateString = eElement.getElementsByTagName("datum").item(0).getTextContent();

                        if(currentTimeDateString == xmlDateString){

                            System.out.println("Datum: " + eElement.getElementsByTagName("uhrzeit").item(0).getTextContent());
                            String test = eElement.getElementsByTagName("uhrzeit").item(0).getTextContent();

                        }




                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

