package ulm.hochschule.project_hoops.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ralph on 10.06.2016.
 */
public class ServerCommunicate {

    private static ServerCommunicate instance;
    private double quoteUlm, quoteOther, oddsUlm, oddsOther;
    private int maxCoinsUlm, maxCoinsOther;

    String ipAdress = "141.59.26.107";

    private ServerCommunicate() {

    }

    public static ServerCommunicate getInstance() {
        if(instance == null) {
            instance = new ServerCommunicate();
        }
        return instance;
    }

    public String getAch() throws ServerException {

        String retString = "";

        boolean access = false;

        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ipAdress, 21399), 3000);
            access = true;

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            retString = (String) ois.readObject();

            s.close();
        } catch (IOException ex) {
            throw new ServerException("No answer from Server");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return retString; //TODO
        }

    public boolean sendTip(int coins, int team) throws ServerException, TimeoutException {
        boolean retVal = true;

        boolean access = false;
        try {

            Socket s = new Socket();
            s.connect(new InetSocketAddress(ipAdress, 21395), 3000);
            access = true;

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

            UserProfile.getInstance().updateCoins(coins*-1);

            s.close();

        } catch (IOException ex) {

            if(!access) {
                throw new TimeoutException();
            } else {

                retVal = false;
                throw new ServerException("No answer from Server");
            }
        }

        return retVal;
    }

    public boolean getRdyToTipp() {
        boolean res = false;
        try {

            Socket s = new Socket();
            s.connect(new InetSocketAddress(ipAdress, 21400), 3000);

            if(s.getInputStream().read() == 0) {
                res = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
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

        boolean access = false;

        try {

            Socket s = new Socket();
            s.connect(new InetSocketAddress(ipAdress, 21396), 3000);
            access = true;

            OutputStream os= s.getOutputStream();
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());

            quoteUlm = (Double) is.readObject();
            maxCoinsUlm = (Integer) is.readObject();
            maxCoinsOther = (Integer) is.readObject();
            oddsUlm = (Double)is.readObject();
            oddsOther = (Double) is.readObject();

            s.close();

            quoteOther = 100 - quoteUlm;

        } catch (IOException ex) {
            retVal = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    public void deleteTipps() {
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ipAdress, 21397), 3000);
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWin() {
        int retVal = -4;
        boolean access = false;
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(ipAdress, 21398), 3000);
            access = true;

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            oos.writeObject(new Integer(UserProfile.getInstance().getUserID()));

            retVal = (Integer) ois.readObject();

            s.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
