package ulm.hochschule.project_hoops;

import android.app.Activity;

import org.junit.Test;

import ulm.hochschule.project_hoops.utilities.AchievementHandler;
import ulm.hochschule.project_hoops.utilities.AchievementObject;
import ulm.hochschule.project_hoops.utilities.AchievementStrategyHandler;
import ulm.hochschule.project_hoops.utilities.ServerException;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Ralph on 28.07.2016.
 */
public class AchievementTests {

    @Test
    public void checkAchievementsFehlt() throws ServerException {
        UserProfile.logoffUser();
        SqlManager s = SqlManager.getInstanceWithoutStrictMode();
        if(!s.userExist("AchUser1"))
            s.createUser("test", "test", "email@example-1.de", "AchUser1", "11111111", "0/0/5/2-0/1/1/1/9/3/2/0/0/4/478/1967/463");
        UserProfile.getInstance("AchUser1", new Activity());

        AchievementHandler ah = AchievementHandler.getInstance();

        AchievementObject ao;

        for(int i = 0; i < 16; i++) {
            ao = ah.getAchievement(i);
            assertEquals(0, ao.getEmblem());
        }
    }

    @Test
    public void checkAchievementsBronze() throws ServerException {
        UserProfile.logoffUser();
        SqlManager s = SqlManager.getInstanceWithoutStrictMode();
        if(!s.userExist("AchUser2"))
            s.createUser("test", "test", "email@example0.de", "AchUser2", "11111111", "1/1/10/3-3/5/5/1/20/5/5/2/2/10/2000/5000/4999");
        UserProfile.getInstance("AchUser2", new Activity());

        AchievementHandler ah = AchievementHandler.getInstance();

        AchievementObject ao;

        for(int i = 0; i < 16; i++) {
            if(i != 0 && i != 1 && i != 6) {
                ao = ah.getAchievement(i);
                assertEquals(1, ao.getEmblem());
            }
        }
    }

    @Test
    public void checkAchievementsSilber() throws ServerException {
        UserProfile.logoffUser();
        SqlManager s = SqlManager.getInstanceWithoutStrictMode();
        if(!s.userExist("AchUser3"))
            s.createUser("test", "test", "email@example1.de", "AchUser3", "11111111", "1/1/50/7-7/30/10/1/50/15/15/10/10/21/9999/10000/10000");
        UserProfile.getInstance("AchUser3", new Activity());

        AchievementHandler ah = AchievementHandler.getInstance();

        AchievementObject ao;

        for(int i = 0; i < 16; i++) {
            if(i != 0 && i != 1 && i != 6) {
                ao = ah.getAchievement(i);
                assertEquals(2, ao.getEmblem());
            }
        }
    }

    @Test
    public void checkAchievementsGold() throws ServerException {
        UserProfile.logoffUser();
        SqlManager s = SqlManager.getInstanceWithoutStrictMode();
        if(!s.userExist("AchUser4"))
            s.createUser("test", "test", "email@example2.de", "AchUser4", "11111111", "1/1/75/15-15/50/15/3/100/30/30/30/30/50/10000/20000/15000");
        UserProfile.getInstance("AchUser4", new Activity());

        AchievementHandler ah = AchievementHandler.getInstance();

        AchievementObject ao;

        for(int i = 0; i < 16; i++) {
            ao = ah.getAchievement(i);
            assertEquals(3, ao.getEmblem());
        }
    }

    @Test
    public void testStrategies() {
        assertEquals(10, AchievementStrategyHandler.getStrategy(0).changeValue(7,3,20));
        assertEquals(9, AchievementStrategyHandler.getStrategy(0).changeValue(7,3,9));
        assertEquals(20, AchievementStrategyHandler.getStrategy(0).changeValue(17,13,20));
        assertEquals(9, AchievementStrategyHandler.getStrategy(3).changeValue(8,9,20));
        assertEquals(20, AchievementStrategyHandler.getStrategy(0).changeValue(20,50,20));
        assertEquals(13, AchievementStrategyHandler.getStrategy(13).changeValue(13,3,20));
        assertEquals(10, AchievementStrategyHandler.getStrategy(13).changeValue(7,10,20));
        assertEquals(10, AchievementStrategyHandler.getStrategy(13).changeValue(10,100,10));
    }
}