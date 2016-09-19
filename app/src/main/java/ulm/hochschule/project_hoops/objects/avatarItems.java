package ulm.hochschule.project_hoops.objects;

import android.content.Context;
import android.graphics.drawable.Drawable;


import java.util.ArrayList;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Maxim on 19.09.2016.
 */


public class AvatarItems {

    private int hatIndex, eyesIndex, hairIndex, beardIndex, skinIndex, bodyIndex;
    private ArrayList<Integer> hats, hair, eyes, beard, skin, body;
    private static AvatarItems aItems;

    private AvatarItems() {
        fillItemArrays();
    }

    public static AvatarItems getInstance() {
        if (aItems == null) {
            aItems = new AvatarItems();
        }
        return aItems;
    }

    private void fillItemArrays() {
        hats = new ArrayList<>();
        hair = new ArrayList<>();
        eyes = new ArrayList<>();

        hats.add(R.drawable.avatarhat);
        hats.add(R.drawable.avatarhat2);

        eyes.add(R.drawable.avatareyes);
        eyes.add(R.drawable.avatareyes2);

        hair.add(R.drawable.avatarhairunderhat);
    }

    public int getAccountItemByID(String item) throws java.sql.SQLException {
        SqlManager sqlManager = SqlManager.getInstance();
        UserProfile uProfile = UserProfile.getInstance();
        String itemsString = sqlManager.getAvatarItems(uProfile.getUsername());
        switch (item) {
            case "hat":
                hatIndex = Integer.parseInt(itemsString.substring(0, 2));
                return hats.get(hatIndex);
            case "eyes":
                eyesIndex = Integer.parseInt(itemsString.substring(2, 4));
                return eyes.get(eyesIndex);
            case "hair":
                hairIndex = Integer.parseInt(itemsString.substring(4, 6));
                return hair.get(hairIndex);
            case "beard":
                beardIndex = Integer.parseInt(itemsString.substring(6, 8));
                return beard.get(beardIndex);
            case "skin":
                skinIndex = Integer.parseInt(itemsString.substring(8, 10));
                return skin.get(skinIndex);
            case "body":
                bodyIndex = Integer.parseInt(itemsString.substring(10, 12));
                return body.get(bodyIndex);
        }
        return 0;
    }

    public ArrayList getList(String item) {
        switch (item) {
            case "hat":
                return hats;
            case "eyes":
                return eyes;
            case "hair":
                return hair;
            case "beard":
                return beard;
            case "skin":
                return skin;
            case "body":
                return body;
        }
        return null;
    }

    public int getIndex(String item) {
        switch (item) {
            case "hat":
                return hatIndex;
            case "eyes":
                return eyesIndex;
            case "hair":
                return hairIndex;
            case "beard":
                return beardIndex;
            case "skin":
                return skinIndex;
            case "body":
                return bodyIndex;
        }
        return 0;
    }

}
