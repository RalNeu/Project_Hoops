package ulm.hochschule.project_hoops.utilities;

import ulm.hochschule.project_hoops.interfaces.AchievementStrategy;

/**
 * Prüft anhand der Achievement-ID das Vorgehen, wie mit dem value umgegangen werden soll. Bei manchen
 * Achievements ist es nötig den Value zu überschreiben, zum Beispiel wenn ein neuer Highscore erreicht wurde, oder den Value aufzuaddieren,
 * wenn zum Beispiel die insgesamt gewonnen Coins gemessen werden wollen.
 * Created by Ralph on 28.07.2016.
 */
public class AchievementStrategyHandler {

    public AchievementStrategy getStrategy(int id) {
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
                as = strat2();
                break;
            case 14:
                as = strat1();
                break;
            case 15:
                as = strat2();
                break;
        }

        return as;
    }

    /**
     * Die Strategie um val dem alten Wert hinzuzufügen
     * @return Die Strategie
     */
    private AchievementStrategy strat1() {
        return new AchievementStrategy() {
            @Override
            public int changeValue(int old, int val, int max) {
                return (old + val) > max ? max : old + val;
            }
        };
    }

    /**
     * Die Strategie um val zu ersetzen, falls es größer ist als der alte Wert
     * @return Die Strategie
     */
    private AchievementStrategy strat2() {
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
}
