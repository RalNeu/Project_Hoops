package ulm.hochschule.project_hoops.utilities;

import ulm.hochschule.project_hoops.interfaces.AchievementStrategy;

/**
 * Created by Ralph on 28.07.2016.
 */
public class AchievementStrategyHandler {

    public static AchievementStrategy getStrategy(int id) {
        AchievementStrategy as = null;

        switch (id) {
            case 0:
                as = strat1();
                break;
            case 1:
                as = strat1();
                break;
            case 2:
                as = strat1();
                break;
            case 3:
                as = strat2();
                break;
            case 4:
                as = strat1();
                break;
            case 5:
                as = strat1();
                break;
            case 6:
                as = strat1();
                break;
            case 7:
                as = strat1();
                break;
            case 8:
                as = strat1();
                break;
            case 9:
                as = strat1();
                break;
            case 10:
                as = strat1();
                break;
            case 11:
                as = strat1();
                break;
            case 12:
                as = strat1();
                break;
            case 13:
                as = strat3();
                break;
            case 14:
                as = strat1();
                break;
            case 15:
                as = strat3();
                break;
        }

        return as;
    }

    private static AchievementStrategy strat1() {
        return new AchievementStrategy() {
            @Override
            public int changeValue(int old, int val, int max) {
                return (old + val) > max ? max : old + val;
            }
        };
    }

    private static AchievementStrategy strat2() {
        return new AchievementStrategy() {
            @Override
            public int changeValue(int old, int val, int max) {
                if(val > old) {
                    return val > max ? max : val;
                } else {
                    return old;
                }
            }
        };
    }

    private static AchievementStrategy strat3() {
        return new AchievementStrategy() {
            @Override
            public int changeValue(int old, int val, int max) {
                if(val > old) {
                    return val > max ? max : val;
                } else
                    return old;
            }
        };
    }
}
