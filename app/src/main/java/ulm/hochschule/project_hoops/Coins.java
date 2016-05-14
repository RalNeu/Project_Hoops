package ulm.hochschule.project_hoops;

/**
 * Created by Johann on 14.05.2016.
 */
public class Coins {

    private int coins;

    public Coins(int coins){
        this.coins = coins;
    }

    public void addCoins(int coins){
        this.coins = coins;
    }

    public boolean removeCoins(int coins){
        return (this.coins -= coins) > 0;
    }

    public int getCoins(){
        return coins;
    }
}
