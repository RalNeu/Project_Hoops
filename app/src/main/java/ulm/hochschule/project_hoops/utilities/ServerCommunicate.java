package ulm.hochschule.project_hoops.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.ServerException;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Ralph on 10.06.2016.
 */
public class ServerCommunicate {

    private static ServerCommunicate instance;

    private ServerCommunicate() {

    }

    public static ServerCommunicate getInstance() {
        if(instance == null) {
            instance = new ServerCommunicate();
        }
        return instance;
    }

    private double quoteUlm, quoteOther, oddsUlm, oddsOther;
    private int maxCoinsUlm, maxCoinsOther;
    public boolean sendTip(int coins, int team) throws ServerException {
        boolean retVal = true;

        try {
            Socket s = new Socket("141.59.26.107", 21395);
            ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
            InputStream is = s.getInputStream();

            oos.writeObject(new Integer(UserProfile.getInstance().getUserID()));
            int answer = is.read();
            if(answer != 0) {
                throw new ServerException("Sending of AccountID went wrong. Errorcode: " + answer);
            }

            oos.writeObject(new Integer(team));
            answer = is.read();
            if(answer != 0) {
                throw new ServerException("Sending of team went wrong. Errorcode: " + answer);
            }

            oos.writeObject(new Integer(coins));
            answer = is.read();

            if(answer != 0) {
                throw new ServerException("Sending of coins went wrong. Errorcode: " + answer);
            }

            UserProfile.getInstance().changeCoins(coins*-1);

            s.close();

        } catch (IOException ex) {
            retVal = false;
        }

        return retVal;
    }

    public double getQuoteUlm() {
        return quoteUlm;
    }

    public void setQuoteUlm(double quoteUlm) {
        this.quoteUlm = quoteUlm;
    }

    public double getQuoteOther() {
        return quoteOther;
    }

    public void setQuoteOther(double quoteOther) {
        this.quoteOther = quoteOther;
    }

    public double getOddsUlm() {
        return oddsUlm;
    }

    public void setOddsUlm(double oddsUlm) {
        this.oddsUlm = oddsUlm;
    }

    public double getOddsOther() {
        return oddsOther;
    }

    public void setOddsOther(double oddsOther) {
        this.oddsOther = oddsOther;
    }

    public int getMaxCoinsUlm() {
        System.out.println(maxCoinsUlm);
        return maxCoinsUlm;
    }

    public void setMaxCoinsUlm(int maxCoinsUlm) {
        this.maxCoinsUlm = maxCoinsUlm;
    }

    public int getMaxCoinsOther() {
        return maxCoinsOther;
    }

    public void setMaxCoinsOther(int maxCoinsOther) {
        this.maxCoinsOther = maxCoinsOther;
    }

    public boolean readQuote() throws ServerException {
        boolean retVal = true;

        try {
            Socket s = new Socket("141.59.26.107", 21396);
            OutputStream os= s.getOutputStream();
            InputStream is = s.getInputStream();

            quoteUlm = ((double) is.read())/100;
            os.write(0);
            maxCoinsUlm = is.read();
            os.write(maxCoinsUlm);
            maxCoinsOther = is.read();
            os.write(0);
            oddsUlm = ((double)is.read())/1000;
            os.write(0);
            oddsOther = ((double)is.read())/1000;
            os.write(1);

            s.close();

            quoteOther = 1 - quoteUlm;

        } catch (IOException ex) {
            retVal = false;
        }

        return retVal;
    }

    public void deleteTipps() {
        try {
            Socket s = new Socket("141.59.26.107", 21397);
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
