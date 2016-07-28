package ulm.hochschule.project_hoops;

import org.junit.Test;

import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.AchievementObject;
import ulm.hochschule.project_hoops.utilities.ServerCommunicate;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {



    //@Test
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
        assertEquals(50, sc.getQuoteUlm(), 0.01);
        assertEquals(50, sc.getQuoteOther(), 0.01);
    }

    //@Test
    public void readWinGameNotFinished() throws Exception{
        ServerCommunicate sc = ServerCommunicate.getInstance();
        sc.deleteTipps();

        SqlManager s = SqlManager.getInstanceWithoutStrictMode();

        if(!s.userExist("a"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "a", "11111111");
        if(!s.userExist("b"))
            s.getInstance().createUser("Vorname", "Nachname", "test@example.com", "b", "11111111");

        UserProfile.logoffUser();
        UserProfile.getInstance("a");
        sc.sendTip(10,0);

        assertEquals(-2, sc.getWin());

        UserProfile.logoffUser();
        UserProfile.getInstance("b");
        assertEquals(-1, sc.getWin());

    }

    @Test
    public void checkAchievementsFehlt() {
        UserProfile.logoffUser();
        SqlManager s = SqlManager.getInstanceWithoutStrictMode();
        if(!s.userExist("AchUser1"))
            s.createUser("test", "test", "email@example.de", "AchUser1", "11111111", "0/0/5/2/1/1/1/9/3/2/0/0/4/478/1967/463", 1);
        UserProfile.getInstance("AchUser1");

        AchievementHandler ah = AchievementHandler.getInstance();

        AchievementObject ao;

        for(int i = 0; i<16;i++) {
            ao = ah.getAchievement(i);
            assertEquals(0, ao.getEmblem());
        }
    }

    @Test
    public void checkAchievementsBronze() {
        UserProfile.logoffUser();
        SqlManager s = SqlManager.getInstanceWithoutStrictMode();
        if(!s.userExist("AchUser2"))
            s.createUser("test", "test", "email@example.de", "AchUser2", "11111111", "1/1/10/3/5/5/1/20/5/5/2/2/10/2000/5000/4999", 1);
        UserProfile.getInstance("AchUser2");

        AchievementHandler ah = AchievementHandler.getInstance();

        AchievementObject ao;

        for(int i = 0; i<16;i++) {
            if(i != 0 && i != 1 && i != 6) {
                ao = ah.getAchievement(i);
                assertEquals(1, ao.getEmblem());
            }
        }
    }

    @Test
    public void checkAchievementsSilber() {
        UserProfile.logoffUser();
        SqlManager s = SqlManager.getInstanceWithoutStrictMode();
        if(!s.userExist("AchUser3"))
            s.createUser("test", "test", "email@example.de", "AchUser3", "11111111", "1/1/50/7/30/10/1/50/15/15/10/10/21/9999/10000/10000", 1);
        UserProfile.getInstance("AchUser3");

        AchievementHandler ah = AchievementHandler.getInstance();

        AchievementObject ao;

        for(int i = 0; i<16;i++) {
            if(i != 0 && i != 1 && i != 6) {
                ao = ah.getAchievement(i);
                assertEquals(2, ao.getEmblem());
            }
        }
    }

    @Test
    public void checkAchievementsGold() {
        UserProfile.logoffUser();
        SqlManager s = SqlManager.getInstanceWithoutStrictMode();
        if(!s.userExist("AchUser4"))
            s.createUser("test", "test", "email@example.de", "AchUser4", "11111111", "1/1/75/15/50/15/3/100/30/30/30/30/50/10000/20000/15000", 1);
        UserProfile.getInstance("AchUser4");

        AchievementHandler ah = AchievementHandler.getInstance();

        AchievementObject ao;

        for(int i = 0; i<16;i++) {
            ao = ah.getAchievement(i);
            assertEquals(3, ao.getEmblem());
        }
    }


}