package ulm.hochschule.project_hoops.objects;


import java.util.ArrayList;

import ulm.hochschule.project_hoops.R;
import ulm.hochschule.project_hoops.utilities.SqlManager;
import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Maxim on 19.09.2016.
 */


public class AvatarItems {

    private int hatIndex, eyesIndex, backgroundIndex, mouthIndex, skinIndex, bodyIndex;
    private ArrayList<Integer> hats, background, eyes, mouth, skin, body;
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
        background = new ArrayList<>();
        eyes = new ArrayList<>();
        mouth = new ArrayList<>();
        skin = new ArrayList<>();
        body = new ArrayList<>();


        hats.add(R.drawable.avatarhat);
        hats.add(R.drawable.avatarhat2);
        hats.add(R.drawable.skyrim_helmet);

        eyes.add(R.drawable.avatareyes);
        eyes.add(R.drawable.avatareyes2);
        eyes.add(R.drawable.avatareyes3);
        eyes.add(R.drawable.avatareyes4);
        eyes.add(R.drawable.avatareyes5);
        eyes.add(R.drawable.avatareyes6);
        eyes.add(R.drawable.avatareyes7);
        eyes.add(R.drawable.avatareyes8);
        eyes.add(R.drawable.avatareyes9);
        eyes.add(R.drawable.avatareyes10);
        eyes.add(R.drawable.avatareyes11);
        eyes.add(R.drawable.avatareyes12);

        background.add(R.drawable.avatarhairunderhat);
        background.add(R.drawable.avatarhairunderhat2);

        mouth.add(R.drawable.avatarmouth);
        mouth.add(R.drawable.avatarmouth2);

        skin.add(R.drawable.avatarskin);
        skin.add(R.drawable.avatarskin2);

        body.add(R.drawable.avatarbody);
        body.add(R.drawable.avatarbody2);

    }

    public int getAccountItemByID(String item) throws java.sql.SQLException {
        SqlManager sqlManager = SqlManager.getInstance();
        UserProfile uProfile = UserProfile.getInstance();
        String itemsString = sqlManager.getAvatarItems(uProfile.getUsername());
        switch (item) {
            case "hat":
                hatIndex = Integer.parseInt(itemsString.substring(0, 2));
                if(hatIndex == -1)
                    return 0;
                return hats.get(hatIndex);
            case "eyes":
                eyesIndex = Integer.parseInt(itemsString.substring(2, 4));
                return eyes.get(eyesIndex);
            case "background":
                backgroundIndex = Integer.parseInt(itemsString.substring(4, 6));
                return background.get(backgroundIndex);
            case "mouth":
                mouthIndex = Integer.parseInt(itemsString.substring(6, 8));
                return mouth.get(mouthIndex);
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
            case "background":
                return background;
            case "mouth":
                return mouth;
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
            case "background":
                return backgroundIndex;
            case "mouth":
                return mouthIndex;
            case "skin":
                return skinIndex;
            case "body":
                return bodyIndex;
        }
        return 0;
    }

}
