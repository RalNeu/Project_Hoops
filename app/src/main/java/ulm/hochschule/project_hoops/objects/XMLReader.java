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
public class XMLReader {





        public static void ReadXML() {

            try {

                File fXmlFile = new File("http://lmx.ratiopharmulm.com/stats/stats.php?data=teamschedule&teamid=418&saison=2015&fixedgamesonly=0");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);


                doc.getDocumentElement().normalize();

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("staff");

                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    System.out.println("\nCurrent Element :" + nNode.getNodeName());

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;


                        System.out.println("Datum: " + eElement.getElementsByTagName("datum").item(0).getTextContent());


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

