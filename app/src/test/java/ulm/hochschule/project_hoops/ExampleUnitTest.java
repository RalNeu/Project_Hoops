package ulm.hochschule.project_hoops;

import org.junit.Test;

import ulm.hochschule.project_hoops.utilities.ServerCommunicate;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {



    @Test
    public void testServerSendTip1() throws Exception {
        ServerCommunicate sc = ServerCommunicate.getInstance();
        sc.deleteTipps();

        SqlManager s = SqlManager.getInstance();

        if(!s.userExist("a"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "a", "11111111");
        if(!s.userExist("b"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "b", "11111111");
        if(!s.userExist("c"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "c", "11111111");
        if(!s.userExist("d"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "d", "11111111");
        if(!s.userExist("e"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "e", "11111111");
        if(!s.userExist("f"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "f", "11111111");
        if(!s.userExist("g"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "g", "11111111");
        if(!s.userExist("h"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "h", "11111111");
        if(!s.userExist("i"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "i", "11111111");
        if(!s.userExist("j"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "j", "11111111");

        UserProfile.logoffUser();
        UserProfile.getInstance("a");
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        UserProfile.getInstance("b");
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        UserProfile.getInstance("c");
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        UserProfile.getInstance("d");
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        UserProfile.getInstance("e");
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        UserProfile.getInstance("f");
        sc.sendTip(10, 1);

        UserProfile.logoffUser();
        UserProfile.getInstance("g");
        sc.sendTip(10, 1);

        UserProfile.logoffUser();
        UserProfile.getInstance("h");
        sc.sendTip(10, 1);

        UserProfile.logoffUser();
        UserProfile.getInstance("i");
        sc.sendTip(10, 1);

        UserProfile.logoffUser();
        UserProfile.getInstance("j");
        sc.sendTip(10, 1);

        sc.readQuote();

        assertEquals(50, sc.getMaxCoinsUlm());
        assertEquals(50, sc.getMaxCoinsOther());
        assertEquals(0.5, sc.getQuoteUlm(), 0.001);
        assertEquals(0.5, sc.getQuoteOther(), 0.001);

    }
}