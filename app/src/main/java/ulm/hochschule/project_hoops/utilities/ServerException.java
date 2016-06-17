package ulm.hochschule.project_hoops.utilities;

/**
 * Created by Ralph on 17.06.2016.
 */
public class ServerException extends Exception{

    String message;

    public ServerException(String message) {
        this.message = message;
    }

}
