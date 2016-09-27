package ulm.hochschule.project_hoops.views;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import ulm.hochschule.project_hoops.R;

/**
 * Created by Ralph on 27.09.2016.
 */
public class ItemView extends LinearLayout{

    private SquareImage img_Item;
    private TextView tv_ItemDesciption, tv_ItemPrice;
    private Button btn_Buy;

    public ItemView(Context context) {
        super(context);
        init(context);
        instantiateUIObjects();
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        instantiateUIObjects();
    }

    public ItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        instantiateUIObjects();
    }

    private void instantiateUIObjects() {
        img_Item = (SquareImage) this.findViewById(R.id.img_Item);
        tv_ItemDesciption = (TextView) this.findViewById(R.id.tv_ItemDescription);
        tv_ItemPrice = (TextView) this.findViewById(R.id.tv_ItemPrice);
        btn_Buy = (Button) this.findViewById(R.id.btn_Buy);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_view, this);
    }

    public void setItem(int image, String description, int price, boolean bought) {
        img_Item.setImageResource(image);
        tv_ItemDesciption.setText(description);
        tv_ItemPrice.setText("" + price);
        btn_Buy.setEnabled(!bought);

        if(bought) {

        } else {
        }

    }

}
