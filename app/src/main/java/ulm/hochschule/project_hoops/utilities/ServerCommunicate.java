package ulm.hochschule.project_hoops.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

    private ServerCommunicate() {

    }

    public static ServerCommunicate getInstance() {
        if(instance == null) {
            instance = new ServerCommunicate();
        }
        return instance;
    }

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

            UserProfile.getInstance().updateCoins(coins*-1);

            s.close();

        } catch (IOException ex) {
            retVal = false;
            throw new ServerException("No answer from Server");
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
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());

            quoteUlm = (Double) is.readObject();
            os.write(0);
            maxCoinsUlm = (Integer) is.readObject();
            os.write(maxCoinsUlm);
            maxCoinsOther = (Integer) is.readObject();
            os.write(0);
            oddsUlm = (Double)is.readObject();
            os.write(0);
            oddsOther = (Double) is.readObject();
            os.write(1);

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
            Socket s = new Socket("141.59.26.107", 21397);
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWin() {
        int retVal = -4;
        try {
            Socket s = new Socket("141.59.26.107", 21398);

            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            oos.writeObject(new Integer(UserProfile.getInstance().getUserID()));

            retVal = (Integer) ois.readObject();

            if(retVal < 0) {
                oos.writeObject(new Integer(-1));
            } else {
                oos.writeObject(new Integer(0));
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
