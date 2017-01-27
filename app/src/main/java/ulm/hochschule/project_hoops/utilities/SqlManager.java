package ulm.hochschule.project_hoops.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ulm.hochschule.project_hoops.objects.Coins;

/**
 * Bildet einen einheitlichen Zugriff auf die Datenbank an einer koordinierten Stelle.
 * Created by Johann on 14.05.2016.
 */
public class SqlManager {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement preparedStmt;

    //create an object of SingleObject
    private static SqlManager instance;

    //make the constructor private so that this class cannot be
    //instantiated

    private SqlManager() {

        try {
             StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://141.59.26.107:3306/hoops", "SuperUser", "root");
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //connection to SQL Server
    private SqlManager(boolean f) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://141.59.26.107:3306/hoops", "SuperUser", "root");
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Get the only object available
    public static SqlManager getInstance(){
        if(instance == null){
            instance = new SqlManager();
        }
        return instance;
    }

    public static SqlManager getInstanceWithoutStrictMode() {
        if(instance == null){
            instance = new SqlManager(true);
        }
        return instance;
    }

    public int createUser(String firstName, String lastName, String email, String userName, String password) {
        return createUser(firstName, lastName, email, userName, password, "1/0/0/0-0/0/0/0/0/0/0/0/0/0/0/0/0");
    }

    public ArrayList<String> readBoughtAvatarItems(int aID) {
        ArrayList<String> ret = new ArrayList<>();

        String query = "select * from bought_items where aID='" + aID + "'";

        try {
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();

            rs.first();

            ret.add(rs.getString("hats"));
            ret.add(rs.getString("eyes"));
            ret.add(rs.getString("backgrounds"));
            ret.add(rs.getString("mouths"));
            ret.add(rs.getString("skins"));
            ret.add(rs.getString("bodies"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public int createUser(String firstName, String lastName, String email, String username, String password, String achievements){

        int ret = 2, personID;
        String query;

        try {

            // create the mysql insert for person
            query = "insert into person (email, vorname, nachname, geburtsdatum, hobbies)"
                    + " values (?, ?, ?, ?, ?)";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, email);
            preparedStmt.setString(2, firstName);
            preparedStmt.setString(3, lastName);
            preparedStmt.setDate(4, new Date(100,1,1));
            preparedStmt.setString(5, "");
            preparedStmt.execute();
            ret--;

            //get the primeKey from the person
            query ="select pID from person where email='" + email + "'";
            preparedStmt = con.prepareStatement(query);
            rs= preparedStmt.executeQuery();
            rs.first();
            personID = rs.getInt("pID");

            //create Account
            query ="insert into account(username, coins, password, pID, lastlogin, achievements) values (?, ?, ?, ?, NOW(), ?)";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setInt(2, 500);
            preparedStmt.setString(3,sha1(password));
            preparedStmt.setInt(4,personID);
            preparedStmt.setString(5, achievements);
            preparedStmt.execute();
            ret--;

            query ="select aID from account where username ='" + username + "'";
            preparedStmt = con.prepareStatement(query);
            rs= preparedStmt.executeQuery();
            rs.first();
            int aID = rs.getInt("aID");

            query ="insert into bought_items(aID) values (" + aID + ")";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.execute();

        }catch(Exception e){
            System.err.println("Errorcode: " + ret);
            e.printStackTrace();
            //If user exists already, delete the person just created.
            if(ret == 1) {
                try {

                    query = "delete from person where email='" + email + "'";
                    preparedStmt = con.prepareStatement(query);
                    preparedStmt.executeUpdate();
                } catch(SQLException err) {
                    err.printStackTrace();
                    System.err.println("Unable to delete from table person.");
                }
            }
        }
        return ret;
    }

    public void updateAchievements(int aID, String achievements) {
        try {
            String query = "update account set achievements = '" + achievements + "' where aID = '" + aID + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String accnametodelete){
        try {
            int pID, aID;
            //Gette die pID des zu löschendes Datensatzes um zu wissen, zu welcher Person dieser
            //gehört. Danach kann man die pID verwenden um die Person zu löschen!
            String hilfsquery = "SELECT aID, pID from account where username=?";
            preparedStmt = con.prepareStatement(hilfsquery);
            preparedStmt.setString(1, accnametodelete);
            rs=preparedStmt.executeQuery();
            rs.next();
            pID=rs.getInt("pID");
            aID=rs.getInt("aID");

            String query = "delete from bought_items where aID = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,aID);
            preparedStmt.executeUpdate();

            query = "delete from tipps where aID = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,aID);
            preparedStmt.executeUpdate();

            query = "delete from account where username = ?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,accnametodelete);
            preparedStmt.executeUpdate();

            query = "delete from person where pID=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,pID);
            preparedStmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setVerif_Status(int intwert, int id) {
        try {
            String query ="UPDATE account SET verified=? WHERE aID=? ";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, intwert);
            preparedStmt.setInt(2,id);
            preparedStmt.executeUpdate();
        }
        catch(Exception e) {
            e.getStackTrace();
        }

    }

    public void setVerif_Code(String username, String code){
        try{
            String query = "UPDATE account SET verified_string=? WHERE username=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,code);
            preparedStmt.setString(2,username);
            preparedStmt.execute();
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public void setStatus(String username, String status){
        try {
            String query = "UPDATE account SET status=? WHERE username=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,status);
            preparedStmt.setString(2,username);
            preparedStmt.execute();
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public boolean userExist(String name){
        boolean response = false;
        try {
            String query = "select case WHEN (select count(*) from account where username='"+name+"') > 0  THEN 1 ELSE 0 END AS Abfrage;";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            response = rs.getBoolean("Abfrage");
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public boolean passwordMatches(String name, String passw){
        boolean response = false;
        try {
            String query = "select case WHEN (select count(*) from account where username='"+ name+ "' and password='" + sha1(passw) + "') > 0  THEN 1 ELSE 0 END AS Abfrage;";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            response = rs.getBoolean("Abfrage");
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    static String sha1(String input) throws NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer();
        for(int j = 0; j < 1000; j++) {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            input = sb.toString();
            sb = new StringBuffer();
        }

        return input;
    }

    public boolean emailExist(String email){
        boolean response = false;
        try {
            String query = "select case WHEN (select count(*) from person where email='"+email+"') > 0  THEN 1 ELSE 0 END AS Abfrage;";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.next();
            response = rs.getBoolean("Abfrage");
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public void buyItem(int aID, int coins, String item, String newItems) throws SQLException{
        String query = "UPDATE account a INNER JOIN bought_items b ON (a.aID = b.aID) SET a.coins = " + coins + ", b." + item + " = '" + newItems + "' WHERE a.aID = " + aID + " AND b.aID = " + aID;
        preparedStmt = con.prepareStatement(query);
        preparedStmt.executeUpdate();
    }


    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Object[] getUser(String userName) throws java.sql.SQLException{
        int  personID=0;


        Object[] retArray = new Object[14];

        String query="select * from account where username = ?";
        preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1,userName);
        rs = preparedStmt.executeQuery();
        rs.beforeFirst();
        rs.next();

        retArray[0] = rs.getString("username");
        retArray[1] = rs.getString("password");
        retArray[2] = new Coins(rs.getInt("coins"));

        retArray[9] = rs.getInt("einstellung");
        retArray[10] = rs.getString("verified_string");
        retArray[11] = rs.getInt("aID");
        retArray[12] = rs.getString("achievements");
        retArray[13] = rs.getDate("lastlogin");
        personID = rs.getInt("pID");


        query = "select * from person where pID = ?";
        preparedStmt = con.prepareStatement(query);
        preparedStmt.setInt(1,personID);
        rs = preparedStmt.executeQuery();
        rs.beforeFirst();
        rs.next();

        retArray[3] = rs.getString("vorname");
        retArray[4] = rs.getString("nachname");
        retArray[5] = rs.getString("email");
        retArray[6] = rs.getDate("geburtsdatum");
        retArray[7] = personID;
        retArray[8] = rs.getString("hobbies");

        return retArray;
    }

    public String getAvatarItems(String username) throws java.sql.SQLException {
        String itemsString = new String();

        String query="select * from account where username = ?";
        preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1,username);
        rs = preparedStmt.executeQuery();
        rs.beforeFirst();
        rs.next();
        itemsString = rs.getString("avatar_items");

        return itemsString;
    }

    public void updateAvatarItems(String itemsString, String username) {
        String query = "update account set avatar_items = '" + itemsString + "' where username = '" + username + "'";
        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLastLogin(int aID) {
        String query = "update account set lastlogin = NOW() where aID = '" + aID + "'";
        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Date getNow() {
        String query = "select NOW() AS 'N' from account";
        Date retVal = new Date(0,0,0);
        try {
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            rs.next();
            retVal = rs.getDate("N");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public void updatePerson(int personID, String col, String val) {
        try {
            String query = "update person set " + col + " = '" + val + "' where pID = '" + personID + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePerson(int personID, String col, Date val) {
        try {
            String query = "update person set " + col + " = '" + val +"' where pID = '" + personID + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccount(String username, String col, int val) {
        try {
            String query = "update account set " + col + " = '" + val +"' where username = '" + username + "'";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPassword(String username) throws java.sql.SQLException{

        String result = "";

        String query="select password from account where username=?";
        preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1,username);
        rs = preparedStmt.executeQuery();
        rs.beforeFirst();

        rs.next();

        result = rs.getString("password");

        return result;
    }

    public boolean isVerified(String username) throws SQLException {
        boolean retVal = false;

        String query = "select verified, username from account where username=?";

        preparedStmt = con.prepareStatement(query);

        preparedStmt.setString(1,username);
        rs = preparedStmt.executeQuery();
        rs.next();

        retVal = ((int) rs.getInt("verified")) == 0;

        return retVal;
    }

    public boolean getVerifStatus(String username) {
        boolean retVal = false;

        String query = "select verified, username from account where username=?";

        try {
            preparedStmt = con.prepareStatement(query);

        preparedStmt.setString(1,username);
        rs = preparedStmt.executeQuery();
        rs.next();

        retVal = ((int) rs.getInt("verified")) == 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    public void updateCoins() {
        if(!UserProfile.isLoggedIn())
            throw new RuntimeException("Please login a user in the first place.");
        UserProfile user = UserProfile.getInstance();
        String query = "update account set coins = '" + user.getCoins() + "' where username = '" + user.getUsername() + "'";

        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getVerif_Code(String username){
        String result = "";

        try{
            String query= "SELECT verified_string from account WHERE username=?";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1,username);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            rs.next();
            result = rs.getString("verified_string");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public int getActiveGame() throws NotBettableException {
        int result = -1;

        try{
            String query= "SELECT min(spielID) as minVal from spiel WHERE datum > now() order by spielID";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            if(!rs.next()) {
                throw new NotBettableException();
            }
            result = rs.getInt("minVal");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    private double addOdds(int coins, int game, int team) throws NotBettableException{
        double ret = 0;
        try{
            int coinsUlm = getMaxCoinsUlm(game);
            int coinsOther = getMaxCoinsOther(game);

            double coinsIns = coinsUlm + coinsOther + 1;

            double coinsTeam = (team == 0 ? coinsUlm : coinsOther) + 1;

            ret = (double) coins * (2.5 - (coinsTeam/coinsIns));

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public void alreadyBetted(int game) throws SQLException, AlreadyBettedException {
        String query= "SELECT * FROM tipp WHERE spielID = " + game + " AND spielID IN (select spielID from tipp where aID = " + UserProfile.getInstance().getUserID() + ")";
        preparedStmt = con.prepareStatement(query);
        rs = preparedStmt.executeQuery();
        rs.beforeFirst();
        if(rs.next()) {
            throw new AlreadyBettedException();
        }
    }

    public boolean sendTip(int coins, int team) throws AlreadyBettedException, NotBettableException{
        boolean result = true;
        try{
            if(UserProfile.getInstance().getCoins() >= coins) {
                int game = getActiveGame();

                alreadyBetted(game);


                String query = "update account set coins = " + (UserProfile.getInstance().getCoins()-coins) + " where aID = " + UserProfile.getInstance().getUserID();
                preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();

                UserProfile.getInstance().updateCoins(-1*coins);
                coins = (int) addOdds(coins, game, team);

                query = "INSERT INTO tipp(aID, spielID, coins, team) VALUES(?, ?, ?, ?)";
                preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, UserProfile.getInstance().getUserID());
                preparedStmt.setInt(2, game);
                preparedStmt.setInt(3, coins);
                preparedStmt.setInt(4, team);
                preparedStmt.executeUpdate();

            } else {
                throw new NotBettableException();
            }

        } catch(SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public void getRdyToTipp() throws AlreadyBettedException {
        try {
            alreadyBetted(getActiveGame());
        } catch (NotBettableException e) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public double getQuoteUlm(){
        double ret = 50;
        try{
            int game = getActiveGame();

            String query= "SELECT count(team) AS ins from tipp where spielID = " + game;
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            if(!rs.next()) {
                throw new NotBettableException();
            }
            int insBets = rs.getInt("ins");

            if(insBets == 0) {
                ret = 50;
            } else {
                query = "SELECT count(team) AS teamBets from tipp where team = 0 and spielID = " + game;
                preparedStmt = con.prepareStatement(query);
                rs = preparedStmt.executeQuery();
                rs.beforeFirst();
                if (!rs.next()) {
                    throw new NotBettableException();
                }
                double teamBets = rs.getInt("teamBets");

                ret = (teamBets / insBets) * 100;
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch(NotBettableException ex) {
        }
        return ret;
    }

    public double getQuoteOther() {
        return 100 - getQuoteUlm();
    }

    public int getMaxCoinsUlm(int game) throws NotBettableException{
        int result = 0;
        try{
            String query= "SELECT sum(coins) AS teamCoins from tipp where team = 0 and spielID = " + game;
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            if(!rs.next()) {
                throw new NotBettableException();
            }
            result = rs.getInt("teamCoins");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }


    public int getMaxCoinsOther(int game) throws NotBettableException{
        int result = 0;
        try{
            String query= "SELECT sum(coins) AS teamCoins from tipp where team = 1 and spielID = " + game;
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            if(!rs.next()) {
                throw new NotBettableException();
            }
            result = rs.getInt("teamCoins");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public int getWin() throws NotBettableException {
        int result = -1;
        try{
            int aID = UserProfile.getInstance().getUserID();
            String query = "SELECT * from tipp inner join spiel on tipp.spielID = spiel.spielID where aID = " +
                                        + aID + " and finished is null and gewonnen is not null";
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            if(rs.next()) {
                result = 0;
                query= "SELECT sum(coins) AS wonCoins from tipp inner join spiel on tipp.spielID = spiel.spielID where aID = "
                        + aID + " and (tipp.team = spiel.gewonnen or spiel.gewonnen = 2) and finished is null and gewonnen is not null";
                preparedStmt = con.prepareStatement(query);
                rs = preparedStmt.executeQuery();
                rs.beforeFirst();
                rs.next();

                result = rs.getInt("wonCoins");

                query= "SELECT spielID from spiel where gewonnen is not null";
                preparedStmt = con.prepareStatement(query);
                rs = preparedStmt.executeQuery();
                ArrayList<Integer> finishedGames = new ArrayList<>();

                rs.beforeFirst();
                while(rs.next()) {
                    finishedGames.add(rs.getInt("spielID"));
                }

                for(Integer spielID : finishedGames) {
                    query = "update tipp set finished = 1 where spielID = " + spielID + " and aID = " + aID;
                    preparedStmt = con.prepareStatement(query);
                    preparedStmt.executeUpdate();
                }
                UserProfile.getInstance().updateCoins(result);

                updateCoins();

            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public String getOpponentName() {
        String result = "";
        try{
            String query= "SELECT gegner from spiel where spielID = " + getActiveGame();
            preparedStmt = con.prepareStatement(query);
            rs = preparedStmt.executeQuery();
            rs.beforeFirst();
            rs.next();
            result = rs.getString("gegner");
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch(NotBettableException ex) {
            ex.printStackTrace();
        }
        return result;
    }

}