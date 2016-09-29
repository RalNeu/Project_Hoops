package ulm.hochschule.project_hoops.objects;

import ulm.hochschule.project_hoops.utilities.UserProfile;

/**
 * Created by Ralph on 27.09.2016.
 */
public class AvatarItemDescription {

    private int id, price, item, idx;
    private String description;

    public AvatarItemDescription(int id, int price, String description, int item, int idx) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.item = item;
        this.idx = idx;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getItem() {
        return item;
    }

    public int getIdx() {
        return idx;
    }

    public boolean getBought() {
        return UserProfile.getInstance().getItemAt(item, idx) == 'y' ? true : false;

    }
}
