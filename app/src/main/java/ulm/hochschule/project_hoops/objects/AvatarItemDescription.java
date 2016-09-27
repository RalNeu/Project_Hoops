package ulm.hochschule.project_hoops.objects;

/**
 * Created by Ralph on 27.09.2016.
 */
public class AvatarItemDescription {

    private int id, price;
    private String description;

    public AvatarItemDescription(int id, int price, String description) {
        this.id = id;
        this.price = price;
        this.description = description;
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
}
