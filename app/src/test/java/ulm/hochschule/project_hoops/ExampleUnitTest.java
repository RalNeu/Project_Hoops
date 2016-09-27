package ulm.hochschule.project_hoops;

import android.app.Activity;

import org.junit.Test;

import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.AchievementObject;
import ulm.hochschule.project_hoops.utilities.ServerCommunicate;
import ulm.hochschule.project_hoops.utilities.ServerException;
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

        SqlManager s = SqlManager.getInstanceWithoutStrictMode();



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
        s.updateAchievements(7, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("a", new Activity());
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        s.updateAchievements(8, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("b", new Activity());
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        s.updateAchievements(9, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("c", new Activity());
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        s.updateAchievements(10, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("d", new Activity());
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        s.updateAchievements(11, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("e", new Activity());
        sc.sendTip(10, 0);

        UserProfile.logoffUser();
        s.updateAchievements(12, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("f", new Activity());
        sc.sendTip(10, 1);

        UserProfile.logoffUser();
        s.updateAchievements(13, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("g", new Activity());
        sc.sendTip(10, 1);

        UserProfile.logoffUser();
        s.updateAchievements(14, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("h", new Activity());
        sc.sendTip(10, 1);

        UserProfile.logoffUser();
        s.updateAchievements(15, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("i", new Activity());
        sc.sendTip(10, 1);

        UserProfile.logoffUser();
        s.updateAchievements(16, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("j", new Activity());
        sc.sendTip(10, 1);

        sc.readQuote();

        assertEquals(50, sc.getMaxCoinsUlm());
        assertEquals(50, sc.getMaxCoinsOther());
        assertEquals(50, sc.getQuoteUlm(), 0.01);
        assertEquals(50, sc.getQuoteOther(), 0.01);
    }

    @Test
    public void readWinGameNotFinished() throws Exception{
        ServerCommunicate sc = ServerCommunicate.getInstance();

        SqlManager s = SqlManager.getInstanceWithoutStrictMode();

        if(!s.userExist("a"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "a", "11111111");
        if(!s.userExist("b"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "b", "11111111");

        UserProfile.logoffUser();
        s.updateAchievements(7, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("a", new Activity());
        sc.sendTip(10,0);

        assertEquals(-2, sc.getWin());

        UserProfile.logoffUser();
        s.updateAchievements(8, "0/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
        UserProfile.getInstance("b", new Activity());
        assertEquals(-2, sc.getWin());

    }



}