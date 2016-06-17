package ulm.hochschule.project_hoops.objects;

/**
 * Created by Johann on 14.05.2016.
 */
public class Coins {

    private int coins;

    public Coins(int coins){
        this.coins = coins;
    }

    public void changeCoins(int c) {
        int ref = coins + c;

        coins = ref < 0 ? 0 : ref;
    }

    public int getCoins(){ return coins; }
}
