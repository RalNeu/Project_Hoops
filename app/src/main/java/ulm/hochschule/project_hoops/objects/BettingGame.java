package ulm.hochschule.project_hoops.objects;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by acer on 10.06.2016.
 */
public class BettingGame {


    private HashMap<String, Integer> teamUlmMap;
    private HashMap<String, Integer> teamOtherMap;
    //Quote
    private int quoteUlm;
    private int quoteOther;
    //Show max coin for each team
    private int maxCoinsUlm;
    private int maxCoinsOther;
    private int maxCoins;
    //quote multiplicator
    private double oddsUlm;
    private double oddsOther;


    public void BettingGame(){

    }

    public void winner(boolean bool) {
        int tmp;
        if (bool = true) {
            for (Map.Entry<String, Integer> entry : teamUlmMap.entrySet()) {
                tmp = entry.getValue();
                entry.setValue((int)(tmp * this.oddsUlm));
            }
        } else {
            for (Map.Entry<String, Integer> entry : teamOtherMap.entrySet()) {
                tmp = entry.getValue();
                entry.setValue((int)(tmp * this.oddsOther));
            }
        }
    }

    public void calculateQuote(){
        int total=0;
        total = this.teamUlmMap.size()+this.teamOtherMap.size();
        this.quoteUlm = (this.teamUlmMap.size() / total) * 100;
        this.quoteOther = (this.teamOtherMap.size() / total) * 100;
    }

    public void calculateMaxCoins() {
        for(Map.Entry<String,Integer> entry : teamUlmMap.entrySet()){
           this.maxCoinsUlm += entry.getValue();
        }
        for(Map.Entry<String,Integer> entry : teamOtherMap.entrySet()){
            this.maxCoinsOther += entry.getValue();
        }
        this.maxCoins = this.maxCoinsOther + this.maxCoinsUlm;
    }

    public void calculateOdds(){
        this.oddsUlm = this.maxCoins/this.maxCoinsUlm;
        this.oddsOther = this.maxCoins/this.maxCoinsOther;

    }

    //Insert Coins
    public void insertCoinsToUlm(String username, int value){
        this.teamUlmMap.put(username,value);
    }
    public void insertCoinsToOther(String username, int value){
        this.teamOtherMap.put(username,value);
    }

    public void deleteMaps(){
        this.teamUlmMap.clear();
        this.teamOtherMap.clear();
    }


    //get Method
    public int getMaxCoins(){
        return this.maxCoins;
    }
    public int getMaxCoinsUlm(){
        return this.maxCoinsUlm;
    }
    public int getMaxCoinsOther(){
        return this.maxCoinsOther;
    }


}
